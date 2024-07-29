package youtube.share.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import youtube.share.demo.entity.Users;
import youtube.share.demo.entity.Videos;
import youtube.share.demo.service.UsersService;
import youtube.share.demo.service.VideoService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoRestController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UsersService usersService;

    // Endpoint to get all videos owned by the authenticated user
    @GetMapping("/my")
    public ResponseEntity<List<Videos>> getMyVideos(Principal principal) {
        Optional<Users> userOpt = Optional.of(usersService.findByEmail(principal.getName()));
        if (userOpt.isPresent()) {
            List<Videos> videos = videoService.findByOwner(userOpt.get());
            return ResponseEntity.ok(videos);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint to share a video with another user
    @PostMapping("/share")
    public ResponseEntity<String> shareVideo(@RequestParam Long videoId, @RequestParam Long userId, Principal principal) {
        Optional<Users> ownerOpt = Optional.of(usersService.findByEmail(principal.getName()));
        if (ownerOpt.isPresent()) {
            Optional<Videos> videoOpt = videoService.findById(videoId);
            //Users userOpt = usersService.findById(userId);
            Optional<Users> userOpt = Optional.of(usersService.findById(userId));

            if (videoOpt.isPresent() && videoOpt.get().getOwner().equals(ownerOpt.get())) {
                videoService.shareVideoWithUser(videoOpt.get(), userOpt.get());
                return ResponseEntity.ok("Video shared successfully");
            }
        }
        return ResponseEntity.badRequest().body("Unable to share video");
    }

    // Endpoint to get all videos shared with the authenticated user
    @GetMapping("/shared-with-me")
    public ResponseEntity<List<Videos>> getSharedWithMe(Principal principal) {
        Optional<Users> userOpt = Optional.of(usersService.findByEmail(principal.getName()));
        if (userOpt.isPresent()) {
            List<Videos> videos = videoService.findSharedWithUser(userOpt.get());
            return ResponseEntity.ok(videos);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint to get a specific video by ID
    @GetMapping("/{id}")
    public ResponseEntity<Videos> getVideoById(@PathVariable Long id) {
        Optional<Videos> videoOpt = videoService.findById(id);
        return videoOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
