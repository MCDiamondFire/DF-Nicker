var Discord = require('discord.js')

exports.run = async function(client, message) {
  var embed = new Discord.RichEmbed()
  .setFooter(`${client.user.username} by Timeraa`, client.user.avatarURL)
  .setThumbnail(client.user.avatarURL)
  .setColor('#9090ff')
  .setTitle("Want to invite me? Great!")
  .setDescription("You need someone to manage the nicknames of your Discord server? I'm up to the task!\n\nSimply invite me by following [this link](https://discordapp.com/api/oauth2/authorize?client_id=477878457225969664&permissions=8&scope=bot) and I'll start managing them for you!")

  message.channel.send(embed)
}

exports.conf = {
  enabled: true,
  permLevel: 0
};

exports.help = {
  name: 'invite',
  description: 'Invite me to your server! :D',
  usage: 'invite'
};