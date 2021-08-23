const express = require('express');
const app = express();

app.use(express.static(__dirname + '/dist/cadastro-veiculos-front'));

app.get('/*', function (req, res){
  res.sendFile(__dirname + '/dist/cadastro-veiculos-front/index.html');
});

app.listen(process.env.PORT || 4200);
