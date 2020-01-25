const http = require('http');
const https = require('https');

function getJson(options, onResult) {
    console.log('rest::getJSON');
    let output = '';
    const port = https;
    const req = port.request(options, (res) => {
        console.log(`${options.host} : ${res.statusCode}`);

        res.setEncoding('utf8');
        res.on('data', (chunk) => {
            output += chunk;
        })

        res.on('end', () => {
            let obj = JSON.parse(output);

            onResult(res.statusCode, obj);
        })
    })

    req.on('error', (err) => {
        console.log(err);
    })
}

module.exports = {};
module.exports.search = function(query, results) {
    return new Promise((resolve, reject) => {
        const path = `/vi/songs?query=${query}&results=${results}`;
        const options = {
            host: 'conuhacks-2020.tsp.cld.touchtunes.com',
            path: path,
            method: 'GET',
            headers: {
                'Authorization': 'x2ahq2gd9xo14e83x4339i95jlwe7boi'
            }
        }
    
        getJson(options, (statusCode, result) => {
            console.log(`onResult: (${statusCode})\n\n${JSON.stringify(result)}`);
    
            resolve(result);
        })
    })
}