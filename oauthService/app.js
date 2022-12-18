const express = require('express');
const {OAuth2Client} = require('google-auth-library');
const http = require('http');
const url = require('url');
const open = require('open');
const destroyer = require('server-destroy');
const cookieParser = require('cookie-parser');
const db = require('./db');
const bodyParser = require('body-parser');
const fetch = require('node-fetch');

const keys = require('./oauth2.keys.json');
const { ConsoleMessage } = require('puppeteer');

const app = express();

app.use(cookieParser());
app.use(bodyParser.json());

app.get('/login', async (req, res) => {
  let userInfo = await authenticate();
  res.status(200);
  res.setHeader("Authorization", "Bearer " + userInfo.access_token);
  await db.save(userInfo);
  res.send();
});

app.post('/refresh', async (req, res) => {
  let access_token = req.headers.authorization.split(" ")[1];

  if(access_token == undefined && access_token == null) {
    res.status(403);
    res.send();
  }

  let user = await db.findByAccessToken(access_token);

  let refreshRequest = {
    "client_id": keys.web.client_id,
    "client_secret": keys.web.client_secret,
    "refresh_token": user.refresh_token,
    "grant_type": "refresh_token"
  }

  let response = await fetch("https://www.googleapis.com/oauth2/v4/token", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json; charset=UTF-8'
    },
    body: JSON.stringify(refreshRequest)
  }).then(res => res.json())
  .then(data => {
    return data;
  });


  user.access_token = response.access_token
  user.expires = '' + Math.floor(new Date().getTime()/1000 + response.expires_in);
  await db.save(user);

  res.status(200);
  res.setHeader("Authorization", "Bearer " + user.access_token);
  res.send();

});

app.listen(8080, () => {
    console.log("app's running on localhost:8080");
});


/**
* Start by acquiring a pre-authenticated oAuth2 client.
*/
async function authenticate() {
  const oAuth2Client = await getAuthenticatedClient();

  const url = 'https://people.googleapis.com/v1/people/me?personFields=names';
  const res = await oAuth2Client.request({url});
  const name = res.data.names[0].displayName;

  // After acquiring an access_token, you may want to check on the audience, expiration,
  // or original scopes requested.  You can do that with the `getTokenInfo` method.
  const accessTokenInfo = await oAuth2Client.getTokenInfo(
    oAuth2Client.credentials.access_token
  );

  return {
    name: name,
    email: accessTokenInfo.email,
    access_token: oAuth2Client.credentials.access_token, 
    expires: accessTokenInfo.expiry_date, 
    refresh_token: oAuth2Client.credentials.refresh_token
  };
}

/**
* Create a new OAuth2Client, and go through the OAuth2 content
* workflow.  Return the full client to the callback.
*/
function getAuthenticatedClient() {

  return new Promise(async (resolve, reject) => {

    const oAuth2Client = new OAuth2Client(
      keys.web.client_id,
      keys.web.client_secret,
      keys.web.redirect_uris[0]
    );

    const authorizeUrl = oAuth2Client.generateAuthUrl({
      access_type: 'offline',
      scope: ['https://www.googleapis.com/auth/userinfo.profile',"https://www.googleapis.com/auth/userinfo.email"],
      prompt: 'consent'
    });

    open(authorizeUrl, {wait: false}).then(cp => cp.unref());

    // Open an http server to accept the oauth callback. In this simple example, the
    // only request to our webserver is to /oauth2callback?code=<code>
    const server = http
      .createServer(async (req, res) => {
        try {
          if (req.url.indexOf('/oauth2callback') > -1) {
            // acquire the code from the querystring, and close the web server.
            const qs = new url.URL(req.url, 'http://localhost:8080/')
              .searchParams;
            const code = qs.get('code');
            res.end('<h1>Authentication successful! Now you can close this page.</h1>');
            server.destroy();

            // Now that we have the code, use that to acquire tokens.
            const r = await oAuth2Client.getToken(code);
            // Make sure to set the credentials on the OAuth2 client.
            oAuth2Client.setCredentials(r.tokens);
            resolve(oAuth2Client);
          }
        } catch (e) {
          reject(e);
        }
      })
      .listen(3000, () => {
      });
    destroyer(server);
  });
}
