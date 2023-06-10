package com.lsd.web.exception;

import com.lsd.lib.exception.LsdException;

public class WebException extends LsdException {
    public WebException(String errorCode) {
        super(errorCode, ErrorMessageLoader.getMessage(errorCode));
    }
    public WebException(String errorCode, Object data) {
        super(errorCode, ErrorMessageLoader.getMessage(errorCode), data);
    }
}
