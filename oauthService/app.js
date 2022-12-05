const express = require('express');
const {OAuth2Client} = require('google-auth-library');
const url = require('url');
const open = require('open');

// Download your OAuth2 configuration from the Google
const keys = require('./oauth2.keys.json');

const app = express();

app.get('/login', async (req, res) => {
    
});



app.listen(3000, () => {
    console.log("app's running on localhost:3000");
});