const express = require('express')
const app = express()
const morgan = require('morgan');
const mongoClient = require('mongodb').MongoClient
const db = require('./config/db/index')
const url = "mongodb://127.0.0.1:27017";
var methodOverride = require('method-override')

const route = require('./routes');


db.connect();
app.use(express.json())
app.use(morgan('combined'));
app.use(methodOverride('_method'))




route(app);
app.listen(3000, () => {
    console.log("Listening on port 3000...")
})