package mcd.stores;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JarStatus {
    /**
     * Percent completion (zero to one) of the jar update procedure.
     */
    public float percent;

    /**
     * The state the jar is in.
     */
    public JarState state;

    /**
     * The file being targeted in the update. This seems to be pretty aribtrary
     * in the panel, not a big deal...
     */
    public String target;

    /**
     * An optional message to display with the token.
     */
    public String message;

    /**
     * When the status last changed.
     */
    public Date time;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("percent", percent);
        map.put("state", state);
        map.put("target", target);
        map.put("message", message);
        map.put("time", time);
        return map;
    }
}
