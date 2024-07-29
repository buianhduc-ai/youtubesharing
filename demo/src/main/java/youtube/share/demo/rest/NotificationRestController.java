package youtube.share.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;

@Controller
public class NotificationRestController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationRestController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(Users user, Videos video) {
    	String _user = Long.toString(user.getId());
    	String _video = video.getTitle();
    	//Notification notification = new Notification(message, notification, 0);
        messagingTemplate.convertAndSendToUser(_user, "/topic/notifications", _video);
    }
}
