package youtube.share.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import youtube.share.demo.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	@Query("SELECT b FROM Users b WHERE b.userName = :username")
	public Users findByUserName(@Param("username") String username);
	
}
