package mcd.protocol.commands;

import mcd.protocol.Client;

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
     * Returns whether the command can be accessed without having to
     * authenticate first.
     * @return true if public, false otherwise
     */
    public boolean isPublic();
}
