import * as Discord from "discord.js";
import { MongoClient } from "../database/client";

var coll = MongoClient.db("DF-Nicker").collection("GuildConfig");

module.exports = (guild: Discord.Guild) => {
  coll.findOneAndDelete({ guildId: guild.id });
};
