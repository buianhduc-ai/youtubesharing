package youtube.share.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitapo.automation.manager.dao.UsersRepository;
import com.bitapo.automation.manager.entity.Users;


@Service
public class UsersServiceImpl implements UsersService {

	// private AccountsDAO accountsDAO;
	private UsersRepository usersRepository;
	
	@Autowired
//	public AccountsSerivceImpl(AccountsDAO theAccountsDAO) {
//		accountsDAO = theAccountsDAO;
//	}
	public UsersServiceImpl(UsersRepository theUsersRepository) {
		usersRepository = theUsersRepository;
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
	public Users findById(int theId) {
		// TODO Auto-generated method stub
		// return accountsDAO.findById(theId);
		// return accountRepository.findById(theId);
		Optional<Users> result = usersRepository.findById(theId);
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
	// @Transactional
	public void save(Users users) {
		// TODO Auto-generated method stub
		// accountsDAO.save(accounts);
		usersRepository.save(users);
	}

	@Override
	// @Transactional
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		// accountsDAO.deleteById(theId);
		usersRepository.deleteById(theId);
	}

}
