package youtube.share.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import youtube.share.demo.entity.Users;


public interface UsersService {

	public List<Users> findAll();
		
	public Users findById(Long userId);
	
	//public Optional<Users> findByEmail(String theId);
	public Users findByEmail(String theId);
	
	public void save(Users users);
	
	public void deleteById(Long theId);

	public Users findByUserName(String theId);
	
	// Add the new method for registering a user and returning a JWT token
	public Map<String, String> registerUser(String username, String email, String password, String googleToken);
	
	void fetchAndStoreYouTubeVideos(Users user);
	
}
