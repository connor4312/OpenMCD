package mcd.protocol;

import com.google.inject.Inject;
import mcd.protocol.commands.Command;
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

    /**
     * State held on the client.
     */
    protected ClientState state;

    @Inject
    public TCPClient(Factory factory) throws IOException {
        this.factory = factory;
        this.state = ClientState.CONNECTED;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public ClientState getState() {
        return state;
    }

    @Override
    public void setState(ClientState state) {
        this.state = state;
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
            // Attempt to dispatch the command.
            Command command = factory.parse(this, input);
            if (canRunCommand(command)) {
                command.run();
            }
        }
    }

    /**
     * Returns whether or not the command can currently be run.
     * @param command the command to check
     * @return true if it can be run
     */
    protected boolean canRunCommand(Command command) {
        // If the command isn't public and we're not authed, false!
        if (!command.runsUnder().contains(state)) {
            return false;
        }

        // True otherwise
        return true;
    }
}
