const userRouter= require('./user');
const bomonRouter = require('./bomon');
const lophpRouter = require('./lophp');


function route(app) {
    app.use('/',userRouter)
    app.use('/',bomonRouter);
    app.use('/',lophpRouter)
}
module.exports = route;