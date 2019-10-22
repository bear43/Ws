let stompClient = null;

let subscribeHandlers = [];

function connect(endPoint) {
    var socket = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(endPoint, function (message) {
            subscribeHandlers.forEach(handler => handler(JSON.parse(message)));
        });
    });
}

function addSubscribeEventHandler(handler) {
    subscribeHandlers.push(handler);
}

function clearSubscribeHandlers() {
    subscribeHandlers = [];
}

function send(endPoint, data) {
    stompClient.send(endPoint, {}, JSON.stringify(data));
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}