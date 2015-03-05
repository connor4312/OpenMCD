package mcd.protocol.commands;

import mcd.protocol.Client;

abstract public class BasicCommand implements Command {
    @Override
    public void assertRunsOn(Client client) throws InvalidRunstateException {
        if (!client.getState().isAuthenticated()) {
            throw new InvalidRunstateException("client not authorized");
        }
    }
}
