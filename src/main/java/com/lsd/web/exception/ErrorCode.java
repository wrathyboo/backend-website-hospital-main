package com.lsd.web.exception;

public interface ErrorCode {
    String ERROR_MESSAGE_DEFAULT_EN = "An error occurred. Please try again later";
    String ERROR_MESSAGE_DEFAULT_VN = "Đã có lỗi xảy ra. Vui lòng thử lại sau";
    String SUCCESS = "LSD-00-00";
    String UN_AUTHORIZED = "LSD-00-401";
    String FORBIDDEN = "LSD-00-403";
    String NOT_FOUND = "LSD-00-404";
    String INTERNAL_SERVER = "LSD-00-500";
    String GATEWAY_TIMEOUT = "LSD-00-504";
}
