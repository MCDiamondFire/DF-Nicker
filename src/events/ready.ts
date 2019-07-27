import { success } from "../util/debug";

import * as Discord from "discord.js";
import { MongoClient } from "../database/client";

import { autoNick } from "../util/autoNick";

var coll = MongoClient.db("DF-Nicker").collection("GuildConfig");
module.exports.run = async (client: Discord.Client) => {
  var guildConfigs = await coll.find().toArray();

  client.guilds.map(g => {
    if (typeof guildConfigs.find(gC => gC.guildId === g.id) === "undefined")
      coll.insertOne({
        guildId: g.id,
        prefix: "dfn!",
        autoNick: false,
        ignore: []
      });
  });

  if (client.user) success(`Connected as ${client.user.tag}`);

  autoNick();
};

module.exports.config = {
  clientOnly: true
};
