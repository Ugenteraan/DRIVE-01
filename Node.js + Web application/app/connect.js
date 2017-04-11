try{
	var socket = io.connect();
}catch(e){
	console.log("Error connectig to server");
}

function sendinSocket(string, time){
	var data = {
		test : string,
		time : time
	};

	socket.emit('direction', data);
}

socket.on('testq', function(data){
	console.log(data);
});