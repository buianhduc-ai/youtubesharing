package youtube.share.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeResponse {

    private List<YouTubeVideoItem> items;

    public static class Item {
        private String id;
        private Snippet snippet;

        // Getters and setters

        public static class Snippet {
            private String title;
            private String description;
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
            
        }
    }

//	public List<Item> getItems() {
//		return items;
//	}
//
//	public void setItems(List<Item> items) {
//		this.items = items;
//	}
    
    // Getters and Setters
    public List<YouTubeVideoItem> getItems() {
        return items;
    }

    public void setItems(List<YouTubeVideoItem> items) {
        this.items = items;
    }


}
