const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Lop = new Schema({
     name:{type : String},
     khoa:{type:String},
     SoLuong:{type:String}
})

module.exports = mongoose.model('Lop',Lop);
