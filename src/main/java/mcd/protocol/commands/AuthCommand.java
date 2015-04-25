package mcd.protocol.commands;

import com.google.inject.Inject;
import mcd.auth.TokenExchange;
import mcd.config.DaemonConfig;
import mcd.config.loader.Config;
import mcd.protocol.Client;
import mcd.protocol.Response;

import java.util.HashMap;
import java.util.Map;

public class AuthCommand extends BasicCommand {
    /**
     * MCD configuration
     */
    protected DaemonConfig config;

    /**
     * Exchange object for generating tokens.
     */
    protected TokenExchange exchange;

    @Inject
    public AuthCommand(DaemonConfig config, TokenExchange exchange) {
        this.config = config;
        this.exchange = exchange;
    }

    @Override
    public void run(Client client, String data) {
        Response response = client.getResponse();
        response.setSuccessful(true);

        // If the client is not unauthenticated, they're good
        if (client.getState().isAuthenticated()) {
            response.setMessage("already authed");
        }
        // If the user defined a password, we need to send them down a token
        // to sign and verify with.
        else if (config.has("multicraft.password")) {
            String token = exchange.generateToken();
            Map<String, String> out = new HashMap<>();
            out.put("token", token);
            response.addEntry(out);
            response.setMessage("token sent");
            client.getState().setAuthToken(token);
        }
        // Otherwise, just say they don't need a password. Multicraft wants
        // to provide "none" as a password by default, but this seems like
        // stupidity -- er, excuse me -- security through obscurity. So
        // ignore that.
        else {
            client.getState().setAuthenticated(true);
            response.setMessage("no password necessary");
        }

        // write the response down to the socket.
        client.write(response);
    }

    @Override
    public boolean matches(String command) {
        return command.equals("auth");
    }

    @Override
    public void assertRunsOn(Client client) throws InvalidRunstateException {}
}
