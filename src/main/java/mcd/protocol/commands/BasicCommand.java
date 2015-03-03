package mcd.protocol.commands;

import mcd.protocol.Client;
import mcd.protocol.ClientState;

import java.util.List;

abstract public class BasicCommand implements Command {
    /**
     * name of the command
     */
    protected String name;
    /**
     * associated socket
     */
    protected Client client;

    /**
     * raw command data
     */
    protected String data;

    /**
     * List of client states under which the command may be run.
     */
    protected List<ClientState> runsUnder;

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public List<ClientState> runsUnder() {
        return runsUnder;
    }
}
