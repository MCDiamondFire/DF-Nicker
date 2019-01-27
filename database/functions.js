//* Require connection
let dbConnections = require('./db')

/**
 * Query the database
 * @param {Number} db 0 = DF, 1 = BotDB
 * @param  {...any} data Query data
 */
exports.query = (db, ...data) => {
  return new Promise((resolve, reject) => {
    //* Use dfnicker db if contributor
    if(process.env.NODE_ENV == "contributor") db = 1
    
    dbConnections[db].query(...data, (err, rows, fields, result) => {
          if (err) return reject(err);
          resolve({ rows, fields, result });
      });
  }).catch(err => console.log("Error while querying + " + err))
}

/**
 * Switch to a different database
 * @param {Number} db 0 = DF, 1 = BotDB
 * @param  {...any} data Query data
 */
exports.switchTos = function switchTo(con, database) {
  return new Promise((resolve, reject) => {
    con.changeUser({ database }, err => {
      if (err) return reject(err);
      resolve();
    });
  });
}