const debug = require("../functions/debug");

module.exports = async g => {
  const {query} = require("../database/functions")
  
  debug.info(`Left guild ${g.name} (${g.id})`)

  query(1, "DELETE FROM guildConfig WHERE guildID = ?", g.id)
  query(1, "DELETE FROM ignoredRoles WHERE guildID = ?", g.id)
}