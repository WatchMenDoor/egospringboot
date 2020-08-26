package com.ego.commons.exception;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.commons.exception
 * @version: 1.0
 */
public class ItemNumNotEnoughException extends RuntimeException {
    public ItemNumNotEnoughException(String message) {
        super(message);
    }
}
