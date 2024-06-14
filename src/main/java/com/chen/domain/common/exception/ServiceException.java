package com.chen.domain.common.exception;

public class ServiceException extends RuntimeException{
    static final long serialVersionUID = -7034897190745766939L;

    public ServiceException(String message) {
        super(message);
    }
}
