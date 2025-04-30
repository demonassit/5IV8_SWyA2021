const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const db = require('../db');

// Ruta de registro
router.post('/register', async (req, res) => {
  const { email, password } = req.body;
  try {
    const hashed = await bcrypt.hash(password, 10);
    db.query('INSERT INTO users (email, password) VALUES (?, ?)', [email, hashed], (err, result) => {
      if (err) {
        console.error("Error al registrar usuario:", err);
        return res.status(500).send('Error al registrar');
      }
      console.debug("Usuario registrado con ID:", result.insertId);
      res.status(200).send('Usuario registrado');
    });
  } catch (error) {
    console.error("Error en el servidor (register):", error);
    res.status(500).send('Error interno del servidor');
  }
});

// Ruta de login
router.post('/login', (req, res) => {
  const { email, password } = req.body;
  db.query('SELECT * FROM users WHERE email = ?', [email], async (err, results) => {
    if (err) {
      console.error("Error en la consulta de login:", err);
      return res.status(500).send('Error en servidor');
    }
    if (results.length === 0) {
      console.warn("Usuario no encontrado con email:", email);
      return res.status(401).send('Credenciales inválidas');
    }

    const user = results[0];
    const valid = await bcrypt.compare(password, user.password);
    if (!valid) {
      console.warn("Contraseña incorrecta para usuario:", email);
      return res.status(401).send('Contraseña incorrecta');
    }

    const token = jwt.sign(
      { id: user.id, email: user.email },
      process.env.JWT_SECRET,
      { expiresIn: '1h' }
    );
    console.debug("Token generado para usuario:", user.email);
    res.json({ token });
  });
});

module.exports = router;
