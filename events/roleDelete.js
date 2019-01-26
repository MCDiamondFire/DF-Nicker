var {query} = require('../database/functions')

module.exports = async role => {
  var {rows} = await query(1, "SELECT verifiedRoles FROM guildConfig WHERE guildID = ?", role.guild.id)
  
  if(rows.length > 0) {
    query(1, "UPDATE guildConfig SET verifiedRoles = ? WHERE guildID = ?", [rows[0].verifiedRoles.replace(`,${role.id}`, ""), role.guild.id])
  }
}