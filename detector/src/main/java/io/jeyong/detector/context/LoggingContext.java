package io.jeyong.detector.context;

import io.jeyong.detector.dto.RequestLogDto;

public final class LoggingContext {
    private final ThreadLocal<RequestLogDto> currentLoggingForm = ThreadLocal.withInitial(RequestLogDto::new);

    public RequestLogDto getCurrentLoggingForm() {
        return currentLoggingForm.get();
    }

    public void clearLoggingContext() {
        currentLoggingForm.remove();
    }
}
