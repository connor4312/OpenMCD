package mcd.protocol;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer implements Server {

    /**
     * port to listen on
     */
    protected int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public void listen() throws IOException {
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Client client = new TCPClient(server.accept());
            new Thread(client).start();
        }
    }
}
