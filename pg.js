
/**
 * https://node-postgres.com/features/connecting
 * 
 */

const { Pool, Client } = require("pg");

const pool = new Pool({
  host: "localhost",
  port: 5432,
  database: "postgres",
  user: "postgres",
  password: "root",
});

pool
  .query("SELECT NAME, PRICE FROM TBL_ITEM")
  .then((res) => res.rows.forEach((row) => console.log(row)))
  .catch(console.log)
  .finally(pool.end());

// const client = new Client({
//   user: 'postgres',
//   host: 'localhost',
//   database: 'postgres',
//   password: 'root',
//   port: 5432,
// })
// client.connect()
// client.query('SELECT NAME, PRICE FROM TBL_ITEM', (err, res) => {
//   console.log(res)
//   client.end()
// })
