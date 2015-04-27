package mcd.protocol.commands;

import mcd.protocol.Client;
import mcd.protocol.Response;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> out = new HashMap<>();
        out.put("info", "");
        out.put("version", "1.8.2");
        out.put("remote", "1.8.2");
        out.put("time", getTimestamp());
        response.addEntry(out);

        client.write(response);
    }

    @Override
    public boolean matches(String command) {
        return command.equals("version");
    }
}
