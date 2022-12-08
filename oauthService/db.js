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
  })

module.exports =  {

    save: function(userData) {
        client.query("INSERT INTO users(name, email, access_token, expires, refresh_token) VALUES($1, $2, $3, $4, $5);", [userData.name, userData.email, userData.access_token, userData.expires, userData.refresh_token]);
        return 0;
    },

    selectAll: function() {
        client.query('SELECT * FROM users', (err, res) => {
            if(err) {
                console.log("connection error: " + err.stack);
            } else {
                console.log(res.rows);
            }
        });
    },
}
