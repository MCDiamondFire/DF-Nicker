var chalk = require("chalk");

module.exports.info = (message) => {
  //* Return and don't log if not NODE_ENV != dev
  if(process.env.NODE_ENV != "dev" && process.env.NODE_ENV != "contributor") return
  console.log(chalk.white("[DF Nicker] ") + chalk.hex("#5050ff")(message))
}

module.exports.success = (message) => {
  //* Return and don't log if not NODE_ENV != dev
  if(process.env.NODE_ENV != "dev" && process.env.NODE_ENV != "contributor") return
  console.log(chalk.white("[DF Nicker] ") + chalk.hex("#50ff50")(message))
}

module.exports.error = (message) => {
  //* Return and don't log if not NODE_ENV != dev
  if(process.env.NODE_ENV != "dev" && process.env.NODE_ENV != "contributor") return
  console.log(chalk.white("[DF Nicker] ") + chalk.hex("#ff5050")(message))
}