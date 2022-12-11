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

    findByAccessToken(accessToken) {
        return {
            name: 'Макс Іванишен',
            email: 'maxivanyshen@gmail.com',
            access_token: 'ya29.a0AeTM1idsMA19r5bnVc2nHO6UTu7cL8dmyZko8iMbFPWYDcJ8EJoKCeBkbAbZmYcaomiaQoY7Q9_A8jus1kwgW3TwFb8FfRhNGBTl7HMKCh_dGj_G-3HUKKd6ttELtaQxYLE-6afCrw-PR05NuK39mjVsib6AaCgYKAfISARMSFQHWtWOmEPb2B6ODzK3pJXJdEQFtVg0163',
            expires: 1670778942980,
            refresh_token: '1//09KblTETXgFo-CgYIARAAGAkSNwF-L9IrR0hDwhxNKZOLX10-woyNLcZH-6rinRfmzB_OVxoWdX0wGOnqXofD2bHBrf3yQZo29cg'
          };
    }
}
