package youtube.share.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitapo.automation.manager.entity.Users;
import com.bitapo.automation.manager.service.UsersService;

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
	
	// GET /accounts/{accountId}
	@GetMapping("/users/{usersId}")
	public Users getAccount(@PathVariable int usersId) {
		Users theUsers = usersService.findById(usersId);
		if (theUsers == null) {
			throw new RuntimeException("Users id not found - " + usersId);
		}
		return theUsers;
	}
	
	// Add an accounts
	@PostMapping("/users")
	public Users addAccount(@RequestBody Users theUsers) {
		theUsers.setId(0);
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
	public String deleteAccount(@PathVariable int usersId) {
		Users theUsers = usersService.findById(usersId);
		if (theUsers == null) {
			throw new RuntimeException("Users id not found - " + usersId);
		}
		usersService.deleteById(usersId);
		return "Delete Users id - " + usersId;
	}
	
}









