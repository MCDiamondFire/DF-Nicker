import * as Discord from "discord.js";
import { client } from "../../..";

module.exports.run = async (message: Discord.Message) => {
  message.delete();

  message
    .reply(`There you go: <${await client.generateInvite("ADMINISTRATOR")}>`)
    .then((msg: Discord.Message) => msg.delete({ timeout: 15 * 1000 }));
};

module.exports.config = {
  name: "invite"
};
