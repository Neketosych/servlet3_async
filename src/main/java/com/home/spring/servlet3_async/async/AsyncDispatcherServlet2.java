package com.home.spring.servlet3_async.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet(urlPatterns = "/async2/*", asyncSupported = true, name = "asyncServlet2")
public class AsyncDispatcherServlet2 extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("thread name before startAsync: " + Thread.currentThread().getName());
        AsyncContext ac = request.startAsync();
        log.info("thread name after startAsync: " + Thread.currentThread().getName());
        ac.start(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("thread name in runnable: " + Thread.currentThread().getName());
                    AsyncDispatcherServlet2.super.doDispatch(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ac.complete();
            }
        });

    }
}
