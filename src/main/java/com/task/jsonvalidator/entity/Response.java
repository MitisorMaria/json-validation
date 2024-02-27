package com.task.jsonvalidator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Getter
    private String message;
    @Getter
    private boolean isValid;
}