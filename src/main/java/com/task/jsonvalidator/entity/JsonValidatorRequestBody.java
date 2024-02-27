package com.task.jsonvalidator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class JsonValidatorRequestBody {
    @Getter
    private String schemaSource;
    @Getter
    private Object jsonObject;
}