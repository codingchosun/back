package com.codingchosun.backend.exception.notfoundfromdb;

public class ImageNotFoundFromDB extends EntityNotFoundFromDB{
    public ImageNotFoundFromDB(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageNotFoundFromDB(String message) {
        super(message);
    }
}
