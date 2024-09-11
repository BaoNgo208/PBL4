const express = require('express');
const router = express.Router();

const UserController = require('../app/controllers/UserController');

router.post('/login',UserController.login);
router.post('/signup',UserController.signup);
router.get('/get/lop/:khoa',UserController.getKhoa);
router.get('/get/khoa/:lop',UserController.getLop);
router.post('/create/lop',UserController.createLop);
router.post('/create/student',UserController.createStudent);
router.delete('/delete/student/:mssv',UserController.deleteStudent);
router.put('/delete/student/lophp',UserController.deleteStudentFromLopHP);
router.put('/update/student',UserController.updateStudent);
router.put('/add/lophp/student',UserController.addSVToLopHP);
router.get('/get/lophp/sinhviens/:MaBM',UserController.getStudentByLopHP)







module.exports = router ;