var {query} = require('../database/functions')
var debug = require('../functions/debug')

module.exports = async (client) => {
  var guildConfigs = (await query(1, "SELECT guildID, autoNick, verifiedRoles FROM guildConfig")).rows
  var linkedAccounts = (await query(0, "SELECT player_name, discord_id FROM linked_accounts WHERE discord_id != 'NULL' ORDER BY player_name DESC")).rows

  client.guilds.map(g => {
    var guildConfig = guildConfigs.find(row => row.guildID == g.id)
    var verifiedRoles = ""
    if(guildConfig && guildConfig.verifiedRoles != null)
      verifiedRoles = guildConfig.verifiedRoles.split(",")

    if(guildConfig && guildConfig.autoNick == 1) {
      g.members.map(m => {
        var linkedAccount = linkedAccounts.find(row => row.discord_id == m.id)

        if(linkedAccount && linkedAccount.player_name != m.displayName) {
          m.setNickname(linkedAccount.player_name, `Linked Minecraft Account`)
          .catch(err => debug.error(`AutoNick: ${err.message}`))
        }
        
        //* Add verified roles
        if(linkedAccount && linkedAccount.player_name == m.displayName && verifiedRoles != "" && !m.roles.some(r => verifiedRoles.includes(r.id))) {
          m.addRoles(verifiedRoles)
          .catch(err => debug.error(`AutoNick - VerifiedRoles: ${err.message}`))
        }
      })
    }
  })
  
}