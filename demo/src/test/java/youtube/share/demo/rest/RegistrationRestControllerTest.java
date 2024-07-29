package youtube.share.demo.rest;

import youtube.share.demo.dao.UsersRepository;
import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.YouTubeResponse;
import youtube.share.demo.entity.YouTubeVideoItem;
import youtube.share.demo.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class RegistrationRestControllerTest {

    @Mock
    private UsersService usersService;
    
    @Mock
    private UsersRepository usersRepository; // Mock repository if needed

    @InjectMocks
    private RegistrationRestController registrationRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationRestController).build();
    }

    @Test
    void testRegisterUser() throws Exception {
        String username = "testUser";
        String email = "test@example.com";
        String password = "testPassword";
        String googleToken = "googleToken";

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", googleToken);

        when(usersService.registerUser(username, email, password, googleToken)).thenReturn(response);

        // Create a mock OAuth2User
        OAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                Map.of("name", username, "email", email),
                "email"
        );

        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(
                oAuth2User,
                oAuth2User.getAuthorities(),
                "google"
        );

        mockMvc.perform(post("/api/register")
                .param("password", password)
                .param("googleToken", googleToken)
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.accessToken").value(googleToken));

        verify(usersService, times(1)).registerUser(username, email, password, googleToken);
    }
    
    @Test
    void testFetchVideos() {
    	String email = "test@example.com";
        String videoId = "abc123";

        // Create a mock YouTubeVideoItem
        YouTubeVideoItem.Snippet snippet = new YouTubeVideoItem.Snippet();
        snippet.setTitle("Video Title");
        snippet.setDescription("Video Description");

        YouTubeVideoItem videoItem = new YouTubeVideoItem();
        videoItem.setId(videoId);
        videoItem.setSnippet(snippet);

        YouTubeResponse response = new YouTubeResponse();
        response.setItems(Collections.singletonList(videoItem));

        // Mock the service method that fetches YouTube data (if it exists)
        // Assuming you have a method in your service to get video details
        // when(yourService.fetchVideoDetails(anyString())).thenReturn(response);

        // Mock user retrieval (if necessary)
        Users mockUser = new Users();
        mockUser.setEmail(email);
        when(usersRepository.findByEmail(email)).thenReturn(mockUser);

        // Run the method under test
        //registrationRestController.fetchVideos(videoId);

    }
}
