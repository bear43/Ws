let stompClient = null;

let socketClient = null;

let debug = false;

let reconnectDelay = 2000;

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

function clearList(listInstance) {
    listInstance.splice(0, listInstance.length);
}

function updateListElement(listInstance, oldElementListInstance, newElementInstance) {
    if(newElementInstance.removed) {
        let index = listInstance.indexOf(oldElementListInstance);
        if(index !== -1)
            listInstance.splice(index, 1);
    } else {
        Object.assign(oldElementListInstance, newElementInstance);
    }
}

function createNewListElement(listInstance, newElement) {
    listInstance.push(newElement);
}

function doesElementExistsInListById(listInstance, id) {
    return listInstance.find(x => x.id === id);
}

function removeListElement(listInstance, element) {
    let obj = doesElementExistsInListById(listInstance, element.id);
    if(obj) {
        let index = listInstance.indexOf(obj);
        listInstance.splice(index, 1);
    }
}

function handleNewListElement(listInstance, newElement) {
    if (newElement.id === null) {//how could it be?
        if(debug === true) console.log("Error. id is null");
    } else {
        let msg = doesElementExistsInListById(listInstance, newElement);
        if (msg) {
            updateListElement(listInstance, msg, newElement);
        } else {
            createNewListElement(listInstance, newElement);
        }
    }
}

function connect() {
    socketClient = new SockJS('/gs-websocket');
    stompClient = Stomp.over(socketClient);
    socketClient.reconnect_delay = reconnectDelay;
    return new Promise((onsuccess, onreject) => {
        stompClient.connect({}, function (frame) {
            if(debug === true) console.log('Connected: ' + frame);
            socketClient.onerror = function(error) {
                if(debug === true) alert(error);
            };
            let origOnCloseHandlerFunction = socketClient.onclose;
            socketClient.onclose = function() {
                if(debug === true) console.log("Connection has been closed");
                origOnCloseHandlerFunction();
            };
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