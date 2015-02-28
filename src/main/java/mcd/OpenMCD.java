package mcd;

import mcd.config.Config;
import mcd.config.IniConfig;
import mcd.protocol.TCPServer;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class OpenMCD {

    /**
     * The current MCD config.
     */
    protected Config config;

    /**
     * Singleton instance of OpenMCD
     */
    private static OpenMCD instance;

    /**
     * Returns the current directory the jar is executing in.
     * @return a file path
     */
    public String cwd() {
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return FilenameUtils.getFullPath(jarPath);
    }

    /**
     * Attempts to load the MCD config. Expects it to be beside the jar
     */
    public void loadConfig() throws IOException {
        config = new IniConfig();
    }

    /**
     * Gets the config object currently loaded.
     * @return the config object
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Returns an instance of the OpenMCD singleton.
     * @return openmcd instance
     */
    public static OpenMCD instance() {
        if (instance == null) {
            instance = new OpenMCD();
        }

        return instance;
    }

    public static void main(String[] args) {
        try {
            OpenMCD app = instance();
            app.loadConfig();
            System.out.println(app.getConfig().get("foo"));
            new TCPServer(25465).listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
