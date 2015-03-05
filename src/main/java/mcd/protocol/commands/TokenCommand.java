package mcd.protocol.commands;

import com.google.inject.Inject;
import mcd.auth.TokenExchange;
import mcd.config.Config;
import mcd.protocol.Client;
import mcd.protocol.Response;

public class TokenCommand extends BasicCommand {

    /**
     * Exchange object for generating tokens.
     */
    protected TokenExchange exchange;

    protected static String prefix = "codeword: ";

    @Inject
    public TokenCommand(Config config, TokenExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void run(Client client, String data) throws InvalidRunstateException {
        String token = client.getState().getAuthToken();
        if (token == null) {
            throw new InvalidRunstateException("incorrect authorization");
        }

        String signature = data.substring(prefix.length()).trim();
        Response response = client.getResponse();
        if (exchange.verify(token, signature)) {
            client.getState().setAuthenticated(true);
            response.setMessage("client authorized - welcome");
            response.setSuccessful(true);
        } else {
            response.setMessage("incorrect authorization");
            response.setSuccessful(false);
        }

        client.write(response);
    }

    @Override
    public void assertRunsOn(Client client) {

    }

    @Override
    public boolean matches(String command) {
        return command.startsWith(prefix);
    }
}
