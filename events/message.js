
var {query} = require('../database/functions')

module.exports = async message => {
  let client = message.client;
  let guild = message.guild

  var {rows} = await query(1, 'SELECT prefix FROM guildConfig WHERE guildID = ?', guild.id)

  if(rows.length > 0) {
    var prefix = rows[0].prefix
  } else {
    var prefix = null
  }

  
  
  if (message.author.bot) return;
  if (!message.content.startsWith(prefix) || prefix == null) return;
  let command = message.content.split(' ')[0].slice(prefix.length).toLowerCase();
  let params = message.content.split(' ').slice(1);
  let perms = client.elevation(message);
  let cmd;
  if (client.commands.has(command)) {
    cmd = client.commands.get(command);
  } else if (client.aliases.has(command)) {
    cmd = client.commands.get(client.aliases.get(command));
  }
  if (cmd) {
    message.delete();
    if (perms < cmd.conf.permLevel) return;
    cmd.run(client, message, params, perms);
  }
};