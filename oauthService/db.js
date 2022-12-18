const { IdTokenClient } = require('google-auth-library');
const { Client } = require('pg');
const { connect } = require('puppeteer');

const client = new Client({
    user: "postgres",
    password: "psql",
    database: "blackjack_users"
}); 

client.connect((err) => {
    if (err) {
      console.error('connection error', err.stack)
    } else {
      console.log('connected')
    }
});

async function save(userData) {
    let user = await client.query("SELECT * FROM users WHERE email = $1", [userData.email]).then(res => res.rows[0]).then(data => {return data;});
    if(user == undefined) {
        client.query("INSERT INTO users(name, email, access_token, expires, refresh_token) VALUES($1, $2, $3, $4, $5);", [userData.name, userData.email, userData.access_token, userData.expires, userData.refresh_token]);
    } else {
        client.query("UPDATE users SET access_token = $1, expires = $2 WHERE email = $3", [userData.access_token, userData.expires, userData.email]);
    }
    return 0;
}

function selectAll() {
    client.query('SELECT * FROM users', (err, res) => {
        if(err) {
            console.log("connection error: " + err.stack);
        } else {
            console.log(res.rows);
        }
    });
}

function findByAccessToken(accessToken) {
    let user = client.query("SELECT * FROM users WHERE access_token = $1", [accessToken]).then(res => res.rows[0]).then(data => {return user = data;});
    return user;
}

module.exports =  {
    save: save,
    selectAll: selectAll,
    findByAccessToken: findByAccessToken
}
