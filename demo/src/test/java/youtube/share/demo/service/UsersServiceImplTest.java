package youtube.share.demo.service;

import youtube.share.demo.dao.UsersRepository;
import youtube.share.demo.entity.Users;
import youtube.share.demo.ultilies.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usersService = new UsersServiceImpl(usersRepository, jwtTokenUtil);
        usersService.setPasswordEncoder(passwordEncoder);
    }

    @Test
    void testRegisterUser() throws Exception {
        String username = "testUser";
        String email = "test@example.com";
        String password = "testPassword";
        String encodedPassword = "encodedPassword";
        String googleToken = "googleToken";
        
        Users user = new Users();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassWord(encodedPassword);

        when(usersRepository.findByEmail(email)).thenReturn(null);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(usersRepository.save(any(Users.class))).thenReturn(user);
        when(jwtTokenUtil.generateToken(anyMap())).thenReturn(googleToken);

        Map<String, String> result = usersService.registerUser(username, email, password, googleToken);

        assertNotNull(result);
        assertEquals(googleToken, result.get("accessToken"));
        verify(usersRepository, times(1)).findByEmail(email);
        verify(usersRepository, times(1)).save(any(Users.class));
        verify(jwtTokenUtil, times(1)).generateToken(anyMap());
    }

//    @Test
//    void testFindById() {
//        int id = 1;
//        Users user = new Users();
//        user.setId(id);
//        Optional<Users> optionalUser = Optional.of(user);
//
//        when(usersRepository.findById(id)).thenReturn(optionalUser);
//
//        Users foundUser = usersService.findById(id);
//
//        assertNotNull(foundUser);
//        assertEquals(id, foundUser.getId());
//    }
    
    @Test
    void testFindByEmail() {
    	String email = "test@example.com";
        Users user = new Users();
        user.setEmail(email);
        Optional<Users> optionalUser = Optional.of(user);

        when(usersRepository.findByEmail(email)).thenReturn(null);
    }

    // Add more tests for other methods in UsersServiceImpl
}