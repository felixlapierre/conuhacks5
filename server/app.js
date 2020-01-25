var express = require('express');
var plays = require('./plays');
var octave = require('./octave');
var app = express();

app.get('/status', function(req, res) {
    res.json({message: "The server is running!"})
})

app.get('/search', function(req, res) {
    octave.search('technologic', 30).then((result) => {
        res.json(result);
    }).catch((err) => {
        console.log(err);
        res.status(500).send();
    })
})
var port = 3000;
app.listen(port);
console.log("Started server on port " + port);