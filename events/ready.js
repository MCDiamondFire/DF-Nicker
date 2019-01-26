var {query} = require('../database/functions')

var debug = require("../functions/debug");

module.exports = async client => {
  var guildConfigs = (await query(1, "SELECT guildID FROM guildConfig")).rows
  client.guilds.map(g => {
    var guildConfig = guildConfigs.find(row => row.guildID == g.id)

    if(!guildConfig) {
      query(1, "INSERT INTO guildConfig (guildID) VALUES (?)", g.id)
    }
  })

  //* Set status
  client.user.setStatus(process.env.NODE_ENV == "dev" ? "dnd" : "online")
  var memberCount = 0;
  client.guilds.map(g => memberCount += g.members.size)
  client.user.setActivity(process.env.NODE_ENV == "dev" ? "Timeraa code" : `${memberCount} Nicknames | dfn!config`, { type: "WATCHING" })
  
  //* Notify what account is used
  debug.success(`Logged in as ${client.user.tag}.`)

  require('../functions/autoNick')(client)
  setInterval(() => require('../functions/autoNick')(client), 15*60*1000)
}