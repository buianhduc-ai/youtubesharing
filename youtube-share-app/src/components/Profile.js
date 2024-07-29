import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Profile = () => {
  const [videos, setVideos] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchVideos = async () => {
      const accessToken = localStorage.getItem('accessToken');
      try {
        const res = await axios.get('http://localhost:8080/api/videos/my', {
          headers: { Authorization: `Bearer ${accessToken}` }
        });
        setVideos(res.data);
      } catch (error) {
        console.error('Failed to fetch videos', error);
      }
    };

    fetchVideos();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    navigate('/');
  };

  return (
    <div>
      <h1>Profile Page</h1>
      <button onClick={handleLogout}>Logout</button>
      <button onClick={() => navigate('/share')}>Share Video</button>
      <div>
        <h2>My Videos</h2>
        <ul>
          {videos.map(video => (
            <li key={video.id}>
              <a href={video.url} target="_blank" rel="noopener noreferrer">{video.title}</a>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Profile;
