const knexConfig = require('./db/knexfile');
require('dotenv').config();
const knex = require('knex')(knexConfig[process.env.NODE_ENV]);

(async function () {
    let raw;
    try {
        raw = await knex('gifts').select("*").where({id: 18});
    } catch (e) {
        console.error(e);
    } finally {
        knex.destroy();
    }
    console.log(raw);
})();
