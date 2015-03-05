package mcd.protocol;

public class ClientState {
    /**
     * Whether the client has been authenticated, or not.
     */
    private boolean authenticated = false;

    /**
     * The authentication token currently being exchanged on the client.
     */
    private String authToken;

    /**
     * Returns whether the client has authenticated.
     * @return true if yes
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Sets the authenticate state of the client.
     * @param authenticated true if authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Gets the current auth token
     * @return the auth token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the current auth token for later verification.
     * @param authToken the auth token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
