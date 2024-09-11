const express = require('express');
const router = express.Router();

const BoMonController = require('../app/controllers/BoMonController');

router.get('/get/bm/:khoa',BoMonController.getBm);
router.post('/create/bomon',BoMonController.createBoBom);

module.exports = router ;