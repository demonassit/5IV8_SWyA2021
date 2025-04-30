const mysql = require('mysql2');
require('dotenv').config();
const express = require('express');
const cors = require('cors');
//const db = require('./db'); // ahora sí ya se leen las variables correctamente

// Crear conexión a la base de datos usando variables de entorno
const db = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  
});

db.connect(err => {
  if (err) throw err;
  console.log("Conectado a la base de datos MySQL");
  console.log('DB_HOST:', process.env.DB_HOST);
  console.log('DB_USER:', process.env.DB_USER);
  console.log('DB_PASSWORD:', process.env.DB_PASSWORD);
  console.log('DB_NAME:', process.env.DB_NAME);
});

module.exports = db;
