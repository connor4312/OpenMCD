package mcd.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TCPResponse implements Response {

    /**
     * Whether the request was successful.
     */
    protected boolean successful;

    /**
     * The optional message to send after the response code.
     */
    protected String message = "";

    /**
     * The list of data entries to response with.
     */
    protected List<Map<String, String>> entries;

    public TCPResponse() {
        this.entries = new ArrayList<>();
    }

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
        for (Map<String, String> entry : entries) {
            builder.append(" ");

            for (Map.Entry<String, String> e : entry.entrySet()) {
                builder.append(escape(e.getKey())).append(" :");
                builder.append(escape(e.getValue())).append(" :");
            }

            builder.append("\n");
        }

        return builder;
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
    public void addEntry(Map<String, String> entry) {
        entries.add(entry);
    }

    @Override
    public String toString() {
        return serialize().append(getClosing()).toString();
    }
}
