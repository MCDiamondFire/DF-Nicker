import { client, connection } from "..";
import { MongoClient } from "../database/client";

export async function autoNick() {
  var [rows] = await connection.query(
      "SELECT player_name, discord_id FROM linked_accounts"
    ),
    coll = MongoClient.db("DF-Nicker").collection("GuildConfig"),
    gConfigs = await coll.find({ autoNick: true }).toArray(),
    guildsToNick = client.guilds.filter(g =>
      gConfigs.map(gConfig => gConfig.guildId).includes(g.id)
    );

  guildsToNick.map(g => {
    var gConfig = gConfigs.find(gC => gC.guildId === g.id),
      usersToNick = g.members.filter(
        m =>
          !gConfig.ignore.includes(m.id) &&
          !m.roles.find(r => gConfig.ignore.includes(r.id))
      );

    usersToNick.map(m => {
      // @ts-ignore
      var row = rows.find(row => row.discord_id === m.id);
      if (row && row.player_name !== m.displayName) {
        console.log(row.player_name);
        m.setNickname(
          row.player_name,
          `Nickname not matching Minecraft name.`
        ).catch(() => {});
      }
    });
  });
}
