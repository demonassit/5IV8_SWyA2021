//npm init -y
//npm install express mysql2 jsonwebtoken bcryptjs dotenv body-parser



const express = require('express');
const cors = require('cors');
const authRoutes = require('./routes/auth');

require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

app.use('/api/auth', authRoutes);

app.listen(3000, () => {
  console.log('Servidor en http://localhost:3000');
});
