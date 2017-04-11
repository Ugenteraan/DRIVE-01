var express 	= require('express'),
	app			= express(),
	mongoose	= require('mongoose'),
	http		= require('http').Server(app),
	client		= require('socket.io').listen(http),
	bodyParser	= require('body-parser');


var port = 8000;

http.listen(port, function(){
	console.log("Listening at port : " + port);
});

mongoose.connect('mongodb://localhost:27017/EV3');

app.use(bodyParser.json());
app.use('/node_modules', express.static(__dirname + "/node_modules"));
app.use('/app', express.static(__dirname + "/app"));


client.on('connection', function(socket){
	console.log('connected');

	socket.on("direction", function(data){
		

		sending(socket, data.test, data.time);
		console.log(data);
	});


	socket.on('foo', function(data){
		console.log(data);
	});

});


app.get('/', function(req,res){
	res.sendFile('index.html', {root:__dirname});
});

app.post('/api/movement', function(req,res){

	var data = req.body.movement;
	//console.log(data);

	res.json("Success");

});

var sending = function(socket, direction, time1){

	
	data = {
		test : direction,
		time: time1
	};
	
	client.emit('incoming', data);
}


