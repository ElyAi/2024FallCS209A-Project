package com.project.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

public class UpdateTags {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/cs209";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "200325Wyx";
    public static void main(String[] args) {
        try {
            String apiKey = "rl_zyHKGUaHDXG8ezSdnxX2xfgsm"; // 如果有 API 密钥，请替换这里，否则省略 "&key=" 参数
            String urlString = "https://api.stackexchange.com/2.3/questions?page=10&pagesize=100&order=desc&sort=votes&tagged=java&site=stackoverflow&filter=!LaSRHcCkdP4H9pnK3i2crW";
            if (!apiKey.isEmpty()) {
                urlString += "&key=" + apiKey;
            }

            // 创建 URL 和连接对象
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Java Application)");
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // 解析 JSON 数据
                parseQuestionAndUser(content.toString());


            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseQuestionAndUser(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root;
            try {
                root = objectMapper.readTree(jsonResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            for (JsonNode item : root.get("items")) {
                int question_id = item.get("question_id").asInt();
                int down_vote_count = item.get("down_vote_count").asInt();
                int up_vote_count = item.get("up_vote_count").asInt();
                int score = item.get("score").asInt();
                JsonNode tags = item.get("tags");

                saveQuestionToDatabase(question_id, down_vote_count, up_vote_count, score);

                saveTagsToDatabase(question_id, tags);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveQuestionToDatabase(int question_id, int down_vote_count, int up_vote_count, int score) throws Exception {
        String upsertQuery = "INSERT INTO questions (question_id, down_vote_count, up_vote_count, score) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (question_id) DO UPDATE " +
                "SET down_vote_count = EXCLUDED.down_vote_count, " +
                "    up_vote_count = EXCLUDED.up_vote_count, " +
                "    score = EXCLUDED.score";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(upsertQuery)) {

            pstmt.setInt(1, question_id);
            pstmt.setInt(2, down_vote_count);
            pstmt.setInt(3, up_vote_count);
            pstmt.setInt(4, score);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Record inserted or updated successfully for question_id: " + question_id);
            } else {
                System.out.println("No changes made for question_id: " + question_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveTagsToDatabase(int question_id, JsonNode tagsNode) {
        String insertTagQuery = "INSERT INTO tags (tag_name) VALUES (?) ON CONFLICT (tag_name) DO NOTHING";
        String selectTagIdQuery = "SELECT tag_id FROM tags WHERE tag_name = ?";
        String insertQuestionTagQuery = "INSERT INTO question_tag (question_id, tag_id) VALUES (?, ?) ON CONFLICT DO NOTHING";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            for (JsonNode tagNode : tagsNode) {
                String tagName = tagNode.asText();
                int tagId;

                // 插入到 tags 表
                try (PreparedStatement insertTagStmt = conn.prepareStatement(insertTagQuery)) {
                    insertTagStmt.setString(1, tagName);
                    insertTagStmt.executeUpdate();
                }

                // 查询 tag_id
                try (PreparedStatement selectTagIdStmt = conn.prepareStatement(selectTagIdQuery)) {
                    selectTagIdStmt.setString(1, tagName);
                    try (ResultSet rs = selectTagIdStmt.executeQuery()) {
                        if (rs.next()) {
                            tagId = rs.getInt("tag_id");

                            // 插入到 question_tag 表
                            try (PreparedStatement insertQuestionTagStmt = conn.prepareStatement(insertQuestionTagQuery)) {
                                insertQuestionTagStmt.setInt(1, question_id);
                                insertQuestionTagStmt.setInt(2, tagId);
                                insertQuestionTagStmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            System.out.println("Tags imported successfully for question_id: " + question_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
