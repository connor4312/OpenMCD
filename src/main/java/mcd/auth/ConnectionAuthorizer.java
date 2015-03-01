package mcd.auth;

import com.google.inject.Inject;
import mcd.config.Config;
import mcd.protocol.Client;

public class ConnectionAuthorizer {

    /**
     * Configuration for the mcd
     */
    protected Config config;

    @Inject
    public ConnectionAuthorizer(Config config) {
        this.config = config;
    }

    /**
     * Returns whether the given client is authorized to connect to the server.
     * If it isn't then the connection should be closed.
     * @param client the client to authorize
     * @return true if it is, false otherwise
     */
    public boolean authorize(Client client) {
        // If we haven't specified allowed IPs, just let anyone in
        if (!config.has("multicraft.allowedIps")) {
            return true;
        }

        // Split the IP list by commas
        String[] ips = config.get("multicraft.allowedIps").toString().split(",");
        // If any of the IPs match, allow
        for (String ip: ips) {
            System.out.println(ip);
            System.out.println(client.getRemoteAddress());
            if (ip.equals(client.getRemoteAddress())) {
                return true;
            }
        }

        // Otherwise, disallow
        return false;
    }
}
