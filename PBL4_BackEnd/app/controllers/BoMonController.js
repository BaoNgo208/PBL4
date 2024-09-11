const BoMon = require('../models/BoMon')

class BoMonController{
    getBm(req,res,next) {

        BoMon.find({Khoa : req.params.khoa})
        .then((bomon) => {
           if(bomon != null) {
        
            console.log(bomon);
            res.status(200).send(JSON.stringify(bomon));

           }
           else {
            res.status(404).send();
           
           }
        })
    }

    async createBoBom(req,res,next) {
        const body = {
             IdBM:req.body.IdBM,
             TenBM:req.body.TenBM,
             Khoa:req.body.Khoa 
        };

        const existedBoMon = await BoMon.findOne({IdBM : body.IdBM});

        if(existedBoMon != null) {
             res.status(400).send();
        }
        else {
             var newBoMon = new BoMon(body);
             newBoMon.save();
             res.status(200).send();
        }

    }
}

module.exports = new BoMonController;