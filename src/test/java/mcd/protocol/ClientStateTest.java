package mcd.protocol;

import mcd.protocol.ClientState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientStateTest {

    protected ClientState client;

    @Before
    public void before() {
        client = new ClientState();
    }

    @Test
    public void testIsUnauthenticatedByDefault() throws Exception {
        assertEquals(false, client.isAuthenticated());
    }
}
