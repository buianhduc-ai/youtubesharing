package youtube.share.demo.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;
import youtube.share.demo.service.UsersService;
import youtube.share.demo.service.VideoService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VideoRestControllerTest {

    @Mock
    private VideoService videoService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private VideoRestController videoRestController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(videoRestController).build();
    }

    @Test
    public void testGetMyVideos() throws Exception {
        Users user = new Users();
        user.setEmail("test@example.com");
        List<Videos> videos = Collections.singletonList(new Videos());

        when(usersService.findByEmail(anyString())).thenReturn(user);
        when(videoService.findByOwner(any(Users.class))).thenReturn(videos);

        mockMvc.perform(get("/api/videos/my")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isOk());

        verify(usersService, times(1)).findByEmail("test@example.com");
        verify(videoService, times(1)).findByOwner(user);
    }

    @Test
    public void testShareVideo() throws Exception {
        Users owner = new Users();
        owner.setEmail("test@example.com");
        Videos video = new Videos();
        video.setOwner(owner);
        Users sharedUser = new Users();
        sharedUser.setId(1L);

        when(usersService.findByEmail(anyString())).thenReturn(owner);
        when(videoService.findById(anyLong())).thenReturn(Optional.of(video));
        when(usersService.findById(anyLong())).thenReturn(sharedUser);

        mockMvc.perform(post("/api/videos/share")
                        .param("videoId", "1")
                        .param("userId", "1")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isOk());

        verify(videoService, times(1)).shareVideoWithUser(video, sharedUser);
    }

    @Test
    public void testGetSharedWithMe() throws Exception {
        Users user = new Users();
        user.setEmail("test@example.com");
        List<Videos> videos = Collections.singletonList(new Videos());

        when(usersService.findByEmail(anyString())).thenReturn(user);
        when(videoService.findSharedWithUser(any(Users.class))).thenReturn(videos);

        mockMvc.perform(get("/api/videos/shared-with-me")
                        .principal(() -> "test@example.com"))
                .andExpect(status().isOk());

        verify(usersService, times(1)).findByEmail("test@example.com");
        verify(videoService, times(1)).findSharedWithUser(user);
    }

    @Test
    public void testGetVideoById() throws Exception {
        Videos video = new Videos();
        when(videoService.findById(anyLong())).thenReturn(Optional.of(video));

        mockMvc.perform(get("/api/videos/1"))
                .andExpect(status().isOk());

        verify(videoService, times(1)).findById(1L);
    }
}