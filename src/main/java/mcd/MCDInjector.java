package mcd;

import com.google.inject.AbstractModule;
import mcd.auth.ShaTokenExchange;
import mcd.auth.TokenExchange;
import mcd.config.DaemonConfig;
import mcd.config.IniDaemonConfig;
import mcd.protocol.Server;
import mcd.protocol.TCPServer;

public class MCDInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(TCPServer.class);
        bind(DaemonConfig.class).to(IniDaemonConfig.class);
        bind(TokenExchange.class).to(ShaTokenExchange.class);
    }
}
