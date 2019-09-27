package intra.poleemploi.security;

public interface SecurityParams {
    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String SECRET = "asBH56Ml1pWWuiopH45";
    public static final long EXPIRATION = 864000; // 10*24*3600
    public static final String HEADER_PREFIX = "Bearer ";
}
