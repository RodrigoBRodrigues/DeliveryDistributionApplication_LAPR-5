import 'reflect-metadata'; // We need this in order to use @Decorators

import config from '../config';

import express from "express";

import Logger from './loaders/logger';

async function startServer() {
  const app = express();

  await require('./loaders').default({ expressApp: app });

  app.listen(config.port, () => {

    console.log("Server listening on port: " + config.port);

    Logger.info(`
      ################################################
      🛡️  Server listening on port: ${config.port} 🛡️ 
      ################################################
    `);
    
    })
    .on('error', (err) => {      
      Logger.error(err);
      process.exit(1);
      return;
  });
}

async function startServerHttps() {
var fs = require('fs'),
    https = require('https'),
    app = express();
    await require('./loaders').default({ expressApp: app });
    https.createServer({
      key: fs.readFileSync('privatekey.pem'),
      cert: fs.readFileSync('certificate.pem')
    }, app).listen(3001);
    console.log("Server Started at: https://localhost:3001"); 
  }


startServerHttps();
startServer();
