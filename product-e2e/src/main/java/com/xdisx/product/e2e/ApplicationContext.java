package com.xdisx.product.e2e;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class,
        })
public class ApplicationContext {}
