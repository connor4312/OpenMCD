package mcd.protocol.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import mcd.protocol.Client;

/**
 * Reponsible for taking commands, parsing them, and returning an appropriate
 * Command instance.
 */
public class Factory {

    /**
     * Instance of the ioc injector
     */
    private Injector injector;

    @Inject
    public Factory(Injector injector) {
        this.injector = injector;
    }

    /**
     * Parses incoming command ata and returns an appropriate command.
     * @param data raw command data
     * @return command to handle it
     */
    public Command parse(Client client, String data) {
        Command command = null;
        switch (data) {
            case "auth":
                command = injector.getInstance(AuthCommand.class);
                break;
            case "version":
                command = injector.getInstance(VersionCommand.class);
                break;
        }

        if (command == null) {
            throw new RuntimeException("Unknown command " + data);
        } else {
            command.setClient(client);
            command.setData(data);
            return command;
        }
    }
}
