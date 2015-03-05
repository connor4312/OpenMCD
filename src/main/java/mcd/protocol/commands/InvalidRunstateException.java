package mcd.protocol.commands;

public class InvalidRunstateException extends Exception {
    public InvalidRunstateException(String message) {
        super(message);
    }
}
