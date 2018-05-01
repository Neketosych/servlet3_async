package com.home.spring.servlet3_async.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Slf4j
@WebServlet(urlPatterns = "/async/*", asyncSupported = true, name = "asyncServlet")
public class AsyncDispatcherServlet extends DispatcherServlet {
    /**
     * Since the actual work is to be delegated to another thread, we should have a thread pool
     * implementation.
     */
    private ExecutorService exececutor;
    private static final int NUM_ASYNC_TASKS = 15;
    private static final long TIME_OUT = 10 * 1000;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        exececutor = Executors.newFixedThreadPool(NUM_ASYNC_TASKS);
    }

    @Override
    public void destroy() {
        exececutor.shutdownNow();
        super.destroy();
    }

    @Override
    protected void doDispatch(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        log.info("thread name before startAsync: " + Thread.currentThread().getName());
        final AsyncContext ac = request.startAsync(request, response);
        log.info("thread name after startAsync: " + Thread.currentThread().getName());
//        ac.setTimeout(TIME_OUT);
        FutureTask task = new FutureTask<>(new Runnable() {

            @Override
            public void run() {
                try {
                    AsyncDispatcherServlet.super.doDispatch(request, response);
                    log.info("thread name in runnable: " + Thread.currentThread().getName());
                } catch (Throwable t) {
                    log.error("Error in async request", t);
                }
                ac.complete();
            }
        }, null);

        ac.addListener(new AsyncDispatcherServletListener(task));
        exececutor.execute(task);
    }
}