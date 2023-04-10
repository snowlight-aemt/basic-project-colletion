const knexConfig = require('./db/knexfile');
require('dotenv').config();
const knex = require('knex')(knexConfig[process.env.NODE_ENV]);

knex.raw('select * from gifts').then(result => {
    console.log(result[0]);
    console.log('----');
    console.log(result[1]);
});

knex.transaction(trx => {
    trx.raw('select * from gifts where id = ?', '18').then(result => {
        console.log(result[0]);
    })
    .catch(e => {
        console.error(e);
    });

    const id = 19
    trx.raw(`select * from gifts where id = '${id}'`).then(result => {
        console.log(result[0]);
    })
    .catch(e => {
        console.error(e);
    });
});