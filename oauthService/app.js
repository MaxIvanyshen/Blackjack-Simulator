const express = require('express');
const {OAuth2Client} = require('google-auth-library');
const http = require('http');
const url = require('url');
const open = require('open');
const destroyer = require('server-destroy');
const cookieParser = require('cookie-parser');
const puppeteer = require('puppeteer');

const app = express();

app.use(cookieParser());

let token = "abc";

app.get('/login', async (req, res) => {
  token = await oauth2(res);
  res.status(200);
  res.cookie("access_token", token, {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
  })
  res.send();
});


app.listen(8080, () => {
    console.log("app's running on localhost:8080");
});


// Download your OAuth2 configuration from the Google
const keys = require('./oauth2.keys.json');
const { CLOUD_RESOURCE_MANAGER } = require('google-auth-library/build/src/auth/baseexternalclient');

/**
* Start by acquiring a pre-authenticated oAuth2 client.
*/
async function oauth2() {
  const oAuth2Client = await getAuthenticatedClient();
  // Make a simple request to the People API using our pre-authenticated client. The `request()` method
  // takes an GaxiosOptions object.  Visit https://github.com/JustinBeckwith/gaxios.
  const url = 'https://people.googleapis.com/v1/people/me?personFields=names';
  const res = await oAuth2Client.request({url});

  // After acquiring an access_token, you may want to check on the audience, expiration,
  // or original scopes requested.  You can do that with the `getTokenInfo` method.
  const tokenInfo = await oAuth2Client.getTokenInfo(
    oAuth2Client.credentials.access_token
  );

  return oAuth2Client.credentials.access_token;;
}

/**
* Create a new OAuth2Client, and go through the OAuth2 content
* workflow.  Return the full client to the callback.
*/
function getAuthenticatedClient() {
  return new Promise(async (resolve, reject) => {
    // create an oAuth client to authorize the API call.  Secrets are kept in a `keys.json` file,
    // which should be downloaded from the Google Developers Console.
    const oAuth2Client = new OAuth2Client(
      keys.web.client_id,
      keys.web.client_secret,
      keys.web.redirect_uris[0]
    );

    // Generate the url that will be used for the consent dialog.
    const authorizeUrl = oAuth2Client.generateAuthUrl({
      access_type: 'offline',
      scope: ['https://www.googleapis.com/auth/userinfo.profile',"https://www.googleapis.com/auth/userinfo.email"],
    });

    open(authorizeUrl, {wait: false}).then(cp => cp.unref());

    // Open an http server to accept the oauth callback. In this simple example, the
    // only request to our webserver is to /oauth2callback?code=<code>
    const server = http
      .createServer(async (req, res) => {
        try {
          if (req.url.indexOf('/oauth2callback') > -1) {
            // acquire the code from the querystring, and close the web server.
            const qs = new url.URL(req.url, 'http://localhost:8080/hello')
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