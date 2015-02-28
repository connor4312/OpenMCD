package mcd.protocol.commands;

import mcd.protocol.Response;
import mcd.protocol.Client;

import java.io.IOException;

public class AuthCommand extends BasicCommand {

    @Override
    public void run() {
        try {
            Response response = client.getResponse();
            response.setSuccessful(true);
            response.setMessage("already authed");
            client.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
