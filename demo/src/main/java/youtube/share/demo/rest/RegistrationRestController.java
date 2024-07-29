package youtube.share.demo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import youtube.share.demo.dao.UsersRepository;
import youtube.share.demo.entity.Users;
import youtube.share.demo.service.UsersService;

@RestController
@RequestMapping("/api")
public class RegistrationRestController {
	
	@Autowired
    private UsersService usersService;
	
	@Autowired
    private UsersRepository usersRepository;
	
	@PostMapping("/register")
	public Map<String, String> registerUser(OAuth2AuthenticationToken authentication, @RequestParam("password") String password, @RequestParam("googleToken") String googleToken) {
	//public Map<String, String> registerUser(OAuth2AuthenticationToken authentication, @RequestParam("password") String password) {
		String username = authentication.getPrincipal().getAttribute("name");
        String email = authentication.getPrincipal().getAttribute("email");
        // String googleToken = authentication.getPrincipal().getAttribute("id_token");
        
        return usersService.registerUser(username, email, password, googleToken);
    }
	
	@GetMapping("/fetch-videos")
    public String fetchVideos(OAuth2AuthenticationToken authentication) {
        String email = authentication.getPrincipal().getAttribute("email");
        //Users user = usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Users user = usersRepository.findByEmail(email);
        usersService.fetchAndStoreYouTubeVideos(user);
        return "Videos fetched and stored successfully.";
    }

}
