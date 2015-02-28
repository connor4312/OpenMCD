package mcd.protocol.commands;

import mcd.protocol.Client;

abstract public class BasicCommand implements Command {
    /**
     * name of the command
     */
    protected String name;
    /**
     * associated socket
     */
    protected Client client;

    public BasicCommand(Client client, String name) {
        this.name = name;
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Client getClient() {
        return client;
    }
}
