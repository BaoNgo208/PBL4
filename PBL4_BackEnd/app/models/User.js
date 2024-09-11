const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const AutoIncrement = require('mongoose-sequence')(mongoose);

const User = new Schema({
     mssv:{type:Number},
     name: {type:String },
     password:{type:String},
     email:{type:String},
     lop : {type:String},
     khoa:{type:String},
     LopDaDK: [{
          MaBM:{type:String},
          IdBM:{type:String},
          _id: false
     }],
     Img:{type:String}

});

User.plugin(AutoIncrement, {inc_field: 'mssv', disable_hooks: true})

module.exports = mongoose.model('User',User)