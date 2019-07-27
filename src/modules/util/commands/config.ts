import * as Discord from "discord.js";
import { MongoClient } from "../../../database/client";

var coll = MongoClient.db("DF-Nicker").collection("GuildConfig");

module.exports.run = async (
  message: Discord.Message,
  params: Array<string>
) => {
  message.delete();

  var guildConfig = await coll.findOne({ guildId: message.guild.id });

  if (
    params.length < 2 ||
    (params.length > 2 && params[0].toLowerCase() != "ignore")
  ) {
    var embed = new Discord.MessageEmbed({
      author: {
        name: message.guild.name,
        iconURL: message.guild.iconURL({ size: 128 })
      },
      color: "#ff3300",
      fields: [
        {
          name: "Prefix (prefix)",
          value: `**\`\`${guildConfig.prefix}\`\`**`,
          inline: true
        },
        {
          name: "Auto Nick (autoNick)",
          value: `**\`\`${guildConfig.autoNick ? "ON" : "OFF"}\`\`**`,
          inline: true
        },
        {
          name: "Ignored Roles/Users (ignore)",
          value:
            guildConfig.ignore.length == 0
              ? "``NONE``"
              : guildConfig.ignore
                  .map(i => {
                    var userRole = message.guild.roles.has(i)
                      ? message.guild.roles.get(i).name
                      : undefined;
                    if (!userRole)
                      userRole = message.guild.members.has(i)
                        ? message.guild.members.get(i).displayName
                        : undefined;

                    return `\`\`${userRole}\`\``;
                  })
                  .join(", ")
        },
        {
          name: "\u200B",
          value: `*Change a config's value by using \`\`${
            guildConfig.prefix
          }config autoNick on/off\`\`*`
        }
      ]
    });

    message.channel
      .send(embed)
      .then((msg: Discord.Message) => msg.delete({ timeout: 30 * 1000 }));
    return;
  }

  if (params[0].toLowerCase() === "prefix") {
    coll.findOneAndUpdate(
      { guildId: message.guild.id },
      { $set: { prefix: params[1] } }
    );

    message.channel
      .send(`Successfully changed prefix to **\`\`${params[1]}\`\`**`)
      .then((msg: Discord.Message) => msg.delete({ timeout: 15 * 1000 }));

    return;
  }

  if (params[0].toLowerCase() === "autonick") {
    if (!["on", "off"].includes(params[1].toLowerCase())) {
      message.channel
        .send("Wrong config value! Either use ``off`` or ``on``")
        .then((msg: Discord.Message) => msg.delete({ timeout: 5 * 1000 }));
      return;
    }

    coll.findOneAndUpdate(
      { guildId: message.guild.id },
      { $set: { autoNick: params[1].toLowerCase() === "on" ? true : false } }
    );

    message.channel
      .send(`Auto Nick is now **\`\`${params[1]}\`\`**`)
      .then((msg: Discord.Message) => msg.delete({ timeout: 15 * 1000 }));

    return;
  }

  if (params[0].toLowerCase() === "ignore") {
    var roleUser = undefined;

    //* Find roles
    if (!roleUser && message.mentions.roles.size > 0)
      roleUser = message.mentions.roles.first();
    if (!roleUser)
      roleUser = message.guild.roles.find(
        m =>
          m.name.toLowerCase() ==
          params
            .slice(1, params.length)
            .join(" ")
            .toLowerCase()
      );

    //* Find user
    if (message.mentions.users.size > 0)
      roleUser = message.mentions.users.first();
    if (!roleUser)
      roleUser = message.guild.members.find(
        m =>
          m.displayName.toLowerCase() ==
          params
            .slice(1, params.length)
            .join(" ")
            .toLowerCase()
      );

    if (guildConfig.ignore.includes(roleUser.id)) {
      guildConfig.ignore = guildConfig.ignore.filter(id => id != roleUser.id);

      message.channel
        .send(
          `Successfully removed ${
            typeof roleUser.name === "undefined" ? "user" : "role"
          } **${roleUser.name || roleUser.displayName}** from the ignored list`
        )
        .then((msg: Discord.Message) => msg.delete({ timeout: 10 * 1000 }));
    } else {
      guildConfig.ignore.push(roleUser.id);

      message.channel
        .send(
          `Successfully added ${
            typeof roleUser.name === "undefined" ? "user" : "role"
          } **${roleUser.name || roleUser.displayName}** to the ignored list`
        )
        .then((msg: Discord.Message) => msg.delete({ timeout: 10 * 1000 }));
    }

    coll.findOneAndUpdate(
      { guildId: message.guild.id },
      { $set: { ignore: guildConfig.ignore } }
    );
  }
};

module.exports.config = {
  name: "config",
  permLevel: 1
};
