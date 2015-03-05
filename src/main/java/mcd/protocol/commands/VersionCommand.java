package mcd.protocol.commands;

import mcd.protocol.Response;
import mcd.protocol.Client;

import java.text.DecimalFormat;
import java.time.Instant;

public class VersionCommand extends BasicCommand {

    public VersionCommand() {
        super();
    }

    /**
     * Returns the current timestamp in the odd decimal format
     * that Multicraft likes.
     * @return the timestamp
     */
    protected String getTimestamp() {
        float now = (float) Instant.now().toEpochMilli() / 1000;
        DecimalFormat format = new DecimalFormat("#.##");

        return format.format(now);
    }

    @Override
    public void run(Client client, String string) {
        Response response = client.getResponse();
        response.setSuccessful(true);
        response.put("info", "");
        response.put("version", "1.8.2");
        response.put("remote", "1.8.2");
        response.put("time", getTimestamp());
        client.write(response);
    }

    @Override
    public boolean matches(String command) {
        return command.equals("version");
    }
}
