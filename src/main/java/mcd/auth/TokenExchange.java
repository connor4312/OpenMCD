package mcd.auth;

public interface TokenExchange {
    /**
     * Generates and returns a new token to be signed on the client.
     * @return a short token string
     */
    public String generateToken();

    /**
     * Verifies a token "signature".
     * @param token the original token string
     * @param signature the token response
     * @return whether the signature is valid
     */
    public boolean verify(String token, String signature);
}
