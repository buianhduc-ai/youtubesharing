import { useState } from 'react';
import axios from 'axios';

function Register({ onRegister }) {
  const [userName, setEmail] = useState('');
  const [passWord, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/users', { userName, passWord });
      onRegister();
    } catch (error) {
      console.error('Registration error:', error);
    }
  };

  return (
    <div className='register-container'>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          value={userName}
          onChange={(e) => setEmail(e.target.value)}
          placeholder='userName'
          required
        />
        <input
          type='password'
          value={passWord}
          onChange={(e) => setPassword(e.target.value)}
          placeholder='Password'
          required
        />
        <button type='submit'>Register</button>
      </form>
    </div>
  );
}

export default Register;
