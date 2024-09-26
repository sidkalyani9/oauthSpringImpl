import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import axios from 'axios';

function App() {
  const handleGoogleLoginSuccess = async (response) => {
    try {
      const idToken = response.credential; // Google's ID token
      console.log('Google ID Token:', idToken);

      // Send the ID token to your backend for validation and login
      const backendResponse = await axios.post('http://localhost:8080/api/auth/google', {
        token: idToken,
      });

      if (backendResponse.data.success) {
        console.log(backendResponse.data)
        console.log('User logged in successfully');
        // Handle the logged-in state, e.g., save token, redirect, etc.
      } else {
        console.error('Login failed');
      }
    } catch (error) {
      console.error('Error during Google login:', error);
    }
  };

  return (
    <div className="App">
      <h1>Login with Google</h1>
      <GoogleLogin
        onSuccess={handleGoogleLoginSuccess}
        onError={() => {
          console.log('Login Failed');
        }}
      />
    </div>
  );
}

export default App;