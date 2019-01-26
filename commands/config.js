var Discord = require('discord.js')
var {query} = require('../database/functions')

exports.run = async function(client, message, params) {
  if(params.length > 0) params[0] = params[0].toLowerCase()

  switch(params[0]) {
    case "prefix":
      handlePrefix(message, params)
      break
    case "autonick":
      handleAutoNick(message, params)
      break
    case "verifiedroles":
      handleVerifiedRoles(message, params)
      break
    default:
      sendConfigEmbed(message)
      break
  }
}

async function handleAutoNick(message, params) {
  var {autoNick, prefix} = (await query(1, "SELECT autoNick, prefix FROM guildConfig WHERE guildID = ?", message.guild.id)).rows[0]

  if(params.length == 2 && params[1].toLowerCase() == "true" || params[1].toLowerCase() == "false") {
    await query(1, "UPDATE guildConfig SET autoNick = ? WHERE guildID = ?", [params[1] == "true" ? 1 : 0, message.guild.id])

    var embed = new Discord.RichEmbed()
    .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
    .setThumbnail(message.guild.iconURL)
    .setColor('#9090ff')
    .setTitle(`${message.guild.name}'s Config`)
    .setDescription(`*Guild's autoNick option changed successfully!*`)
    .addField("AutoNick", `\`\`${params[1]}\`\``, true)
  
    message.channel.send(embed)
  } else {
    var embed = new Discord.RichEmbed()
    .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
    .setThumbnail(message.guild.iconURL)
    .setColor('#9090ff')
    .setTitle(`${message.guild.name}'s Config`)
    .setDescription(`*Change the guild's autoNick option by typing\n\`\`${prefix}config autonick <true|false>\`\`.*`)
    .addField("AutoNick", `\`\`${autoNick == 0 ? "false" : "true"}\`\``, true)
  
    message.channel.send(embed)
  }
}

async function handlePrefix(message, params) {
  var {prefix} = (await query(1, "SELECT prefix FROM guildConfig WHERE guildID = ?", message.guild.id)).rows[0]

  if(params.length == 2) {
    await query(1, "UPDATE guildConfig SET prefix = ? WHERE guildID = ?", [params[1], message.guild.id])

    var embed = new Discord.RichEmbed()
    .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
    .setThumbnail(message.guild.iconURL)
    .setColor('#9090ff')
    .setTitle(`${message.guild.name}'s Config`)
    .setDescription(`*Guild's prefix changed successfully!*`)
    .addField("Old Prefix", `\`\`${prefix}\`\``, true)
    .addField("New Prefix", `\`\`${params[1]}\`\``, true)
  
    message.channel.send(embed)
  } else {
    var embed = new Discord.RichEmbed()
    .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
    .setThumbnail(message.guild.iconURL)
    .setColor('#9090ff')
    .setTitle(`${message.guild.name}'s Config`)
    .setDescription(`*Change the guild's prefix by typing\n\`\`${prefix}config prefix <prefix>\`\`.*`)
    .addField("Current Prefix", `\`\`${prefix}\`\``, true)
  
    message.channel.send(embed)
  }
}

async function handleVerifiedRoles(message, params) {
  var {prefix, verifiedRoles} = (await query(1, "SELECT prefix, verifiedRoles FROM guildConfig WHERE guildID = ?", message.guild.id)).rows[0]

  if(verifiedRoles != undefined) {
    var verifiedRolesOLD = verifiedRoles.split(",")
    var roles = await Promise.all(verifiedRolesOLD.map(verRole => {return message.guild.roles.get(verRole) != undefined ? message.guild.roles.get(verRole).name : null}))
    roles = roles.filter(role => role != null)
  }

  if(params.length >= 2) {
    var roles = params.slice(1, params.length).join("").split(";")
    roles = await Promise.all(roles.map(r => {
      var outPutRole = message.guild.roles.find(role => role.name.toLowerCase() == r.toLowerCase())
      if(!outPutRole) message.guild.roles.find(role => role.id == r)
      return outPutRole
    }))

    roles = roles.filter(r => r != null)

    if(roles.length > 0) {
      await query(1, "UPDATE guildConfig SET verifiedRoles = ? WHERE guildID = ?", [roles.map(r => r.id).join(","), message.guild.id])
  
      var embed = new Discord.RichEmbed()
      .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
      .setThumbnail(message.guild.iconURL)
      .setColor('#9090ff')
      .setTitle(`${message.guild.name}'s Config`)
      .setDescription(`*Guild's verified roles changed successfully!*`)
      .addField("Verified Role/s", `**${roles.map(r => r.name).join("**, **")}**`, true)
    } else {
      var embed = new Discord.RichEmbed()
      .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
      .setThumbnail(message.guild.iconURL)
      .setColor('#9090ff')
      .setTitle(`${message.guild.name}'s Config`)
      .setDescription(`*Could not find any of the given roles.*`)
    }
  
    message.channel.send(embed)
  } else {
    var embed = new Discord.RichEmbed()
    .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
    .setThumbnail(message.guild.iconURL)
    .setColor('#9090ff')
    .setTitle(`${message.guild.name}'s Config`)
    .setDescription(`*Change the guild's verify roles by typing\n\`\`${prefix}config verifiedRoles <roleName/roleID>\`\` (separate by \`\`;\`\`).*`)
    .addField("Verified Role/s", `${roles != undefined ? "**" + roles.join("**, **") + "**" : "\`\`None\`\`"}`, true)
  
    message.channel.send(embed)
  }
}

async function sendConfigEmbed(message) {
  var {prefix, autoNick, verifiedRoles} = (await query(1, "SELECT prefix, autoNick, verifiedRoles FROM guildConfig WHERE guildID = ?", message.guild.id)).rows[0]

  if(verifiedRoles != undefined) {
    var verifiedRolesOLD = verifiedRoles.split(",")
    var roles = await Promise.all(verifiedRolesOLD.map(verRole => {return message.guild.roles.get(verRole) != undefined ? message.guild.roles.get(verRole).name : null}))
    roles = roles.filter(role => role != null)
  }

  var embed = new Discord.RichEmbed()
  .setFooter(`${message.client.user.username} by Timeraa`, message.client.user.avatarURL)
  .setThumbnail(message.guild.iconURL)
  .setDescription(`*Edit a setting by typing* \`\`\`${prefix}config <prefix/autoNick/verifiedRoles>\`\`\``)
  .setColor('#9090ff')
  .setTitle(`${message.guild.name}'s Config`)
  .addField("Prefix", `\`\`${prefix}\`\``, true)
  .addField("AutoNick", `\`\`${autoNick == 0 ? "false" : "true"}\`\``, true)

  if(verifiedRoles != undefined)
    embed.addField("Verified Role/s", `**${roles.join("**, **")}**`);
  else
    embed.addField("Verified Role/s", `\`\`None\`\``);
    

  message.channel.send(embed)
}

exports.conf = {
  enabled: true,
  permLevel: 2
};

exports.help = {
  name: 'config',
  description: 'Change the guild config.',
  usage: 'config'
};