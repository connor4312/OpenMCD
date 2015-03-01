package mcd;

import com.google.inject.AbstractModule;
import mcd.auth.ShaTokenExchange;
import mcd.auth.TokenExchange;
import mcd.config.Config;
import mcd.config.IniConfig;
import mcd.protocol.Server;
import mcd.protocol.TCPServer;

public class MCDInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(Server.class).to(TCPServer.class);
        bind(Config.class).to(IniConfig.class);
        bind(TokenExchange.class).to(ShaTokenExchange.class);
    }
}
