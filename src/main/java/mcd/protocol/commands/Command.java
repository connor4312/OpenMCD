package mcd.protocol.commands;

import mcd.protocol.Client;

public interface Command {

    /**
     * Dispatches a command to run.
     * @param client the associated network client
     * @param data the raw command string
     */
    public void run(Client client, String data) throws InvalidRunstateException;

    /**
     * Checks whether the client is in a fit state to run this command.
     * If not, it throws an exception.
     */
    public void assertRunsOn(Client client) throws InvalidRunstateException;

    /**
     * Returns whether the command matches and should be run from
     * the given string.
     * @param command the original command string
     * @return true if it should be run
     */
    public boolean matches(String command);
}
