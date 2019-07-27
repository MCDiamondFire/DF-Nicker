import * as Discord from "discord.js";
import { MongoClient } from "../database/client";

module.exports = async (message: Discord.Message) => {
  var guildConfig = await MongoClient.db("DF-Nicker")
    .collection("GuildConfig")
    .findOne({ guildId: message.guild.id });

  //* Message is command
  if (!message.content.startsWith(guildConfig.prefix)) return;

  //* Prevent bots
  if (message.author && message.author.bot) return;

  var command = message.content.split(" ")[0].slice(guildConfig.prefix.length),
    params = message.content.split(" ").slice(1),
    perms = message.client.elevation(message),
    cmd: any;

  //* Get current command from commands/aliases
  if (message.client.commands.has(command))
    cmd = message.client.commands.get(command);
  else if (message.client.aliases.has(command)) {
    cmd = message.client.commands.get(message.client.aliases.get(command));
  }

  //* Run command if found
  if (cmd) {
    //TODO Send fancy no permission message
    if (
      typeof cmd.config.permLevel != "undefined" &&
      perms < cmd.config.permLevel
    )
      return;

    //* Run the command
    cmd.run(message, params, perms);
  }
};
