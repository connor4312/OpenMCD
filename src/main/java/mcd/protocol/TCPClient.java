package mcd.protocol;

import com.google.inject.Inject;
import mcd.protocol.commands.Factory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient implements Client {
    /**
     * The associated client socket.
     */
    protected java.net.Socket socket;

    /**
     * Input stream from the socket.
     */
    protected BufferedReader input;

    /**
     * Output stream to the socket.
     */
    protected DataOutputStream output;

    /**
     * Factory for making new commands.
     */
    protected Factory factory;

    @Inject
    public TCPClient(Factory factory) throws IOException {
        this.factory = factory;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String read() throws IOException {
        if (input == null) {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        return input.readLine();
    }

    @Override
    public boolean connected() {
        return socket.isConnected();
    }

    @Override
    public void write(String data) throws IOException {
        if (output == null) {
            output = new DataOutputStream(socket.getOutputStream());
        }

        output.writeBytes(data);
    }

    @Override
    public void write(Response response) throws IOException {
        System.out.println(response.toString());
        write(response.toString());
    }

    @Override
    public Response getResponse() {
        return new TCPResponse();
    }

    @Override
    public void run() {
        while (connected()) {
            String input;

            try {
                input = read();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            factory.parse(this, input).run();
        }
    }
}
