package mcd.auth;

import mcd.auth.ShaTokenExchange;
import mcd.config.Config;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShaTokenExchangeTest {

    protected ShaTokenExchange exchange;
    protected Config config;

    @Before
    public void before() throws Exception {
        config = mock(Config.class);
        when(config.get("multicraft.password")).thenReturn("none");
        exchange = new ShaTokenExchange(config);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Check the token is a 32-length numeric string
     * Method: generateToken()
     */
    @Test
    public void testGenerateToken() throws Exception {
        assertTrue(exchange.generateToken().matches("[0-9]+"));
    }

    @Test
    public void testFalseWhenVerifyNotCorrect() throws Exception {
        assertFalse(exchange.verify("foo", "bar"));
    }

    @Test
    public void testTrueWhenVerifySuccess() throws Exception {
        assertTrue(exchange.verify("4275310260", "AAYHWgEDWgEOUVZUVVoMBFADAAxSAApQAAFdUAACAgAAClYDBQIAVA=="));
    }
}
