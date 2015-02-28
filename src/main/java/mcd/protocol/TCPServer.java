package mcd.protocol;

import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Server {

    /**
     * Instance of the ioc injector
     */
    private Injector injector;

    @Inject
    public TCPServer(Injector injector) {
        this.injector = injector;
    }

    public void listen(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();
            TCPClient client = injector.getInstance(TCPClient.class);
            client.setSocket(socket);
            new Thread(client).start();
        }
    }
}
