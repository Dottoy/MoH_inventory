package org.openmrs.module.Quiz.exception;

import org.openmrs.api.APIException;

public class TestReceiptException extends APIException {

    public TestReceiptException() {
    }

    public TestReceiptException(String message) {
        super(message);
    }

    public TestReceiptException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestReceiptException(Throwable cause) {
        super(cause);
    }

    public TestReceiptException(String messageKey, Object[] parameters) {
        super(messageKey, parameters);
    }

    public TestReceiptException(String messageKey, Object[] parameters, Throwable cause) {
        super(messageKey, parameters, cause);
    }
}
