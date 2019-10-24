let stompClient = null;

let debug = false;

function defaultJSONHandler(point, data) {
    point.bodyHandlers.forEach(handlerInstance => {
        handlerInstance.handler(JSON.parse(data.body), handlerInstance.args);
    });
}

function defaultHandler(point, data) {
    point.rawHandlers.forEach(handlerInstance => handlerInstance.handler(data, handlerInstance.args));
}

function subscribe(point) {
    stompClient.subscribe(point.endPoint, function (message) {
        defaultHandler(point, message);
        defaultJSONHandler(point, message);
    });
}

function connect() {
    let socket = new SockJS('/gs-websocket');
    //addRawDataHandler(defaultRawDataHandler);
    stompClient = Stomp.over(socket);
    return new Promise((onsuccess, onreject) => {
        stompClient.connect({}, function (frame) {
            if(debug === true) console.log('Connected: ' + frame);
            onsuccess();
        });
    });
}

function send(endPoint, data) {
    stompClient.send(endPoint, {}, JSON.stringify(data));
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    if(debug === true) console.log("Disconnected");
}