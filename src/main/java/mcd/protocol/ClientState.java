package mcd.protocol;

public enum ClientState {
    // client is connected, not yet authed
    CONNECTED,
    // client is in the process of authenticating
    AUTHENTICATING,
    // client has authenticated and is ready to go!
    AUTHENTICATED
}
