package com.task.jsonvalidator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response object containing information regarding the validity of the JSON object, both in text message and in boolean
 * form.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Getter private String message;
    @Getter private boolean isValid;

    @Override
    public boolean equals(Object other) {
        if (this.getClass().equals(other.getClass())) {
            if (this.getMessage().equals(((Response) other).getMessage()) && this.isValid == ((Response) other).isValid) {
                return true;
            }
        }
        return false;
    }
}