package mcd;

import com.google.inject.Guice;
import com.google.inject.Injector;
import mcd.config.DaemonConfig;
import mcd.protocol.Server;
import org.apache.commons.cli.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CLI {

    private static final String serve = "s";
    private static final String dir = "d";
    private static final String unpack = "unpack";

    /**
     * Gets the options object for the cli.
     * @return an Options object
     */
    protected static Options getOptions() {
        Options options = new Options();
        options.addOption(serve, false, "Boots the daemon server.");
        options.addOption(unpack, false, "Unpacks config and directory structure for the first boot.");
        options.addOption(dir, true, "Sets the base directory to use for the daemon.");
        return options;
    }

    /**
     * Parses a returns a CommandLine for the input args.
     *
     * @param args input arguments from main()
     * @return the parsed CommandLine
     * @throws ParseException
     */
    protected static CommandLine parseArgs(String[] args) throws ParseException {
        return new BasicParser().parse(getOptions(), args);
    }

    protected static String getJarDir() {
        String path = CLI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return path;
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MCDInjector());
        DaemonConfig config = injector.getInstance(DaemonConfig.class);

        CommandLine options;
        try {
            options = parseArgs(args);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("mcd", getOptions());
            e.printStackTrace();
            return;
        }

        if (options.hasOption(dir)) {
            config.setBaseDirectory(options.getOptionValue(dir));
        } else {
            config.setBaseDirectory(getJarDir());
        }

        try {
            Bootstrap app = new Bootstrap();
            if (options.hasOption(unpack)) {
                app.unpack(config);
            } else if (options.hasOption(serve)) {
                app.boot(config, injector.getInstance(Server.class));
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
