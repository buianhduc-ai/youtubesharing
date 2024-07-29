package youtube.share.demo.service;

import java.util.List;
import java.util.Optional;

import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;

public interface VideoService {
	
	List<Videos> findByOwner(Users owner);
    //void shareVideoWithUser(Videos videos, Long long1);
    List<Videos> findSharedWithUser(Users users);
    Optional<Videos> findById(Long id);
    void save(Videos videos);
    //void shareVideoWithUser(Videos videos, Long longId);
	void shareVideoWithUser(Videos videos, Users users);
    //List<Videos> shareVideoWithUser(Videos videos, Users users);

}
