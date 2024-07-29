import React, { useState } from 'react';
import axios from 'axios';

const ShareVideo = () => {
  const [url, setUrl] = useState('');
  const handleSubmit = async (e) => {
    e.preventDefault();
    const accessToken = localStorage.getItem('accessToken');
    try {
      await axios.post('http://localhost:8080/api/videos/share', { url }, {
        headers: { Authorization: `Bearer ${accessToken}` }
      });
      alert('Video shared successfully!');
    } catch (error) {
      console.error('Failed to share video', error);
    }
  };

  return (
    <div>
      <h1>Share a Video</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="Enter YouTube URL"
        />
        <button type="submit">Share</button>
      </form>
    </div>
  );
};

export default ShareVideo;
