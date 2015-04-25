package mcd.stores;

public interface JarInterface {
    /**
     * Returns the name of the jar.
     * @return that jar's name which should be displayed in the panel.
     */
    public String getName();

    /**
     * Returns the current status of the jar. This is generally maintained
     * and can be queries at any time (it is in JarState.READY when not
     * doing anything).
     *
     * @return A list of jar statuses, which is one for the jar file and one
     *         for the config file by default.
     */
    public JarStatus[] getStatus();
}
