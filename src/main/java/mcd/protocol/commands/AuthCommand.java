package mcd.protocol.commands;

import mcd.auth.TokenExchange;
import mcd.config.Config;
import mcd.protocol.Response;
import mcd.protocol.Client;

import java.io.IOException;

public class AuthCommand extends BasicCommand {
    /**
     * MCD configuration
     */
    protected Config config;

    /**
     * Exchange object for generating tokens.
     */
    protected TokenExchange exchange;

    public AuthCommand(Config config, TokenExchange exchange) {
        this.config = config;
        this.exchange = exchange;
    }

    @Override
    public boolean isPublic() {
        return true;
    }

    @Override
    public void run() {
        Response response = client.getResponse();
        response.setSuccessful(true);

        // If the user defined a password, we need to send them down a token
        // to sign and verify with.
        if (config.has("multicraft.password")) {
            String token = exchange.generateToken();
            response.put("token", token);
            client.getState().put("token", token);
            response.setMessage("token sent");
        }
        // Otherwise, they're good!
        else {
            client.getState().put("authed", true);
            response.setMessage("already authed");
        }

        try {
            client.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
