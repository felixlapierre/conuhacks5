
var mongoose = require('mongoose');
var connectionString = 'mongodb://localhost:27017/tunein';
mongoose.connect(connectionString, {useNewUrlParser: true, useUnifiedTopology: true});
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));

function onError(error) {
    if(error)
        console.log("An error occurred: " + error);
}

var Schema = mongoose.Schema;

var PlaySchema = new Schema({
    id: Number,
    styleName: String,
    genreName: String,
    duration: Number,
    title: String,
    artistName: String,
    lat: Number,
    long: Number
});

var PlayModel = mongoose.model('Plays', PlaySchema);

var samplePlay = new PlayModel(
    {
        "id": 7782908,
        "lat": 45.495302,
        "long": -73.578964,
        "styleName": "Electro - Dance",
        "genreName": "Pop",
        "duration": 217,
        "title": "Technologic",
        "artistName": "Daft Punk"
    }
)

PlayModel.find({"id": 7782908}, (err, athletes) => {
    onError(err);
    if(athletes.length == 0) {
        console.log("No plays exist: adding a sample play");
        samplePlay.save(onError);
    } else {
        console.log("Sample play already exists");
    }
})

module.exports.save = function(info, lat, lon) {
    //TEMP: Remove other plays with the same ID so we don't fill up the database
    PlayModel.deleteMany({"id": info.id}, (err) => {
        if(err) console.log(err);
    })

    var newPlay = new PlayModel({
        "id": info.id,
        "lat": lat,
        "long": lon,
        "styleName": info.styleName,
        "genreName": info.genreName,
        "duration": info.duration,
        "title": info.title,
        "artistName": info.artistName
    });

    newPlay.save((err) => {
        if(err) console.log(err);
    });
}