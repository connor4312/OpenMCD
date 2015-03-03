package mcd.auth;

import com.google.inject.Inject;
import mcd.config.Config;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaTokenExchange implements TokenExchange {
    /**
     * MCD config sintance
     */
    protected Config config;

    @Inject
    public ShaTokenExchange(Config config) {
        this.config = config;
    }

    @Override
    public String generateToken() {
        return RandomStringUtils.random(32);
    }

    @Override
    public boolean verify(String token, String signature) {
        String password = (String) config.get("multicraft.password");
        String hashed = hash(xor(token + hash(hash(password)), hash(password)));
        return hashed.equals(signature);
    }

    /**
     * sha1 hashes a string
     * @param string input string
     * @return output digest
     */
    protected String hash(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ignored) {}
        md.update(string.getBytes());
        return new String(md.digest());
    }

    /**
     * Performs an xor operation on two strings.
     * @param left one string
     * @param right the other string
     * @return the results!
     */
    protected String xor(String left, String right) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < left.length(); i++) {
            builder.append((char) (left.charAt(i) ^ right.charAt(i % right.length())));
        }

        return builder.toString();
    }
}
