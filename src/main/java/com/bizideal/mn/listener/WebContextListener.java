package com.bizideal.mn.listener;

import com.bizideal.mn.server.NettyServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 10:21
 * @version: 1.0
 * @Description:
 */
@WebListener
public class WebContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new Thread() {
            @Override
            public void run() {
                new NettyServer().start();
            }
        }.start();
        ;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
