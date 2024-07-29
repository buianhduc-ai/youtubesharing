package youtube.share.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import youtube.share.demo.entity.AuthRequest;
import youtube.share.demo.entity.AuthResponse;
import youtube.share.demo.entity.Users;
import youtube.share.demo.ultilies.JwtTokenUtil;

@RestController
public class AuthRestController {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JwtTokenUtil jwtUtil;
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login (@RequestBody AuthRequest request) {
		try {
			Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
					request.getUsername(),request.getPassword()));
			
			Users users = (Users) authentication.getPrincipal();
			
			// String accessToken = "JWT Token";
			String accessToken = jwtUtil.generalAccessToken(users);
			
			AuthResponse response = new AuthResponse(users.getId(),users.getUsername(),accessToken);
			
			return ResponseEntity.ok(response);
			
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
}
