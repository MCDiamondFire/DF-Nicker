var mysql = require('mysql'), 
    debug = require('../functions/debug'),
    dfSQL,
    botSQL

module.exports = function(client) {
    //* If no connection exists -> create one
    if(!dfSQL) {
        dfSQL = mysql.createConnection({
            host: 'mcdiamondfire.com',
            user: process.env.dfuser,
            password: process.env.dfpass,
            database: 'hypercube',
            multipleStatements: true
        });
        
        //* Create connection
        dfSQL.connect(function (err) {
            if (err) debug.error(err); else
            debug.success("DFSQL connected!")
        });
    }
    
    //* If no connection exists -> create one
    if(!botSQL) {
        //! localhost -> premid.app if development instance
        botSQL = mysql.createConnection({
            host: "localhost",
            user: process.env.botuser,
            password: process.env.botpass,
            database: 'dfnicker',
            multipleStatements: true
        });
        
        //* Create connection
        botSQL.connect(function (err) {
            if (err) debug.error(err); else
            debug.success("BOTSQL connected!")
        });
    }
    return [dfSQL, botSQL]
}()