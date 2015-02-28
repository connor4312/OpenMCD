package mcd.config;

import mcd.OpenMCD;
import org.ini4j.Wini;
import org.apache.commons.io.FileUtils;

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

    public IniConfig() throws IOException {
        File file = new File(OpenMCD.instance().cwd(), filename);
        if (!file.exists()) {
            unpackConfig();
        }

        config = new Wini(file);
    }

    /**
     * Copies the config from our resources directory beside the jar.
     * @throws IOException
     */
    protected void unpackConfig() throws IOException {
        FileUtils.copyURLToFile(
                getClass().getResource("/" + filename),
                new File(OpenMCD.instance().cwd(), filename)
        );
    }

    @Override
    public Object get(String path) {
        String[] parts = path.split(".", 2);
        return config.get(parts[0], parts[1]);
    }

    @Override
    public boolean has(String path) {
        return get(path) != null;
    }
}
