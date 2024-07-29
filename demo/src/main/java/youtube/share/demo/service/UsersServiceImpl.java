package youtube.share.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import youtube.share.demo.dao.UsersRepository;
import youtube.share.demo.dao.VideosRepository;
import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;
import youtube.share.demo.entity.YouTubeResponse;
import youtube.share.demo.entity.YouTubeVideoItem;
import youtube.share.demo.ultilies.JwtTokenUtil;

@Service
public class UsersServiceImpl implements UsersService {

	private UsersRepository usersRepository;
	private JwtTokenUtil jwtTokenUtil;
	private PasswordEncoder passwordEncoder;
	private VideosRepository videosRepository;
	
	@Autowired
	public UsersServiceImpl(UsersRepository theUsersRepository, JwtTokenUtil theJwtTokenUtil) {
		usersRepository = theUsersRepository;
		jwtTokenUtil = theJwtTokenUtil;
		passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	@Override
	// @Transactional
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		// return accountsDAO.findAll();
		return usersRepository.findAll();
	}

	@Override
	// @Transactional
	public Users findById(Long theId) {
		// TODO Auto-generated method stub
		// return accountsDAO.findById(theId);
		// return accountRepository.findById(theId);
		int _theID = theId.intValue();
		Optional<Users> result = usersRepository.findById(_theID);
		Users theUsers = null;
		if (result.isPresent()) {
			theUsers = result.get();
		} else {
			// we didn't find account.
			throw  new RuntimeException("Did not find Users id - " + theId);
		}

		return theUsers;
	}
	
	@Override
	public Users findByUserName(String theId) {
		// TODO Auto-generated method stub
		Users theUsers = usersRepository.findByUserName(theId);
		if (theUsers == null) {
			throw  new RuntimeException("Did not find Users id - " + theId);
		} else {
			// we didn't find account.
			return theUsers;
		}

	}
	
	@Override
	//public Optional<Users> findByEmail(String theId) {
	public Users findByEmail(String theId) {
		// TODO Auto-generated method stub
		//Optional<Users> theUsers = usersRepository.findByEmail(theId);
		Users theUsers = usersRepository.findByEmail(theId);
		if (theUsers == null) {
			throw  new RuntimeException("Did not find Users id - " + theId);
		} else {
			// we didn't find account.
			return theUsers;
		}

	}
	
	@Override
    public Map<String, String> registerUser(String username, String email, String password, String googleToken) {
	//public Map<String, String> registerUser(String username, String email, String password) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            user = new Users();
            //user.setId(0);
            user.setUserName(username);
            user.setEmail(email);
            user.setPassWord(passwordEncoder.encode(password));
            //user.setGoogleToken(googleToken);
            usersRepository.save(user);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUserName());
        claims.put("email", user.getEmail());
        claims.put("password", user.getPassWord());
        claims.put("google", user.getGoogleToken());
        String token = jwtTokenUtil.generateToken(claims);
        
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token);
        System.out.println(response);
        return response;
    }
	
//	@Override
//	public Map<String, String> registerUser(String username, String email, String password, String googleToken) {
//	    Optional<Users> optionalUser = usersRepository.findByEmail(email);
//	    Users user;
//
//	    if (optionalUser.isPresent()) {
//	        user = optionalUser.get();
//	    } else {
//	        user = new Users();
//	        user.setUserName(username);
//	        user.setEmail(email);
//	        user.setPassWord(passwordEncoder.encode(password));
//	        user.setGoogleToken(googleToken);
//	        usersRepository.save(user);
//	    }
//
//	    Map<String, Object> claims = new HashMap<>();
//	    claims.put("username", user.getUserName());
//	    claims.put("email", user.getEmail());
//	    claims.put("password", user.getPassWord());
//	    claims.put("google", user.getGoogleToken());
//	    
//	    String token = jwtTokenUtil.generateToken(claims);
//
//	    Map<String, String> response = new HashMap<>();
//	    response.put("accessToken", token);
//	    System.out.println(response);
//	    return response;
//	}

	
	@Override
    public void fetchAndStoreYouTubeVideos(Users user) {
        String accessToken = user.getGoogleToken();
        String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet&mine=true&access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();
        YouTubeResponse response = restTemplate.getForObject(url, YouTubeResponse.class);

        if (response != null && response.getItems() != null) {
            List<Videos> videos = response.getItems().stream().map((YouTubeVideoItem item) -> {
                Videos video = new Videos();
                video.setVideoId(item.getId());
                video.setTitle(item.getSnippet().getTitle());
                video.setDescription(item.getSnippet().getDescription());
                video.setUrl("https://www.youtube.com/watch?v=" + item.getId());
                video.setOwner(user);
                return video;
            }).collect(Collectors.toList());

            videosRepository.saveAll(videos);
        }
    }

	@Override
	// @Transactional
	public void save(Users users) {
		// TODO Auto-generated method stub
		// accountsDAO.save(accounts);
		usersRepository.save(users);
	}

	@Override
	// @Transactional
	public void deleteById(Long theId) {
		// TODO Auto-generated method stub
		// accountsDAO.deleteById(theId);
		int _theID = theId.intValue();
		usersRepository.deleteById(_theID);
	}

}
