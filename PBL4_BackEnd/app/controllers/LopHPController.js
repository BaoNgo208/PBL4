const LopHP = require('../models/LopHP');
const User = require('../models/User');
class LopHPController  {
    getLopHp(req,res,next) {
        
         LopHP.find({IdBM:req.params.IdBM})
         .then((lophp) => {
            if(lophp != null) {
                res.status(200).send(JSON.stringify(lophp));
            }
            else {
                res.status(400).send();
            
            }
         })
    }

   async addLopHP(req,res,next) {
         const newLopHP = {
             MaBM: req.body.MaBM,
             TenLopBM:req.body.TenLopBM,
             IdBM:req.body.IdBM,
             SoLuong:req.body.SoLuong
         };
      

         const lopHp =  await LopHP.findOne({MaBM : newLopHP.MaBM});

         if(lopHp == null) {

             var newLop = new LopHP(newLopHP);
             newLop.save();
             res.status(200).send();
         }
         else {
            res.status(400).send();
         }

    }

    async deleteLopHP(req,res,next) {
         const student =  await User.find({'LopDaDK.MaBM' : req.params.MaBM}) ;
             
         var newLopHP = [];
         for(var  i = 0; i< student.length;i++ ) {
            var size = student[i].LopDaDK.length;
        
              for(var j = 1 ; j < size;i++ ) {
              
                    if(student[i].LopDaDK[j].MaBM == req.params.MaBM  ) {
                            newLopHP = student[i].LopDaDK.filter((lop) => lop.MaBM !== req.params.MaBM);
                          
                        
                            User.findOneAndUpdate({'LopDaDK.MaBM' : req.params.MaBM}, {LopDaDK : newLopHP})
                            .then((user) => {
                                  
                            LopHP.deleteOne({MaBM : req.params.MaBM})
                            .then((lop) => {
                                console.log(lop);
                                res.status(200).send();
                            }) ;

                           });
                           break;       
                                             
                    }     
              }
            
         }
     
    }

    async updateLopHP(req,res,next) {
        const newLopHP = {
            MaBM: req.body.MaBM,
            TenLopBM:req.body.TenLopBM,
            IdBM:req.body.IdBM,
            SoLuong:req.body.SoLuong
        };


    
        const lopHp =  await LopHP.findOne({MaBM : newLopHP.MaBM});
     
        var lopCurrent_size = await User.count({'LopDaDK.MaBM':lopHp.MaBM});

        if(lopCurrent_size > newLopHP.SoLuong) {
             res.status(401).send();
        }
        else {
              
        if(lopHp != null) {

            LopHP.updateOne({MaBM:newLopHP.MaBM},newLopHP,{new: true})
            .then( (lophp) => {
                res.status(200).send();
    
            }
            )
            .catch(next)
        }
        else {
           res.status(400).send();
        }
        }

       
         
    }
}

module.exports = new LopHPController;
