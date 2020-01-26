var unirest = require("unirest");

module.exports.search = function (query, size) {
    return new Promise((resolve, reject) => {
        var req = unirest("GET", "https://conuhacks-2020.tsp.cld.touchtunes.com/v1/songs");

        req.query({
            "query": query,
            "size": size
        });

        req.headers({
            "cache-control": "no-cache",
            "Connection": "keep-alive",
            "Accept-Encoding": "gzip, deflate",
            "Host": "conuhacks-2020.tsp.cld.touchtunes.com",
            "Postman-Token": "01b7ae42-3f45-4fdb-a884-b55e3265474d,6a3c2cca-69e8-4055-9ee9-043a76973fa3",
            "Cache-Control": "no-cache",
            "Accept": "*/*",
            "User-Agent": "PostmanRuntime/7.20.1",
            "Authorization": "x2ahq2gd9xo14e83x4339i95jlwe7boi"
        });


        req.end(function (res) {
            if (res.error) reject(res.error);
            else resolve(res.body.songs);
        });
    })
}

module.exports.play = function (id) {
    return new Promise((resolve, reject) => {
        var unirest = require("unirest");

        var req = unirest("GET", `https://conuhacks-2020.tsp.cld.touchtunes.com/v1/songs/${id}`);

        req.headers({
            "cache-control": "no-cache",
            "Connection": "keep-alive",
            "Accept-Encoding": "gzip, deflate",
            "Host": "conuhacks-2020.tsp.cld.touchtunes.com",
            "Postman-Token": "64fb2817-1486-495c-8c01-d3ee99c35b8f,f91100c7-e0b4-4200-9358-2374d678ee7d",
            "Cache-Control": "no-cache",
            "Accept": "*/*",
            "User-Agent": "PostmanRuntime/7.20.1",
            "Authorization": "x2ahq2gd9xo14e83x4339i95jlwe7boi"
        });


        req.end(function (res) {
            if (res.error) reject(res.error);
            else resolve([res.body.playUrl]);
        });
    })
}