package mcd.protocol;

import com.google.inject.Inject;
import com.google.inject.Injector;
import mcd.auth.ConnectionAuthorizer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Server {

    /**
     * Instance of the ioc injector
     */
    private Injector injector;

    /**
     * Authorization instance used to check if a connection is good.
     */
    private ConnectionAuthorizer authorizer;

    @Inject
    public TCPServer(Injector injector, ConnectionAuthorizer authorizer) {
        this.injector = injector;
        this.authorizer = authorizer;
    }

    public void listen(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();
            TCPClient client = injector.getInstance(TCPClient.class);
            client.setSocket(socket);
            handleClient(client);
        }
    }

    /**
     * Processes an incoming connection and dispatches it on a thread.
     * @param socket the incoming socket
     */
    protected void handleClient(TCPClient client) {
        if (!authorizer.authorize(client)) {
            client.close();
            return;
        }

        new Thread(client).start();
    }
}
