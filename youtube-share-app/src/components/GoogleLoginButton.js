// src/components/GoogleLoginButton.js
import React from 'react';
import { GoogleLogin } from 'react-google-login';
import axios from 'axios';

const GoogleLoginButton = () => {

  const responseGoogle = (response) => {
    const { tokenId } = response;

    // Send the token to your backend
    axios.post('http://localhost:8080/api/auth/google', { token: tokenId })
      .then(response => {
        localStorage.setItem('token', response.data.accessToken);
        // Redirect to profile page or wherever you want
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
  }

  return (
    <GoogleLogin
      clientId="95535633848-p5foc07m18gich7i0u7c8oglcn5drf1r.apps.googleusercontent.com"
      buttonText="Login with Google"
      onSuccess={responseGoogle}
      onFailure={responseGoogle}
      cookiePolicy={'single_host_origin'}
    />
  );
}

export default GoogleLoginButton;
