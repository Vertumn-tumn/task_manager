package org.yandex_practicum.exceptions;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(IOException exception) {
        super(exception);
    }
}
