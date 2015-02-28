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
}
