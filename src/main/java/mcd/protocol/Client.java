package mcd.protocol;

import java.io.IOException;

public interface Client extends Runnable {
    /**
     * Reads a string from the socket, blocking until a new one is available.
     * @return string of data
     */
    public String read() throws IOException;

    /**
     * Returns whether the socket is still connected and able to be read from.
     * @return whether the socket is alive
     */
    public boolean connected();

    /**
     * Writes data out to the socket.
     * @param data the data to write
     */
    public void write(String data) throws IOException;

    /**
     * Writes a response out to the socket.
     * @param data the response to write
     */
    public void write(Response data) throws IOException;

    /**
     * Returns a response appropriate to the socket type.
     * @return Response instance
     */
    public Response getResponse();
}
