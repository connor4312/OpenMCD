package mcd.protocol.commands;

import mcd.protocol.ClientState;
import mcd.protocol.Response;
import mcd.protocol.Client;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;

public class VersionCommand extends BasicCommand {

    public VersionCommand() {
        super();

        this.runsUnder = new ArrayList<>();
        this.runsUnder.add(ClientState.AUTHENTICATED);
    }

    protected String getTimestamp() {
        float now = (float) Instant.now().toEpochMilli() / 1000;
        DecimalFormat format = new DecimalFormat("#.##");

        return format.format(now);
    }

    @Override
    public void run() {
        try {
            Response response = client.getResponse();
            response.setSuccessful(true);
            response.put("info", "");
            response.put("version", "1.8.2");
            response.put("remote", "1.8.2");
            response.put("time", getTimestamp());
            client.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
