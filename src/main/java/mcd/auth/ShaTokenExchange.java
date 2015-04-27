package mcd.auth;

import com.google.inject.Inject;
import mcd.config.DaemonConfig;
import org.apache.commons.lang3.RandomStringUtils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ShaTokenExchange implements TokenExchange {
    /**
     * MCD config instance
     */
    protected DaemonConfig config;

    @Inject
    public ShaTokenExchange(DaemonConfig config) {
        this.config = config;
    }

    @Override
    public String generateToken() {
        return RandomStringUtils.random(32, false, true);
    }

    @Override
    public boolean verify(String token, String signature) {
        String password = (String) config.get("multicraft.password");
        String hashed = Base64.getEncoder().encodeToString(
                xor(hash(token + hash(hash(password))), hash(password)).getBytes()
        );

        return hashed.equals(signature);
    }

    /**
     * sha1 hashes a string
     * @param string input string
     * @return output digest
     */
    protected String hash(String string) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm unavailable.");
        }

        md.update(string.getBytes());
        byte[] hash = md.digest();

        return (new HexBinaryAdapter()).marshal(hash).toLowerCase();
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
