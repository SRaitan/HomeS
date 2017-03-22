package com.company;

/**
 * Created by hackeru on 3/21/2017.
 */
class InvalidCipherKeyException extends NumberFormatException {
    InvalidCipherKeyException(String message) {
        super("Your cipher key seems to be invalid: " + message);
    }
}
