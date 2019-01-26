var debug = require('../functions/debug'),
  { query } = require('../database/functions')

module.exports = async (oldMember, newMember) => {
  if (oldMember.displayName != newMember.displayName) {
    var {autoNick} = (await query(1, "SELECT autoNick FROM guildConfig WHERE guildID = ?", newMember.guild.id)).rows[0]

    if(autoNick == 1) {
      var player_name = (await query(0, "SELECT player_name FROM linked_accounts WHERE discord_id = ? ORDER BY player_name DESC LIMIT 1", newMember.id)).rows
  
      if(player_name.length > 0)
        player_name = player_name[0].player_name;
      else
        return;
  
      newMember.setNickname(player_name, `Linked Minecraft Account`)
      .catch(err => debug.error(`AutoNick: ${err.message}`))
    }
  }
}