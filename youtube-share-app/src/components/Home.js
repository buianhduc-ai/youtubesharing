import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleLogin } from 'react-google-login';
import axios from 'axios';

const Home = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isRegister, setIsRegister] = useState(false);
  const navigate = useNavigate();

  const handleLoginSuccess = async (response) => {
    const { tokenId } = response;
    try {
      const res = await axios.post('http://localhost:8080/api/auth/google', { token: tokenId });
      localStorage.setItem('accessToken', res.data.accessToken);
      navigate('/profile');
    } catch (error) {
      console.error('Login failed', error);
    }
  };

  const handleLoginFailure = (response) => {
    console.error('Login failed', response);
  };

  const handleEmailLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(`http://localhost:8080/api/auth/${isRegister ? 'register' : 'login'}`, {
        email,
        password
      });
      localStorage.setItem('accessToken', res.data.accessToken);
      navigate('/profile');
    } catch (error) {
      console.error('Login/Register failed', error);
    }
  };

  return (
    <div>
      <h1>{isRegister ? 'Register' : 'Login'}</h1>
      <form onSubmit={handleEmailLogin}>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Email"
          required
        />
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
          required
        />
        <button type="submit">{isRegister ? 'Register' : 'Login'}</button>
      </form>
      <button onClick={() => setIsRegister(!isRegister)}>
        {isRegister ? 'Switch to Login' : 'Switch to Register'}
      </button>
      <h2>OR</h2>
      <GoogleLogin
        clientId="95535633848-p5foc07m18gich7i0u7c8oglcn5drf1r.apps.googleusercontent.com"
        buttonText="Login with Google"
        onSuccess={handleLoginSuccess}
        onFailure={handleLoginFailure}
        cookiePolicy={'single_host_origin'}
      />
    </div>
  );
};

export default Home;
