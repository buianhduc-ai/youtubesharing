package youtube.share.demo.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;
import youtube.share.demo.rest.NotificationRestController;

@SpringBootTest
public class NotificationServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private NotificationRestController notificationRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendVideoShareNotification() {
        Users user = new Users();
        user.setId(1L);

        Videos video = new Videos();
        video.setTitle("Sample Video");

        notificationRestController.sendNotification(user, video);

        //verify(messagingTemplate).convertAndSend("/topic/notifications/1", "A video has been shared with you: Sample Video");
    }
}
