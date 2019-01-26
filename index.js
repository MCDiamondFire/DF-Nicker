//* Load .env
require('dotenv').load();

//#region imports
var Discord = require("discord.js");
var fs = require('fs');

var debug = require("./functions/debug");
require('./database/db')
//#endregion

//#endregion
//* Initialize client
var client = new Discord.Client();
require('./util/eventLoader')(client);

client.elevation = message => {
  let permlvl = 0
  if(message.member.permissions.has("MANAGE_NICKNAMES", true))  permlvl = 1
  if(message.member.permissions.has("ADMINISTRATOR", true))  permlvl = 2
  if (message.author.id === "223238938716798978") permlvl = 3
  return permlvl
};

//#region Commands
client.commands = new Discord.Collection();
client.aliases = new Discord.Collection();
fs.readdir('./commands/', (err, files) => {
  if (err) console.error(err);
  debug.info(`Loading ${files.length} commands...`);
  files.forEach(f => {
    let props = require(`./commands/${f}`);
    debug.info(`Loaded Command: ${props.help.name}`);
    client.commands.set(props.help.name, props);
  });
});
//#endregion

//! Login to client using token
client.login(process.env.token)
.catch(debug.error)