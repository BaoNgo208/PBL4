const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const BoMon= new Schema({
    IdBM:{type:String},
    TenBM: {type:String },
    Khoa:{type:String},
   
})

module.exports = mongoose.model('BoMon',BoMon)