package mcd.config.loader;

import java.io.IOException;

public interface Config {
    /**
     * Gets a config option.
     * @param path the option name in dot notation (section.value)
     * @return the section value
     */
    public Object get(String path);

    /**
     * Returns whether the path exists in the config.
     * @param path the option name in dot notation (section.value)
     * @return true if it exists, false otherwise
     */
    public boolean has(String path);

    /**
     * Loads the config from a file or network path.
     * @param path path to load from
     */
    public void load(String path) throws IOException;
}
