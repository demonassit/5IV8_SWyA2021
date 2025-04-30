const jwt = require('jsonwebtoken');


function verifyToken(req, res, next) {
  const token = req.headers['authorization'];
  if (!token) {
    console.warn("Token no proporcionado");
    return res.status(403).send('Token requerido');
  }

  jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
    if (err) {
      console.warn("Token inválido:", err);
      return res.status(401).send('Token inválido');
    }
    req.user = decoded;
    next();
  });
}

module.exports = verifyToken;
