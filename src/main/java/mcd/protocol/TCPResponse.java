package mcd.protocol;

import java.util.HashMap;
import java.util.Map;

public class TCPResponse extends HashMap<String, Object> implements Response {

    /**
     * Whether the request was successful.
     */
    protected boolean successful;

    /**
     * The optional message to send after the response code.
     */
    protected String message = "";

    /**
     * Sanitizes the values for use in serialization.
     * @param value value to sanitize
     * @return escaped value
     */
    protected String escape(String value) {
        return value.replace(" :", " \\:");
    }

    /**
     * Serializes the map into a string that the multicraft panel can read.
     * @return serialized values
     */
    protected StringBuilder serialize() {
        StringBuilder builder = new StringBuilder(" ");
        for (Map.Entry<String, Object> entry : entrySet()) {
            builder.append(escape(entry.getKey())).append(" :");
            builder.append(escape(entry.getValue().toString())).append(" :");
        }

        return builder.append("\n");
    }

    /**
     * Gets the closing >OK or >ERROR tag.
     * @return the closing tag
     */
    protected StringBuilder getClosing() {
        StringBuilder builder = new StringBuilder(">");
        if (successful) {
            builder.append("OK");
        } else {
            builder.append("ERROR");
        }

        if (message.length() > 0) {
            builder.append(" - ").append(message);
        }

        return builder.append("\n");
    }

    @Override
    public void setSuccessful(boolean didSuccess) {
        successful = didSuccess;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return serialize().append(getClosing()).toString();
    }
}
