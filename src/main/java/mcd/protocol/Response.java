package mcd.protocol;

import java.util.Map;

public interface Response {
    /**
     * Sets the response success status.
     * @param didSuccess true for success, false for error
     */
    public void setSuccessful(boolean didSuccess);

    /**
     * Sets an additional message to be sent after the response type.
     * @param message the message to send
     */
    public void setMessage(String message);

    /**
     * Adds a new data entry to the output.
     * @param entry the entry to be encoded
     */
    public void addEntry(Map<String, String> entry);
}
