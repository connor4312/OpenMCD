package mcd.protocol.commands;

import com.google.inject.Inject;
import mcd.auth.TokenExchange;
import mcd.config.Config;
import mcd.protocol.ClientState;
import mcd.protocol.Response;

import java.io.IOException;
import java.util.ArrayList;

public class AuthCommand extends BasicCommand {
    /**
     * MCD configuration
     */
    protected Config config;

    /**
     * Exchange object for generating tokens.
     */
    protected TokenExchange exchange;

    @Inject
    public AuthCommand(Config config, TokenExchange exchange) {
        this.config = config;
        this.exchange = exchange;

        this.runsUnder = new ArrayList<>();
        this.runsUnder.add(ClientState.CONNECTED);
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
            response.setMessage("token sent");
            client.setState(ClientState.AUTHENTICATING);
        }
        // Otherwise, they're good!
        else {
            client.setState(ClientState.AUTHENTICATED);
            response.setMessage("already authed");
        }

        // write the response down to the socket.
        try {
            client.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
