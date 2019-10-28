Vue.component("message-item", {
    props: ["message"],
    template: "#message-item-template"
});

Vue.component("add-channel-form", {
    data: () => {
        return {
            title: ""
        }
    },
    methods: {
       onClickSubmit: function() {
           if(isEmptyString(this.title)) {
               alert("Cannot be empty");
           } else {
               this.$emit('on-submit-channel', this.title);
               this.title = "";
           }
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

Vue.component('text-message-item', {
    props: ["id", "text", "creationDate", "author"],
    methods: {
        onDeleteMessage: function() {
            let msg = this.$root.createMessageInstance(this.id, null, null, true, this.$root.currentChannelInstance);
            send("/app/delete/message", msg);
        }
    },
    template: '#text-message-item-template'
});

Vue.component('voice-message-item', {
    props: ["id", "data", "creationDate", "author"],
    methods: {
        onDeleteMessage: function() {
            let msg = this.$root.createMessageInstance(this.id, null, null, true, this.$root.currentChannelInstance);
            send("/app/delete/message", msg);
        },
        onClickPlayButton: (data) => {
            createSoundWithBuffer(new Uint8Array(data).buffer);
        }
    },
    mounted() {
        let dat = this.data;
        this.$refs.audio.addEventListener("play", event => {
            this.onClickPlayButton(dat);
        });
    },
    template: '#voice-message-item-template'
});

Vue.component('add-voice-message-form', {
    data: () => {
        return {
            data: null,
            mediaRecorder: null,
            stream: null
        }
    },
    template: '#add-voice-message-form-template',
    methods: {
        onClickRecord: function() {
            navigator.mediaDevices.getUserMedia({audio: true, video:false}).then((stream) => {
                this.stream = stream;
                this.mediaRecorder = new MediaRecorder(stream);
                this.mediaRecorder.start();
                this.data = [];
                this.mediaRecorder.addEventListener("dataavailable", event => {
                    this.data.push(event.data);
                });
                console.log(stream);
            }).catch(error => alert("No matching devices are available. Error: " + error));
        },
        onClickSubmit: function() {
            if(this.mediaRecorder) {
                this.mediaRecorder.addEventListener("stop", event => {
                    this.stream.getTracks().forEach(x => { x.stop(); });
                    let blob = new Blob(this.data);
                    blob.arrayBuffer().then((buffer) => {
                        let ba = new Uint8Array(buffer);
                        let array = [];
                        for(let index in ba) {
                            array.push(ba[index]);
                        }
                        this.$emit("on-submit-voice", array);
                    });
                });
                this.mediaRecorder.stop();
                this.mediaRecorder = null;
                //this.$emit("on-submit-voice", this.data);
                /*if(this.stream) {
                    this.stream.active = false;
                    this.stream = null;
                }*/
            }
        }
    }
});

Vue.component('add-message-form', {
    data: () => {
        return {
            text: ""
        }
    },
    template: '#add-message-form',
    methods: {
        onClickSubmit: function() {
            if(isEmptyString(this.text)) {
                alert("Cannot be empty");
            } else {
                this.$emit('on-submit-message', this.text);
                this.text = "";
            }
        }
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

const messageType = {
    TEXT: {
        name: "TEXT",
        handle: function(listInstance, messageObject) {
            let byteArray = messageObject.data;
            messageObject.text = new TextDecoder().decode(new Uint8Array(byteArray));
            handleNewListElement(listInstance, messageObject);
        }
    },
    VOICE: {
        name: "VOICE",
        handle: function(listInstance, messageObject) {
            handleNewListElement(listInstance, messageObject);
        }
    },
    resolveType: function(messageObject) {
        switch(messageObject.messageType) {
            case this.TEXT.name:
                return messageType.TEXT;
            case this.VOICE.name:
                return messageType.VOICE;
            default:
                return messageType.TEXT;
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
        createMessageInstance: (id, data, creationDate, removed, channel, messageType) => {
            return {
                id: id,
                data: data,
                creationDate: creationDate,
                removed: removed,
                channel: channel,
                messageType: messageType
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
        onSubmitVoice: function(data) {
            send("/app/create/message",
                this.createMessageInstance(null, data, null, false, this.currentChannelInstance, messageType.VOICE.name));
        },
        onSubmitMessage: function(text) {
            let utf8Array = new TextEncoder().encode(text);
            let byteArray = [];
            for(let index in utf8Array) {
                byteArray.push(utf8Array[index]);
            }
            send("/app/create/message",
                this.createMessageInstance(null, byteArray, null, false, this.currentChannelInstance, messageType.TEXT.name));
        },
        onSubmitChannel: function(text) {
            send("/app/create/channel", this.createChannelInstance(null, text, null, false));
        },
        unsubscribeFromChannel: function() {
            this.currentChannelSubscribe.unsubscribe();
            this.currentUserChannelSubscribe.unsubscribe();
            this.currentChannelSubscribe = null;
            this.currentUserChannelSubscribe = null;
            this.currentChannelInstance = null;
            clearList(this.messageList);
        },
        onClickBackButton: function() {
            this.unsubscribeFromChannel();
            this.currentViewMode = applicationModes.CHANNEL_LIST_VIEW_MODE;
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
            messageType.resolveType(message).handle(this.messageList, message);
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