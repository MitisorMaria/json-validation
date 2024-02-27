package com.task.jsonvalidator.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration("validatorProperties")
@PropertySource("classpath:application.properties")
public class ValidatorProperties {

    @Value("${savePath}")
    private String savePath;

    public String getSavePath() {
        return savePath;
    }
}