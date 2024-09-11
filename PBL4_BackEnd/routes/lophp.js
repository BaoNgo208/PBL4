const express = require('express');
const router = express.Router();

const LopHPController = require('../app/controllers/LopHPController');

router.get('/get/lophp/:IdBM',LopHPController.getLopHp);
router.post('/create/lopbm',LopHPController.addLopHP) ;
router.delete('/delete/lophp/:MaBM',LopHPController.deleteLopHP);
router.put('/update/lophp' , LopHPController.updateLopHP);
module.exports = router ;
