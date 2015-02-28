package mcd.protocol.commands;

import mcd.protocol.Client;

/**
 * Reponsible for taking commands, parsing them, and returning an appropriate
 * Command instance.
 */
public class Factory {
    public Command parse(Client client, String data) {
        switch (data) {
            case "auth": return new AuthCommand(client, data);
            case "version": return new VersionCommand(client, data);
        }

        throw new RuntimeException("Unknown command " + data);
    }
}
