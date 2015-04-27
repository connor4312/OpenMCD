package mcd.config;

import com.google.inject.Singleton;
import mcd.config.loader.IniConfig;

@Singleton
public class IniDaemonConfig extends IniConfig implements DaemonConfig {

    protected String baseDirectory;

    @Override
    public String getBaseDirectory() {
        return baseDirectory;
    }

    @Override
    public void setBaseDirectory(String dir) {
        baseDirectory = dir;
    }
}
