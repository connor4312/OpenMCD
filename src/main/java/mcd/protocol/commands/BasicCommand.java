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

    /**
     * raw command data
     */
    protected String data;

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
    public boolean isPublic() {
        return false;
    }
}
