import * as Discord from "discord.js";
import { MongoClient } from "../database/client";
import { connection } from "..";

var coll = MongoClient.db("DF-Nicker").collection("GuildConfig");

module.exports = async (oldMember: Discord.GuildMember, newMember: Discord.GuildMember) => {
  if(oldMember.displayName === newMember.displayName) return

  var guildConfig = await coll.findOne({ guildId: newMember.guild.id }),
    [rows] = await connection.query(
      "SELECT player_name FROM linked_accounts WHERE discord_id = ?",
      newMember.id
    );

  if (
    guildConfig.autoNick &&
    rows.length > 0 &&
    rows[0].player_name !== newMember.displayName
  )
    newMember
      .setNickname(rows[0].player_name, `Nickname not matching Minecraft name.`)
      .catch(() => {});
};
