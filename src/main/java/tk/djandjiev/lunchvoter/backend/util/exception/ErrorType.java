package tk.djandjiev.lunchvoter.backend.util.exception;

public enum ErrorType {
    APP_ERROR("Application error"),
    DATA_NOT_FOUND("Data not found"),
    DATA_ERROR("Data error"),
    VALIDATION_ERROR("Validation error");

    private final String errorCode;

    ErrorType(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
