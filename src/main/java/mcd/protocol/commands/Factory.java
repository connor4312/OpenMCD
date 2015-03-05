package mcd.protocol.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Responsible for taking commands, parsing them, and returning an appropriate
 * Command instance.
 */
public class Factory {

    /**
     * List of commands which may be run.
     */
    private Command[] commands;

    @Inject
    public Factory(Injector injector) {
        this.commands = new Command[] {
                injector.getInstance(AuthCommand.class),
                injector.getInstance(VersionCommand.class),
                injector.getInstance(TokenCommand.class)
        };
    }

    /**
     * Parses incoming command ata and returns an appropriate command.
     * @param data raw command data
     * @return command to handle it
     */
    public Command parse(String data) throws InvalidRunstateException {
        Command command = null;
        for (Command c : commands) {
            if (c.matches(data)) {
                command = c;
                break;
            }
        }

        if (command == null) {
            throw new InvalidRunstateException("Unknown command " + data);
        } else {
            return command;
        }
    }
}
