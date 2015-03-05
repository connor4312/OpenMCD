package mcd.protocol;

public interface Client extends Runnable {
    /**
     * Reads a string from the socket, blocking until a new one is available.
     * @return string of data
     */
    public String read();

    /**
     * Returns whether the socket is still connected and able to be read from.
     * @return whether the socket is alive
     */
    public boolean connected();

    /**
     * Writes data out to the socket.
     * @param data the data to write
     */
    public void write(String data);

    /**
     * Writes a response out to the socket.
     * @param data the response to write
     */
    public void write(Response data);

    /**
     * Terminates a client connection and event loop.
     */
    public void close();

    /**
     * Gets the current state object of the client.
     * @return the client's current state
     */
    public ClientState getState();

    /**
     * Returns the remote server address of the connecting client.
     * @return the server address, such as an IP
     */
    public String getRemoteAddress();

    /**
     * Returns a response appropriate to the socket type.
     * @return Response instance
     */
    public Response getResponse();
}
