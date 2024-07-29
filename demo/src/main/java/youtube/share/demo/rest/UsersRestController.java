package youtube.share.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import youtube.share.demo.entity.Users;
import youtube.share.demo.service.UsersService;


@RestController
@RequestMapping("/api")
public class UsersRestController {

	// Define filed
	private UsersService usersService;
	
	// Constructor Injection
	@Autowired
	public UsersRestController (UsersService theUsersService) {
		usersService = theUsersService;
	}
	
	// Export List
	@GetMapping("/users")
	public List<Users> findAll() {
		return usersService.findAll();
	}
	
	// GET /users/username/{accountId}
	@GetMapping("/users/{usersId}")
	public Users getAccount(@PathVariable Long usersId) {
		Users theUsers = usersService.findById(usersId);
		if (theUsers == null) {
			throw new RuntimeException("Users id not found - " + usersId);
		}
		return theUsers;
	}
	
	// GET /users/email/{accountId}
	@GetMapping("/users/username/{usersId}")
	public Users getUserName(@PathVariable String usersId) {
		Users theUsers = usersService.findByUserName(usersId);
		if (theUsers == null) {
			throw new RuntimeException("Users id not found - " + usersId);
		}
		return theUsers;
	}
	
	// GET /users/email/{accountId}
	@GetMapping("/users/email/{usersId}")
	//public Optional<Users> getEmail(@PathVariable String usersId) {
	public Users getEmail(@PathVariable String usersId) {
		//Optional<Users> theUsers = usersService.findByEmail(usersId);
		Users theUsers = usersService.findByEmail(usersId);
		if (theUsers == null) {
			throw new RuntimeException("Users id not found - " + usersId);
		}
		return theUsers;
	}
	
	// Add an accounts
	@PostMapping("/users")
	public Users addAccount(@RequestBody Users theUsers) {
		//theUsers.setId(0);
		usersService.save(theUsers);
		return theUsers;
	}
	
	// Update an accounts
	@PutMapping("/users")
	public Users updateAccount(@RequestBody Users theUsers) {
		usersService.save(theUsers);
		return theUsers;
	}
	
	// GET /accounts/{accountId}
	@DeleteMapping("/users/{usersId}")
	public String deleteAccount(@PathVariable Long usersId) {
		Users theUsers = usersService.findById(usersId);
		if (theUsers == null) {
			throw new RuntimeException("Users id not found - " + usersId);
		}
		usersService.deleteById(usersId);
		return "Delete Users id - " + usersId;
	}
	
}









