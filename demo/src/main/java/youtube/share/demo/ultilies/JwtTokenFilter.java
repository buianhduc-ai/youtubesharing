package youtube.share.demo.ultilies;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import youtube.share.demo.entity.Users;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String header = request.getHeader("Authorization");
		if (!hasAuthorizationHeader(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String accessToken = getAccessToken(request);
		if (!jwtTokenUtil.validateAccessToken(accessToken)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		setAuthenticationContext(accessToken,request);
		filterChain.doFilter(request, response);
		
	}
	
	private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
		// TODO Auto-generated method stub
		UserDetails userDetails = getUserDetails(accessToken);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,null);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private UserDetails getUserDetails(String accessToken) {
		// TODO Auto-generated method stub
		Users users = new Users();
		String[] subjectArr = jwtTokenUtil.getSubject(accessToken).split(",");
		
		users.setId(Long.parseLong(subjectArr[0]));
		users.setUserName(subjectArr[1]);
		
		return users;
	}

	private Boolean hasAuthorizationHeader (HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
		
	}
	
	private String getAccessToken (HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}

}
