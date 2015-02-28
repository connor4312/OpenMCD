package mcd.protocol;

import java.io.IOException;

public interface Server {
    /**
     * Starts the server listening for connections.
     * @param port the port to listen on
     * @throws IOException
     */
    public void listen(int port) throws IOException;
}
