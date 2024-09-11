const mongoose = require('mongoose');
async function connect() {
    try {
        await mongoose.connect('mongodb://localhost:27017/myDb');
        console.log('Connected successfully');

    }
    catch(error) {
        console.log('failed');
    }
}

module.exports = { connect };