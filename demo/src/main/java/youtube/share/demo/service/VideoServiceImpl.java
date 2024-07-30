package youtube.share.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import youtube.share.demo.dao.VideosRepository;
import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;
import youtube.share.demo.rest.NotificationRestController;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosRepository videosRepository;

    @Autowired
    private NotificationRestController notificationRestController;

    @Autowired
    public VideoServiceImpl(NotificationRestController notificationRestController) {
        this.notificationRestController = notificationRestController;
    }
    
    @Override
	// @Transactional
	public List<Videos> findAll() {
		// TODO Auto-generated method stub
		// return accountsDAO.findAll();
		return videosRepository.findAll();
	}

    @Override
    public List<Videos> findByOwner(Users owner) {
        return videosRepository.findByOwner(owner);
    }

    @Override
    public void shareVideoWithUser(Videos videos, Users users) {
        videos.addSharedUser(users);
        videosRepository.save(videos);
        notificationRestController.sendNotification(users, videos);
    }

    @Override
    public List<Videos> findSharedWithUser(Users users) {
        return videosRepository.findSharedWithUser(users);
    }

    @Override
    public Optional<Videos> findById(Long id) {
        return videosRepository.findById(id);
    }

    @Override
    public void save(Videos videos) {
        videosRepository.save(videos);
    }
}
