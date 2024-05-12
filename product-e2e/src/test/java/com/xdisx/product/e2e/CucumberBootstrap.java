package com.xdisx.product.e2e;

import com.xdisx.product.e2e.product.steps.context.SharedProductContext;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@CucumberContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ApplicationContext.class)
public class CucumberBootstrap {
    @Autowired
    protected SharedProductContext productCreationContext;
}
