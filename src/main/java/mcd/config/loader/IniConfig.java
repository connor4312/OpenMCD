package mcd.config.loader;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class IniConfig implements Config {

    /**
     * The ini file associated with this config
     */
    protected Wini config;

    /**
     * Config filename.
     */
    public static String filename = "mcd.conf";

    public void load(String path) throws IOException {
        config = new Wini(new File(path));
    }

    @Override
    public Object get(String path) {
        String[] parts = path.split("\\.", 2);
        if (parts.length == 2) {
            return config.get(parts[0], parts[1]);
        } else {
            return config.get(parts[0]);
        }
    }

    @Override
    public boolean has(String path) {
        return get(path) != null;
    }
}
