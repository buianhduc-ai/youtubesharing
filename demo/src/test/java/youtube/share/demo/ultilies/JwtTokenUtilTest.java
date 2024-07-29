package youtube.share.demo.ultilies;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenUtilTest {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.token-validity}")
    private long tokenValidity;

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        jwtTokenUtil.setSecretKey(secretKey);
        jwtTokenUtil.setTokenValidity(tokenValidity);
    }

    @Test
    void testGenerateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "testUser");
        claims.put("email", "test@example.com");

        String token = jwtTokenUtil.generateToken(claims);

        assertNotNull(token);
        
        Claims parsedClaims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        assertEquals("testUser", parsedClaims.get("username"));
        assertEquals("test@example.com", parsedClaims.get("email"));
    }
}
