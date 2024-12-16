package com.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    @GetMapping("/")
    public String home() {
        return "html/home";
    }

    @GetMapping("/java_topic")
    public String java_topic() {
        return "html/java_topic";
    }

    @GetMapping("/user_engagement")
    public String user_engagement() {
        return "html/user_engagement";
    }

    @GetMapping("/common_mistake")
    public String common_mistake() {
        return "html/common_mistake";
    }

    @GetMapping("/answer_quality")
    public String answer_quality() {
        return "html/answer_quality";
    }
}
