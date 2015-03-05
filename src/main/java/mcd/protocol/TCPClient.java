package mcd.protocol;

import com.google.inject.Inject;
import mcd.protocol.commands.Command;
import mcd.protocol.commands.Factory;
import mcd.protocol.commands.InvalidRunstateException;

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

    /**
     * State held on the client.
     */
    protected ClientState state;

    @Inject
    public TCPClient(Factory factory, ClientState state) throws IOException {
        this.factory = factory;
        this.state = state;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public ClientState getState() {
        return state;
    }

    @Override
    public String read() {
        try {
            if (input == null) {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }

            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean connected() {
        return socket.isConnected();
    }

    @Override
    public void write(String data) {
        try {
            if (output == null) {
                output = new DataOutputStream(socket.getOutputStream());
            }

            output.writeBytes(data);
        } catch (IOException ignored) {
            // happens when the socket closes. Do nothing.
        }
    }

    @Override
    public void write(Response response) {
        System.out.println(" ==== OUT ==== \n" + response.toString());
        write(response.toString());
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {}
    }

    @Override
    public String getRemoteAddress() {
        return socket.getInetAddress().toString().substring(1);
    }

    @Override
    public Response getResponse() {
        return new TCPResponse();
    }

    @Override
    public void run() {
        String input;

        while ((input = read()) != null) {
            System.out.println(" ==== IN ==== \n" + input);
            // Attempt to dispatch the command.
            try {
                Command command = factory.parse(input);
                command.assertRunsOn(this);
                command.run(this, input);
            } catch (InvalidRunstateException e) {
                Response r = getResponse();
                r.setMessage(e.getMessage());
                r.setSuccessful(false);
                write(r);
            }
        }

        close();
    }
}
