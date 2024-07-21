package youtube.share.demo.service;

import java.util.List;

import com.bitapo.automation.manager.entity.Users;

public interface UsersService {

	public List<Users> findAll();
		
	public Users findById(int theId);
	
	public void save(Users users);
	
	public void deleteById(int theId);

	public Users findByUserName(String theId);
	
}
