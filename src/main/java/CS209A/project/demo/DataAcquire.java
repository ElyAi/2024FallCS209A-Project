package org.example.cs209project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;

public class DataAcquire {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/cs209";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "200325Wyx";

    public static void main(String[] args) {
        try {
            String apiKey = "rl_zyHKGUaHDXG8ezSdnxX2xfgsm"; // 如果有 API 密钥，请替换这里，否则省略 "&key=" 参数
            String urlString = "https://api.stackexchange.com/2.3/questions?page=1&pagesize=4&order=desc&sort=votes&tagged=java&site=stackoverflow&filter=withbody";
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
                User user = new User();
                JsonNode owner = item.get("owner");
                user.account_id = owner.get("account_id").asInt();
                user.user_id = owner.get("user_id").asInt();
                user.accept_rate = owner.path("accept_rate").asInt(0);
                user.user_type = owner.get("user_type").asText();
                user.display_name = owner.get("display_name").asText();
                user.link = owner.get("link").asText();
                user.reputation = owner.get("reputation").asInt();


                int question_id = item.get("question_id").asInt();
                String title = item.get("title").asText();
                String link = item.get("link").asText();
                int score = item.get("score").asInt();
                long creationDateUnix = item.get("creation_date").asLong();
                Timestamp creationDate = new Timestamp(creationDateUnix * 1000);
                boolean is_answered = item.get("is_answered").asBoolean();
                int view_count = item.get("view_count").asInt();
                int answer_count = item.get("answer_count").asInt();
                long lastEditDateUnix = item.path("last_edit_date").asLong(0L);
                Timestamp last_edit_date = new Timestamp(lastEditDateUnix * 1000);
                long lastActivityDateUnix = item.get("last_edit_date").asLong();
                Timestamp last_activity_date = new Timestamp(lastActivityDateUnix * 1000);
                int accepted_answer_id = item.path("accepted_answer_id").asInt(-1);
                String body = item.get("body").asText();
                String decodedBody = StringEscapeUtils.unescapeHtml4(body);

                saveUserToDatabase(user.account_id, user.user_id, user.accept_rate, user.user_type, user.display_name, user.link, user.reputation);

                saveQuestionToDatabase(question_id, title, link, score, creationDate, is_answered, view_count, answer_count, last_edit_date, last_activity_date, accepted_answer_id, user, decodedBody);

                fetchAnswersForQuestion(question_id);
                fetchCommentsForQuestion(question_id, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fetchAnswersForQuestion(int question_id) throws Exception {
        try {
            String apiKey = "rl_zyHKGUaHDXG8ezSdnxX2xfgsm"; // 如果有 API 密钥，请替换这里，否则省略 "&key=" 参数
            String urlString = "https://api.stackexchange.com/2.3/questions/" + question_id + "/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody";
            urlString += "&key=" + apiKey;

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
                parseAnswer(content.toString());
            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fetchCommentsForQuestion(int post_id, boolean answer) throws Exception {
        try {
            String apiKey = "rl_zyHKGUaHDXG8ezSdnxX2xfgsm"; // 如果有 API 密钥，请替换这里，否则省略 "&key=" 参数
            String urlString = "";
            if (answer) {
                urlString = "https://api.stackexchange.com/2.3/answers/" + post_id + "/comments?order=desc&sort=creation&site=stackoverflow&filter=withbody";
            } else {
                urlString = "https://api.stackexchange.com/2.3/questions/" + post_id + "/comments?order=desc&sort=creation&site=stackoverflow&filter=withbody";
            }
            urlString += "&key=" + apiKey;

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
                parseComment(content.toString(), answer);
            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseAnswer(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root;
            try {
                root = objectMapper.readTree(jsonResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            for (JsonNode item : root.get("items")) {
                User user = new User();
                JsonNode owner = item.get("owner");
                user.account_id = owner.get("account_id").asInt();
                user.user_id = owner.get("user_id").asInt();
                user.accept_rate = owner.path("accept_rate").asInt(0);
                user.user_type = owner.get("user_type").asText();
                user.display_name = owner.get("display_name").asText();
                user.link = owner.get("link").asText();
                user.reputation = owner.get("reputation").asInt();


                int question_id = item.get("question_id").asInt();
                long creationDateUnix = item.get("creation_date").asLong();
                Timestamp creationDate = new Timestamp(creationDateUnix * 1000);
                boolean is_accepted = item.get("is_accepted").asBoolean();
                long lastEditDateUnix = item.path("last_edit_date").asLong(0L);
                Timestamp last_edit_date = new Timestamp(lastEditDateUnix * 1000);
                long lastActivityDateUnix = item.get("last_edit_date").asLong();
                Timestamp last_activity_date = new Timestamp(lastActivityDateUnix * 1000);
                long communityOwnedDateUnix = item.path("community_owned_date").asLong(0L);
                Timestamp community_owned_date = new Timestamp(communityOwnedDateUnix * 1000);
                int answer_id = item.get("answer_id").asInt();
                int score = item.get("score").asInt();
                String body = item.get("body").asText();
                String decodedBody = StringEscapeUtils.unescapeHtml4(body);

                saveUserToDatabase(user.account_id, user.user_id, user.accept_rate, user.user_type, user.display_name, user.link, user.reputation);

                saveAnswerToDatabase(answer_id, question_id, community_owned_date, creationDate, is_accepted, last_activity_date, last_edit_date, user, decodedBody, score);

                fetchCommentsForQuestion(answer_id, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseComment(String jsonResponse, boolean answer) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root;
            try {
                root = objectMapper.readTree(jsonResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            for (JsonNode item : root.get("items")) {
                User user1 = new User();
                User user2 = new User();
                JsonNode owner = item.get("owner");
                JsonNode reply_to_user = item.get("reply_to_user");
                user1.account_id = owner.get("account_id").asInt();
                user1.user_id = owner.get("user_id").asInt();
                user1.accept_rate = owner.path("accept_rate").asInt(0);
                user1.user_type = owner.get("user_type").asText();
                user1.display_name = owner.get("display_name").asText();
                user1.link = owner.get("link").asText();
                user1.reputation = owner.get("reputation").asInt();
                if (reply_to_user != null) {
                    user2.account_id = reply_to_user.get("account_id").asInt();
                    user2.user_id = reply_to_user.get("user_id").asInt();
                    user2.accept_rate = reply_to_user.path("accept_rate").asInt(0);
                    user2.user_type = reply_to_user.get("user_type").asText();
                    user2.display_name = reply_to_user.get("display_name").asText();
                    user2.link = reply_to_user.get("link").asText();
                    user2.reputation = reply_to_user.get("reputation").asInt();
                    saveUserToDatabase(user2.account_id, user2.user_id, user2.accept_rate, user2.user_type, user2.display_name, user2.link, user2.reputation);
                } else {

                }


                long creationDateUnix = item.get("creation_date").asLong();
                Timestamp creationDate = new Timestamp(creationDateUnix * 1000);
                boolean edited = item.get("edited").asBoolean();
                int comment_id = item.get("comment_id").asInt();
                int post_id = item.get("post_id").asInt();
                int answer_id = -1;
                int question_id = -1;
                if (answer) {
                    answer_id = post_id;
                } else {
                    question_id = post_id;
                }
                int score = item.get("score").asInt();
                String body = item.get("body").asText();
                String decodedBody = StringEscapeUtils.unescapeHtml4(body);

                saveUserToDatabase(user1.account_id, user1.user_id, user1.accept_rate, user1.user_type, user1.display_name, user1.link, user1.reputation);


                saveCommentToDatabase(comment_id, answer_id, question_id, creationDate, edited, user1, user2, decodedBody, score);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveUserToDatabase(int account_id, int user_id, int accept_rate, String user_type, String display_name, String link, int reputation) throws Exception {
        String insertQuery = "INSERT INTO users (account_id, user_id, reputation, user_type, accept_rate, display_name, link) VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT (user_id) DO NOTHING";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false);  // 开启事务

            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setInt(1, account_id);
                pstmt.setInt(2, user_id);
                pstmt.setInt(3, reputation);
                pstmt.setString(4, user_type);
                pstmt.setInt(5, accept_rate);
                pstmt.setString(6, display_name);
                pstmt.setString(7, link);

                int affectedRows = pstmt.executeUpdate();

                // 如果插入成功，则提交事务
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long generatedId = generatedKeys.getLong(1);
                            //System.out.println("Inserted record with generated ID: " + generatedId);
                        }
                    }
                    conn.commit();  // 提交事务
                } else {
                    conn.rollback();  // 插入失败时回滚事务
                }
            } catch (Exception e) {
                conn.rollback();  // 出现异常时回滚事务
                throw e;
            } finally {
                conn.setAutoCommit(true);  // 恢复自动提交模式
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveQuestionToDatabase(int question_id, String title, String link, int score, Timestamp creationDate, boolean is_answered, int view_count, int answer_count, Timestamp last_activity_date, Timestamp last_edit_date, int accepted_answer_id, User owner, String body) throws Exception {
        String insertQuery = "INSERT INTO questions (question_id, title, link, is_answered, view_count, accepted_answer_id, answer_count, score, last_activity_date, creation_date, last_edit_date, owner_id, body) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (question_id) DO NOTHING";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, question_id);
            pstmt.setString(2, title);
            pstmt.setString(3, link);
            pstmt.setBoolean(4, is_answered);
            pstmt.setInt(5, view_count);
            if (accepted_answer_id == -1) {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(6, accepted_answer_id);
            }
            pstmt.setInt(7, answer_count);
            pstmt.setInt(8, score);
            pstmt.setTimestamp(9, last_activity_date);
            pstmt.setTimestamp(10, creationDate);
            pstmt.setTimestamp(11, last_edit_date);
            pstmt.setInt(12, owner.user_id);
            pstmt.setString(13, body);

            int affectedRows = pstmt.executeUpdate();

            // 获取自增 id
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        // System.out.println("Inserted record with generated ID: " + generatedId);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAnswerToDatabase(int answer_id, int question_id, Timestamp community_owned_date, Timestamp creationDate, boolean is_accepted, Timestamp last_activity_date, Timestamp last_edit_date, User owner, String body, int score) throws Exception {
        String insertQuery = "INSERT INTO answers (answer_id, is_accepted, score, community_owned_date, last_activity_date, creation_date, last_edit_date, question_id, owner_id, body) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (answer_id) DO NOTHING";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, answer_id);
            pstmt.setBoolean(2, is_accepted);
            pstmt.setInt(3, score);
            pstmt.setTimestamp(4, community_owned_date);
            pstmt.setTimestamp(5, last_activity_date);
            pstmt.setTimestamp(6, creationDate);
            pstmt.setTimestamp(7, last_edit_date);
            pstmt.setInt(8, question_id);
            pstmt.setInt(9, owner.user_id);
            pstmt.setString(10, body);

            int affectedRows = pstmt.executeUpdate();

            // 获取自增 id
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        // System.out.println("Inserted record with generated ID: " + generatedId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveCommentToDatabase(int comment_id, int answer_id, int question_id, Timestamp creationDate, boolean edited, User owner, User reply_to_user, String body, int score) throws Exception {
        String insertQuery = "INSERT INTO comments (comment_id, score, creation_date, owner_id, reply_to_id, question_id, answer_id, edited, body) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (comment_id) DO NOTHING";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, comment_id);
            pstmt.setInt(2, score);
            pstmt.setTimestamp(3, creationDate);
            pstmt.setInt(4, owner.user_id);
            if (reply_to_user.user_id == -1) {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(5, reply_to_user.user_id);
            }
            if (question_id == -1) {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(6, question_id);
            }
            if (answer_id == -1) {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(7, answer_id);
            }
            pstmt.setBoolean(8, edited);
            pstmt.setString(9, body);

            int affectedRows = pstmt.executeUpdate();

            // 获取自增 id
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        // System.out.println("Inserted record with generated ID: " + generatedId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class User {
    // The user_id is the per-site user id (in the above case, for Stack Overflow). The account_id is the global Stack Exchange account id (i.e. the id of your Stack Exchange account).
    public int account_id;
    public int user_id;
    public int reputation;
    public String user_type;
    public int accept_rate;
    public String display_name;
    public String link;

    public User() {
        user_id = -1;
    }
}