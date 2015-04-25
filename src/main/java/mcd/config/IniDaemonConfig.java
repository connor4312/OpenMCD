package mcd.config;

import com.google.inject.Singleton;
import mcd.config.loader.IniConfig;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Singleton
public class IniDaemonConfig extends IniConfig implements DaemonConfig {

    @Override
    public void load(String path) throws IOException {
        File file = new File(path, filename);
        if (!file.exists()) {
            unpackConfig(path);
        }

        super.load(file.getAbsolutePath());
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
}
