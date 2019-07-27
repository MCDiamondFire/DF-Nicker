import * as Discord from "discord.js";
import { MongoClient } from "../database/client";
import { connection } from "..";

var coll = MongoClient.db("DF-Nicker").collection("GuildConfig");

module.exports = async (member: Discord.GuildMember) => {
  var guildConfig = await coll.findOne({ guildId: member.guild.id }),
    [rows] = await connection.query(
      "SELECT player_name FROM linked_accounts WHERE discord_id = ?",
      member.id
    );

  if (
    guildConfig.autoNick &&
    //@ts-ignore
    rows.length > 0 &&
    rows[0].player_name !== member.displayName
  )
    member
      .setNickname(rows[0].player_name, `Nickname not matching Minecraft name.`)
      .catch(() => {});
};
