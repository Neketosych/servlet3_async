package com.home.spring.servlet3_async.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class AsyncController {

    @RequestMapping("/async")
    @ResponseBody
    public String async() {
        log.info("Call async, thread name in controller: {}", Thread.currentThread().getName());
        return "result from async";
    }

    @RequestMapping("/async2")
    @ResponseBody
    public String testAsync() {
        log.info("Call async2, thread name in controller: {}", Thread.currentThread().getName());
        return "result from async2";
    }
}