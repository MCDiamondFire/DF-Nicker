const reqEvent = (event) => require(`../events/${event}`)

module.exports = client => {
  client.on('ready', () => reqEvent('ready')(client));
  client.on('message', reqEvent('message'))
  client.on('guildMemberUpdate', reqEvent('guildMemberUpdate'))
  client.on('guildDelete', reqEvent('guildDelete'))
  client.on('guildCreate', reqEvent('guildCreate'))
  client.on('roleDelete', reqEvent('roleDelete'))
};