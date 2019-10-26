Vue.component("add-channel-form", {
    data: () => {
        return {
            title: ""
        }
    },
    template: "#add-channel-form-template"
});

Vue.component("channel-item", {
    props: ["id", "title", "author"],
    methods: {
        onDeleteChannel: function() {
            let msg = this.$root.createChannelInstance(this.id, this.title, null, false);
            send("/app/delete/channel", msg);
        }
    },
    template: "#channel-item-template"
});

Vue.component("channel-container", {
    props: ["channelList"],
    methods: {
        onChannelClick: function(channelId) {
            this.$emit("on-channel-click", channelId);
        }
    },
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
        onDeleteMessage: function() {
            let msg = this.$root.createMessageInstance(this.id, this.text, this.creationDate, true, this.$root.currentChannelInstance);
            send("/app/delete/message", msg);
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

let errorEndpoint = createEndpointInstance("/user/queue/error");

let channelEndpoint = createEndpointInstance("/topic/channels");

let userChannelEndpoint = createEndpointInstance("/user/topic/channels");


const applicationModes = {
    CHANNEL_LIST_VIEW_MODE: {
        name: "CHANNEL_LIST_VIEW_MODE",
        switchMode: function(newMode, callback) {
            applicationModes.switchMode(this, newMode, callback);
        }
    },
    CHANNEL_MESSAGES_VIEW_MODE: {
        name: "CHANNEL_MESSAGES_VIEW_MODE",
        switchMode: function(newMode, callback) {
            applicationModes.switchMode(this, newMode, callback);
        }
    },
    switchMode: function(self, newMode, callback) {
        if(callback) callback();
        Object.assign(self.currentViewMode, newMode);
    }
};

// start app
new Vue({
    el: '#app',
    data: {
        showModal: false,
        currentViewMode: applicationModes.CHANNEL_LIST_VIEW_MODE,
        messageList: [],
        channelList: [],
        currentChannelSubscribe: null,
        currentUserChannelSubscribe: null,
        currentChannelInstance: null
    },
    methods: {
        createMessageInstance: (id, text, creationDate, removed, channel) => {
            return {
                id: id,
                text: text,
                creationDate: creationDate,
                removed: removed,
                channel: channel
            }
        },
        createChannelInstance: (id, title, creationDate, removed) => {
            return {
                id: id,
                title: title,
                creationDate: creationDate,
                removed: removed
            }
        },
        onSubmitMessage: function(text) {
            send("/app/create/message",
                this.createMessageInstance(null, text, null, false, this.currentChannelInstance));
        },
        onSubmitChannel: function(text) {
            send("/app/create/channel", this.createChannelInstance(null, text, null, false));
        },
        onChannelClick: function(channelId) {
            this.currentChannelInstance = this.createChannelInstance(channelId, null, null, false);
            this.currentChannelSubscribe = subscribeById("/topic/channel/", channelId, this.commonHandler, this.messageHandler);
            this.currentUserChannelSubscribe = subscribeById("/user/topic/channel/", channelId, this.commonHandler, this.messageHandler);
            this.getMessages(channelId);
            this.currentViewMode = applicationModes.CHANNEL_MESSAGES_VIEW_MODE;
        },
        /* Message operations */
        createMessage: function(message) {
            createNewListElement(this.messageList, message);
        },
        removeMessage: function(message) {
            removeListElement(this.messageList, message);
        },
        updateMessage: function(messageInstance, newInstance) {
            updateListElement(this.messageList, messageInstance, newInstance);
        },
        doesMessageExists: function(message) {
            return doesElementExistsInListById(this.messageList, message.id);
        },
        /* Channel operations */
        createChannel: function(channel) {
            createNewListElement(this.channelList, channel);
        },
        removeChannel: function(channel) {
            removeListElement(this.channelList, channel);
        },
        updateChannel: function(channelInstance, newInstance) {
            updateListElement(this.channelList, channelInstance, newInstance);
        },
        doesChannelExists: function(channel) {
            return doesElementExistsInListById(this.channelList, channel.id);
        },
        messageHandler: function(message) {
            handleNewListElement(this.messageList, message);
        },
        channelHandler: function(channel) {
            handleNewListElement(this.channelList, channel);
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
        clearMessageList: function() {
            clearList(this.messageList);
        },
        clearChannelList: function() {
            clearList(this.channelList);
        },
        getMessages: function(channelId) {
            this.clearMessageList();
            send("/app/read/messages", {id: channelId});
        },
        getChannels: function() {
            this.clearChannelList();
            send("/app/read/channels", {});
        },
        exceptionHandler: function(json) {
            alert(json.message);
        },
        onConnectHandler: function() {
            subscribe(errorEndpoint);
            subscribe(channelEndpoint);
            subscribe(userChannelEndpoint);
            this.getChannels();
        }
    },
    created() {
        handlerType.BODY.createHandlerAndPush(errorEndpoint, this.exceptionHandler);
        handlerType.BODY.createHandlerAndPush(channelEndpoint, this.commonHandler, this.channelHandler);
        handlerType.BODY.createHandlerAndPush(userChannelEndpoint, this.commonHandler, this.channelHandler);
        connect().then(() => {
            this.onConnectHandler();
        });
    }
});