package mcd.config;

import com.google.inject.Singleton;
import org.ini4j.Wini;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Singleton
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
        File file = new File(path, filename);
        if (!file.exists()) {
            unpackConfig(path);
        }

        config = new Wini(file);
    }

    /**
     * Copies the config from our resources directory beside the jar.
     * @param path the folder path to copy into
     * @throws IOException
     */
    protected void unpackConfig(String path) throws IOException {
        FileUtils.copyURLToFile(
                getClass().getResource("/" + filename),
                new File(path, filename)
        );
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
