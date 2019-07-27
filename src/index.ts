//* Load .env file
import { config } from "dotenv";
config();
import * as Discord from "discord.js";
import { error, success } from "./util/debug";
import moduleLoader from "./util/moduleLoader";
import { connect } from "./database/client";
import * as mysql from "mysql2/promise";

export var connection;

//* Create new client & set login presence
var client = new Discord.Client({
  presence:
    process.env.NODE_ENV == "dev"
      ? {
          status: "dnd",
          activity: {
            name: "DEV MODE",
            type: "WATCHING"
          }
        }
      : {
          status: "online",
          activity: {
            name: "Timeraa",
            type: "LISTENING"
          }
        }
});

//* Commands, Command aliases, Command permission levels
client.commands = new Discord.Collection();
client.aliases = new Discord.Collection();
client.elevation = (message: Discord.Message) => {
  //* Permission level checker
  var permlvl: Number = 0;

  if (!message.member) return 0;

  //* Admin
  if (message.member.hasPermission("ADMINISTRATOR")) permlvl = 1;
  //* Timeraa
  if (message.author.id === "223238938716798978") permlvl = 2;

  //* Return permlvl
  return permlvl;
};

//! Make sure that database is connected first then proceed
(async () => {
  //* Connect to Mongo DB
  await connect()
    .then(_ => success("Connected to the database"))
    .catch((err: Error) => {
      error(`Could not connect to database: ${err.name}`);
      process.exit();
    });

  connection = await mysql.createConnection({
    host: process.env.SQLHOST,
    user: process.env.SQLUSER,
    password: process.env.SQLPASS,
    database: process.env.SQLDB
  });

  client.login(process.env.TOKEN).then(() => {
    moduleLoader(client);
  });
})().catch(error);

export { client };
