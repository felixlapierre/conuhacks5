var express = require('express');
var plays = require('./plays');
var octave = require('./octave');
var app = express();

app.get('/status', function(req, res) {
    res.json({message: "The server is running!"})
})

app.get('/search', function(req, res) {
    const query = req.query || "technologic";
    const size = req.size || 30;
    octave.search(query, size).then((result) => {
        res.json(result);
    }).catch((err) => {
        console.log(err);
        res.status(500).send();
    })
})

app.get('/play', function(req, res) {
    const id = req.id || 7782908;
    const lat = req.lat || 45.495302;
    const lon = req.lon || -73.578964;
    
    octave.play(id).then((result) => {
        if(result.playUrl) {
            res.json({playUrl: result.playUrl});
        } else {
            res.status(404).send();
        }

        plays.save(result, lat, lon);
    }).catch((err) => {
        console.log(err);
        res.status(500).send();
    })
})
var port = 3000;
app.listen(port);
console.log("Started server on port " + port);