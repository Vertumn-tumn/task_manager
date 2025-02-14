package org.yandex_practicum.util;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");

    private final String methodName;

    HttpMethod(String methodName) {
        this.methodName = methodName;
    }


    @Override
    public String toString() {
        return methodName;
    }
}
