var express = require('express');
var plays = require('./plays');
var octave = require('./octave');
var app = express();

app.get('/status', function(req, res) {
    res.json({message: "The server is running!"})
})

app.get('/search', function(req, res) {
    const query = req.query.query || "technologic";
    const size = req.query.size || 30;
    octave.search(query, size).then((result) => {
        res.json(result);
    }).catch((err) => {
        console.log(err);
        res.status(500).send();
    })
})

app.get('/play', function(req, res) {
    const id = req.query.id || 7782908;
    const lat = req.query.lat || 45.495302;
    const lon = req.query.lon || -73.578964;
    
    octave.play(id).then((result) => {
        if(result.playUrl) {
            res.json({playUrl: result.playUrl});
            plays.save(result, lat, lon);
        } else {
            res.status(404).send();
        }
    }).catch((err) => {
        console.log(err);
        res.status(500).send();
    })
})

app.get('/nearby', function(req, res) {
    const lat = req.query.lat;
    const lon = req.query.lon;
    if(lat === undefined || lon === undefined) {
        res.status(400).send("lat and lon are required in query string");
        return;
    }

    plays.getNearby(lat, lon).then((result) => {
        res.json(result);
    }).catch((err) => {
        console.log(err);
        res.status(500).send();
    })
})
var port = 3000;
app.listen(port);
console.log("Started server on port " + port);