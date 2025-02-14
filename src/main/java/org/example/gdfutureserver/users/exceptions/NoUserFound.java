package org.example.gdfutureserver.users.exceptions;

public class NoUserFound extends RuntimeException {
    public NoUserFound(String message) {
        super(message);
    }
}
