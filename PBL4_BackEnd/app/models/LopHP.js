const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const LopBoMon = Schema({ 
    MaBM:{type:String},
    TenLopBM: {type:String},
    IdBM:{type:String},
    SoLuong:{type:Number}
})

module.exports = mongoose.model('LopBoMon',LopBoMon);