// src/components/Login.js
import React, { useState } from 'react';
import api from '../api';
import { useHistory } from 'react-router-dom';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const history = useHistory();

  const handleLogin = () => {
    api.post('/auth/login', { email, password })
      .then(response => {
        localStorage.setItem('token', response.data.accessToken);
        history.push('/profile');
      })
      .catch(error => {
        console.error('There was an error logging in!', error);
      });
  };

  const handleRegister = () => {
    api.post('/auth/register', { email, password })
      .then(response => {
        localStorage.setItem('token', response.data.accessToken);
        history.push('/profile');
      })
      .catch(error => {
        console.error('There was an error registering!', error);
      });
  };

  return (
    <div className="container">
      <h1>Login/Register</h1>
      <div>
        <input 
          type="email" 
          placeholder="Email" 
          value={email} 
          onChange={(e) => setEmail(e.target.value)} 
        />
        <input 
          type="password" 
          placeholder="Password" 
          value={password} 
          onChange={(e) => setPassword(e.target.value)} 
        />
        <button onClick={handleLogin}>Login</button>
        <button onClick={handleRegister}>Register</button>
        <GoogleLoginButton />
      </div>
    </div>
  );
}

export default Login;
