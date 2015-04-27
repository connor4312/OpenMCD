package mcd.config;

import mcd.config.loader.Config;

public interface DaemonConfig extends Config {
    /**
     * Get the base directory the MCD is booted in.
     * @return the directory
     */
    public String getBaseDirectory();

    /**
     * Set the base directory the MCD is booted in.
     * @param dir the directory
     */
    public void setBaseDirectory(String dir);
}
