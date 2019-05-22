package com.cfysu.springboot.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author canglong
 * @Date 2019/3/27
 */
public class DemoInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("==========initialize=========");
    }
}
