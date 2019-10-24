Vue.component("channel-item", {
    props: ["id", "title", "author"],
    methods: {
        onClick: function() {
            alert("clicked");
        }
    },
    template: "#channel-item-template"
});

Vue.component("channel-container", {
    props: ["channelList"],
    template: "#channel-container-template"
});

Vue.component('message-container', {
    props: ["messageList"],
    methods: {

    },
    template: '#msg-cont'
});

Vue.component('message-item', {
    props: ["id", "text", "creationDate", "author"],
    methods: {
        onDeleteMessage() {
            let msg = this.$root.createMessageInstance(this.id, this.text, this.creationDate, true);
            send("/app/delete", msg);
        }
    },
    template: '#message-item-template'
});

Vue.component('add-message-form', {
    data: () => {
        return {
            text: ""
        }
    },
    template: '#add-message-form',
    methods: {
    }
});

Vue.component('modal', {
    props: ["headerText", "bodyText", "footerText"],
    template: '#modal-template',
    methods: {
    }
});

/*
    Body or raw handler have next structure:
    {
        handler: function(data, args) {...}
        args: []//Handler args
    }
*/

function createHandler(func, args) {
    return {
        handler: func,
        args: args
    }
}

const handlerType = {
    BODY: {
        createHandlerAndPush: function(endpointInstance, func, args) {
            endpointInstance.bodyHandlers.push(createHandler(func, args));
        }
    },
    RAW: {
        createHandlerAndPush: function(endpointInstance, func, args) {
            endpointInstance.rawHandlers.push(createHandler(func, args));
        }
    }
};

let messageEndpoint = {
    endPoint: "/topic/message",
    bodyHandlers: [],
    rawHandlers: []
};

let errorEndpoint = {
    endPoint: "/user/queue/error",
    bodyHandlers: [],
    rawHandlers: []
};

let channelEndpoint = {
    endPoint: "/user/topic/channel",
    bodyHandlers: [],
    rawHandlers: []
};

// start app
new Vue({
    el: '#app',
    data: {
        showModal: false,
        messageList: []
    },
    methods: {
        createMessageInstance: (id, text, creationDate, removed) => {
            return {
                id: id,
                text: text,
                creationDate: creationDate,
                removed: removed
            }
        },
        onSubmitMessage: function(text) {
            send("/app/create", this.createMessageInstance(null, text, null, false));
        },
        doesMessageExists: function(message) {
            return this.messageList.find(x => x.id === message.id);
        },
        updateMessage: function(messageInstance, newInstance) {
            if(newInstance.removed) {
                let index = this.messageList.indexOf(messageInstance);
                if(index !== -1)
                    this.messageList.splice(index, 1);
            } else {
                Object.assign(messageInstance, newInstance);
            }
        },
        createMessage: function(message) {
            this.messageList.push(message);
        },
        removeMessage: function(message) {
            let obj = this.doesMessageExists(message);
            if(obj) {
                this.messageList.remove(obj);
            }
        },
        messageHandler: function(message) {
            if (message.id === null) {//how could it be?
                alert("Error. id is null");
            } else {
                let msg = this.doesMessageExists(message);
                if (msg) {
                    this.updateMessage(msg, message);
                } else {
                    this.createMessage(message);
                }
            }
        },
        channelHandler: function(json) {

        },
        commonHandler: function (json, subHandler) {
            if(json.forEach) {
                json.forEach(x => {
                    subHandler(x);
                });
            } else {
                subHandler(json);
            }
        },
        getMessages: function() {
            send("/app/read/messages", {});
        },
        getChannels: function() {
            send("/app/read/channels", {});
        },
        exceptionHandler: function(json) {
            alert(json.message);
        }
    },
    created() {
        //messageEndpoint.bodyHandlers.push(this.commonHandler);
        //errorEndpoint.bodyHandlers.push(createHandler(this.exceptionHandler));
        //channelEndpoint.bodyHandler.push(createHandler(this.commonHandler, this.channelHandler));
        handlerType.BODY.createHandlerAndPush(messageEndpoint, this.commonHandler, this.messageHandler);
        handlerType.BODY.createHandlerAndPush(errorEndpoint, this.exceptionHandler);
        handlerType.BODY.createHandlerAndPush(channelEndpoint, this.commonHandler, this.channelHandler);
        connect().then(() => {
            subscribe(errorEndpoint);
            subscribe(messageEndpoint);
            //subscribe(channelEndpoint);
            //this.getChannels();
            this.getMessages();
        });
    }
});