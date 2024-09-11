const User = require("../models/User");
const Admin = require("../models/Admin");
const Lop = require("../models/Lop");
const BoMon = require("../models/BoMon");
const LopHP = require("../models/LopHP");

var index_mssv=102210000;


class UserController {

    
    login(req,res,next) {
        const query = {
            email:req.body.email,
            password:req.body.password
       }
  
       Admin.findOne(query)
       .then((admin) => {
           if(admin != null) {
              res.status(200).send();
           } 
           else {
               res.status(400).send();
           }
       })
       .catch(next)
    }

    signup(req,res,next) {
             const newAdmin = {
         name: req.body.name,
         email: req.body.email,
         password: req.body.password,
     }
 
     console.log(newAdmin);
 
     const query = { email: newAdmin.email }
 
 
     Admin.findOne(query)
     .then((admin) => {
         if(admin == null) {
             var newadmin = new Admin(newAdmin);
             newadmin.save();
             res.status(200).send();
         }
         else {
             res.status(400).send();
            
         }
 
     })
     .catch(next)
 
    }

    getKhoa(req,res,next) {
        Lop.find({khoa: req.params.khoa})
             .then(lops => {
                if(lops != null) {
                    res.status(200).send(JSON.stringify(lops));
                }
                else {
                     res.status(404).send();
                }
              
              
         })
    }
    getLop(req,res,next) {
        User.find({ lop : req.params.lop})
             .then((users)=> {
                 if(users != null) {
                        res.status(200).send(JSON.stringify(users));
                 }
                 else{
                     res.status(404).send();
                 }
             })
             .catch(next)
    }

    createLop(req,res,next) {
        const newLop = {
                      name : req.body.name,
                      khoa: req.body.khoa,
                      SoLuong:"40"
                  }
           
        Lop.findOne(newLop)
        .then(lop => {
            if(lop == null) {
            var newlop = new Lop(newLop);
            newlop.save();
            res.status(200).send();
            }
            else {
                res.status(400).send();
            }
        })
    }

    // getAll(req,res,next) {
    //     User.find({})
    //         .then((users) => {
    //              res.status(200).send(JSON.stringify(users));
    //              console.log(users);
    //         })
    //         .catch()
    // }

    async createStudent(req,res,next) {
           var count = await User.estimatedDocumentCount();
           var lopCurrent_size = await User.count({lop: req.body.lop});
           var lopMaxSize = await Lop.findOne({name: req.body.lop});
           var latestCount = await User.find({}).sort({mssv:-1}).limit(1);
            console.log("max",latestCount[0].mssv );
            if(lopCurrent_size  < parseInt(lopMaxSize.SoLuong)) {
                var current_mssv = latestCount[0].mssv + 1  ;
                const newStudent = { 
                     mssv: current_mssv,
                     email:req.body.email,
                     name : req.body.name,
                     password:req.body.password,
                     lop:req.body.lop,
                     khoa:req.body.khoa,
                     Img:req.body.Img
                }
                User.findOne({email : newStudent.email})
                .then((student)=> {
                    if(student == null) {
                            var newstudent = new User(newStudent);
                            newstudent.save();
                            res.status(200).send();
                        }
                        else {
                            res.status(400).send();
                            
                        }
                })
                .catch(() => {})
            }
            else {
                 res.status(403).send();
            }
          
    }

    deleteStudent(req,res,next) {
        User.deleteOne({mssv:req.params.mssv})
             .then((user) => {
              
                  res.status(200).send();
             })
    }

   async updateStudent(req,res,next) {

        User.countDocuments({email:req.body.email})
        .then((count) => {
      
             if(count < 1 ) {
                User.updateOne({mssv:req.body.mssv}, req.body,{new: true})
                .then( (user) => {
                    res.status(200).send();
                    console.log(user);
                }
                )
                .catch(next)
             }
             else {
                res.status(400).send();
                 
             }
        })
    }

    getStudentByLopHP(req,res,next) { 

        User.find({'LopDaDK.MaBM':req.params.MaBM})
        .then((users) => {
            if(users != null) {
                // console.log(users);
                res.status(200).send(JSON.stringify(users));
         }
         else{
             res.status(404).send();
         }
        } )
        .catch(next)
    }

    async deleteStudentFromLopHP(req,res,next) {
       

        const student = await User.findOne({'LopDaDK.MaBM':req.body.MaBM , mssv: req.body.mssv});
        var newLopHP = [];
        

        for( var i =0 ;i< student.LopDaDK.length ;i++) {
             if(student.LopDaDK[i].MaBM == req.body.MaBM) {
                   newLopHP = student.LopDaDK.filter((lop) => lop.MaBM !== req.body.MaBM);
                  
             }
        }

        User.findOneAndUpdate({'LopDaDK.MaBM':req.body.MaBM , mssv : req.body.mssv},{LopDaDK : newLopHP })
        
        .then((user) => {
            res.status(200).send();
        })
    }

    async addSVToLopHP(req,res,next) {

    const student = await User.findOne({ mssv: req.body.mssv});
    const Lophp = await LopHP.findOne({MaBM: req.body.MaBM});
    const Khoa= await BoMon.findOne({IdBM:Lophp.IdBM});
    var check ;
    var currentLopHPSize = await User.count({'LopDaDK.MaBM':req.body.MaBM});
    console.log("current size = " , currentLopHPSize);

    if(currentLopHPSize < Lophp.SoLuong) {
        if(student.LopDaDK.length == 0) {
            check = true;
       }
       for(var i = 0 ; i<student.LopDaDK.length;i++) {
           if(student.LopDaDK[i].MaBM == req.body.MaBM || student.LopDaDK[i].IdBM == Lophp.IdBM ) {
                res.status(400).send();
                check = false;
           }
       
           else {
                check = true; 
           }
          
      }
       if (student.khoa != Khoa.Khoa){
           res.status(405).send();
   
       }
       else if( check == true ) {
           var newLopHP = [ ... student.LopDaDK,{MaBM : req.body.MaBM, IdBM : Lophp.IdBM}];
   
           User.findOneAndUpdate({mssv : req.body.mssv},{LopDaDK : newLopHP })
           .then((user) => {
                   res.status(200).send();
           })
       }
       else if(student == null) {
               res.status(404).send();
               
       }

    }
    else {
         res.status(403).send();
    }

    
}

}

module.exports = new UserController;