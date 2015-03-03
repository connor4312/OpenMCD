package mcd.protocol.commands;

import mcd.protocol.Client;
import mcd.protocol.ClientState;

import java.util.List;

public interface Command extends Runnable {
    /**
     * Returns the command name as sent from the panel.
     * @return the command name
     */
    public String getName();

    /**
     * Returns the socket the command was sent on.
     * @return a TCP socket object
     */
    public Client getClient();

    /**
     * Sets the command data
     * @param data raw command data
     */
    public void setData(String data);

    /**
     * The associated net client.
     * @param client net client
     */
    public void setClient(Client client);

    /**
     * Returns an array of states under which the command may be run.
     * @return array of ClientStates
     */
    public List<ClientState> runsUnder();
}
