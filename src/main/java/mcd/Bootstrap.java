package mcd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import mcd.config.DaemonConfig;
import mcd.protocol.Server;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Singleton
public class Bootstrap {

    /**
     * Starts up the MCD server.
     */
    @Inject
    public void boot(DaemonConfig config, Server server) throws IOException {
        int port = Integer.parseInt((String) config.get("multicraft.port"));
        server.listen(port);
    }

    /**
     * Unpacks resources to the base directory.
     */
    @Inject
    public void unpack(DaemonConfig config) throws IOException, URISyntaxException {
        new Unpacker().to(new File(config.getBaseDirectory()));
    }
}
