package youtube.share.demo.entity;

public class AuthResponse {

	private Long  id;

	private String username;
	
	private String accessToken;
	
	public AuthResponse () {}

	public AuthResponse(Long  id,String username, String accessToken) {
		this.id = id;
		this.username = username;
		this.accessToken = accessToken;
	}
	
	public Long  getId() {
		return id;
	}

	public void setId(Long  id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
