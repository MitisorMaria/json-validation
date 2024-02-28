package com.task.jsonvalidator.util;

/**
 * Class that holds constant values used throughout the application.
 */
public class Constants {
    public static final String VALID_RESPONSE = "The JSON object is valid according to the schema you provided.";
    public static final String INVALID_RESPONSE = "The JSON object is invalid according to the schema you provided.";
    public static final String PROCESSING_ERROR_RESPONSE = "Could not process JSON object. Please check the format.";
    public static final String ERROR_RESPONSE_EMPTY_FILE = "Failed to store empty file.";
    public static final String ERROR_RESPONSE = "The file provided cannot be stored.";
    public static final String CURRENT_DIRECTORY = "user.dir";
    public static final String JSON_EXTENSION = ".json";
    public static final Character[] INVALID_WINDOWS_SPECIFIC_CHARS = {'"', '*', '<', '>', '?', '|'};
    public static final Character[] INVALID_UNIX_SPECIFIC_CHARS = {'\000', '*'};

    public static Character[] getInvalidCharsByOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return INVALID_WINDOWS_SPECIFIC_CHARS;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return INVALID_UNIX_SPECIFIC_CHARS;
        } else {
            return new Character[]{};
        }
    }
}