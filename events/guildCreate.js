const debug = require("../functions/debug");

module.exports = async g => {
  const {query} = require("../database/functions")
  
  debug.info(`Joined guild ${g.name} (${g.id})`)

  query(1, "INSERT INTO guildConfig (guildID) VALUES (?)", g.id)
}