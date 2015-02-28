package mcd;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import mcd.config.Config;
import mcd.protocol.Server;
import org.apache.commons.io.FilenameUtils;
import java.io.IOException;

@Singleton
public class OpenMCD {

    /**
     * The current MCD config.
     */
    protected Config config;

    /**
     * The network server.
     */
    protected Server server;

    @Inject
    public OpenMCD(Config config, Server server) throws IOException {
        this.config = config;
        this.server = server;
    }

    /**
     * Gets the daemon's base directory.
     * @return a file path
     */
    public String getBaseDirectory() {
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return FilenameUtils.getFullPath(jarPath);
    }

    /**
     * Attempts to load the MCD config. Expects it to be beside the jar
     */
    protected void loadConfig() throws IOException {
        config.load(this.getBaseDirectory());
    }

    /**
     * Starts up the MCD daemon.
     */
    public void boot() throws IOException {
        loadConfig();

        try {
            int port = Integer.parseInt((String) config.get("multicraft.port"));
            server.listen(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MCDInjector());
        OpenMCD app = injector.getInstance(OpenMCD.class);

        try {
            app.boot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
