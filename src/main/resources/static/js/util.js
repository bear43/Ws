let stompClient = null;

let subscribeHandlers = [];

function getRawHandlers() {
    return [
            (data) => {
                defaultRawDataHandler(data);
        }
    ];
}

let rawDataHandlers = getRawHandlers();

function defaultRawDataHandler(data) {
    let jsonBody = JSON.parse(data.body);
    subscribeHandlers.forEach(handler => handler(jsonBody));
}

function connect(endPoint) {
    let socket = new SockJS('/gs-websocket');
    //addRawDataHandler(defaultRawDataHandler);
    stompClient = Stomp.over(socket);
    return new Promise((onsuccess, onreject) => {
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            onsuccess();
            stompClient.subscribe(endPoint, function (message) {
                rawDataHandlers.forEach(handler => handler(message));
            });
        });
    });
}

function addSubscribeEventHandler(handler) {
    subscribeHandlers.push(handler);
}

function clearSubscribeHandlers() {
    subscribeHandlers = [];
}

function addRawDataHandler(handler) {
    rawDataHandlers.push(handler);
}

function clearRawDataHandler() {
    rawDataHandlers = [];
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