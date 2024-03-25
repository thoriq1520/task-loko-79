import bodyParser from "body-parser";
import express from "express";
import Controllers from "./controller/sendMessage.controller.js";
import KafkaConfig from "./config/kafka.config.js";
import mongoose from "mongoose";
const app = express()
const jsonParser = bodyParser.json()

//api post data to kafka
app.post("/api/send", jsonParser, Controllers.sendMessage)

mongoose.connect('mongodb://127.0.0.1:27017/db-loko', { useNewUrlParser: true, useUnifiedTopology: true });
const db = mongoose.connection;

db.on('error', console.error.bind(console, 'Koneksi MongoDB gagal:'));
db.once('open', function() {
  console.log('Terhubung ke MongoDB');
});
// consume message from kafka
const kafkaConfig = new KafkaConfig()
kafkaConfig.consume('info-loko', (value) =>{
    console.log(value);
})

app.listen(8080, () => {
    console.log("Server is running on port 8080.")
})