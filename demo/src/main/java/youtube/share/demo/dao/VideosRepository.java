package youtube.share.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;

@Repository
public interface VideosRepository extends JpaRepository<Videos, Long> {
	
	List<Videos> findByOwner(Users owner);

	@Query("SELECT v FROM Videos v JOIN v.sharedUsers su WHERE su = :user")
    List<Videos> findSharedWithUser(Users user);
	
}
