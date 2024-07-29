package youtube.share.demo.entity;

import java.util.HashSet;
import java.util.Set;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name="videos")
public class Videos {

	// Define fields
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="videoId")
	private String videoId;
	
	@Column(name="title")
    private String title;
	
	@Column(name="description")
    private String description;
	
	@Column(name="url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users owner;
    
    @ManyToMany
    @JoinTable(
        name = "shared_videos",
        joinColumns = @JoinColumn(name = "video_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Users> sharedUsers = new HashSet<>();
    
    public Videos () {}

	public Videos(Long id, String videoId, String title, String description, String url, Users owner) {
		this.id = id;
		this.videoId = videoId;
		this.title = title;
		this.description = description;
		this.url = url;
		this.owner = owner;
	}

	public Videos(String videoId, String title, String description, String url, Users owner) {
		this.videoId = videoId;
		this.title = title;
		this.description = description;
		this.url = url;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Users getOwner() {
		return owner;
	}

	public void setOwner(Users owner) {
		this.owner = owner;
	}

	public Set<Users> getSharedUsers() {
		return sharedUsers;
	}

	public void setSharedUsers(Set<Users> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}
	
	public void addSharedUser(Users user) {
        sharedUsers.add(user);
    }

    public void removeSharedUser(Users user) {
        sharedUsers.remove(user);
    }
		
}
