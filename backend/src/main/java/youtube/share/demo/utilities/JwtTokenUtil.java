package youtube.share.demo.utilities;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import youtube.share.demo.entity.Users;

@Component
public class JwtTokenUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;  // 24h
	
	@Value("${app.jwt.secret}")
	private String secretKey;
	
	public String generalAccessToken (Users users) {
		return Jwts.builder().setSubject(users.getId() + "," + users.getUsername())
				.setIssuer("Bitapo")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
	
	public Boolean validateAccessToken (String token) {
		
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return Boolean.TRUE;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT expired",ex);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("JWT is null, empty or has only whitespace ",ex);
		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT is not supported ",ex);
		} catch (SignatureException ex) {
			LOGGER.error("Signature validation failed ",ex);
		}
		return Boolean.FALSE;
	}
	
	public String getSubject (String token) {
		return parseClaims(token).getSubject();
	}
	
	private Claims parseClaims (String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
}
