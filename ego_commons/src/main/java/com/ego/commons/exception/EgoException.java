package com.ego.commons.exception;

/**
 * @Auther: liuxw
 * @Date: 2019/8/9
 * @Description: com.ego.commons.exception
 * @version: 1.0
 */
public class EgoException extends RuntimeException {
    public EgoException() {
    }

    public EgoException(String message) {
        super(message);
    }
}
