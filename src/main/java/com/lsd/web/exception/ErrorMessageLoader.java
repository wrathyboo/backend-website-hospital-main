package com.lsd.web.exception;

import com.lsd.lib.exception.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
public class ErrorMessageLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMessageLoader.class);
    private static Map<String, Message> errorMessages;
    private static final Message DEFAULT_MSG = new Message()
            .withEn(ErrorCode.ERROR_MESSAGE_DEFAULT_EN)
            .withVn(ErrorCode.ERROR_MESSAGE_DEFAULT_VN);

    public ErrorMessageLoader() {
        try {
            Charset charset = Charset.forName("UTF-8");

            Properties msgEN = new Properties();
            msgEN.load(new InputStreamReader(getClass().getResourceAsStream("/message_en.properties"), charset));
            Properties msgVN = new Properties();
            msgVN.load(new InputStreamReader(getClass().getResourceAsStream("/message_vn.properties"), charset));

            errorMessages = msgEN.entrySet().stream().collect(
                    Collectors.toMap(
                            x -> x.getKey().toString(),
                            e -> new Message().withEn(e.getValue().toString()).withVn(msgVN.getProperty(e.getKey().toString()))
                    )
            );
        } catch (Exception ex) {
            LOGGER.error("FAILED to load message with exception {}", ex);
        }
    }

    public static Map<String, Message> getErrorMessages() {
        return errorMessages;
    }

    public static Message getMessage(String errorCode) {
        try {
            Message message =errorMessages.get(errorCode);
            if (!Objects.isNull(message))
                return message;
            LOGGER.warn("Not found error message with error code {}", errorCode);
        } catch (Exception ex) {
            LOGGER.error("Get message error with exception {}", ex);
        }
        return DEFAULT_MSG;
    }
}

