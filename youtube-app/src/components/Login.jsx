import { useState } from 'react';
import axios from 'axios';

function Login({ onLogin }) {
  const [username, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://139.180.186.162:8080/auth/login', { username, password });
      const token = response.data.accessToken;
      onLogin(token);
    } catch (error) {
      console.error('Login error:', error);
    }
  };

  return (
    <div className='login-container'>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          value={username}
          onChange={(e) => setEmail(e.target.value)}
          placeholder='username'
          required
        />
        <input
          type='password'
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder='Password'
          required
        />
        <button type='submit'>Login</button>
      </form>
    </div>
  );
}

export default Login;
