Vue.component('message-container', {
    props: ["messageList"],
    methods: {

    },
    template: '#msg-cont'
});

Vue.component('message-item', {
    props: ["text", "creationDate", "author"],
    methods: {
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
            send("/updater/create", this.createMessageInstance(null, text, null, false));
        },
        doesMessageExists: function(message) {
            return this.messageList.find(x => x.id === message.id);
        },
        updateMessage: function(messageInstance, newInstance) {
            if(newInstance.removed) {
                this.messageList.remove(messageInstance);
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
        commonHandler: function (json) {
            if(json.forEach) {
                json.forEach(x => {
                    this.messageHandler(x);
                });
            } else {
                this.messageHandler(json);
            }
        },
        getMessages: function() {
            send("/updater/read", {});
        }
    },
    created() {
        addSubscribeEventHandler(this.commonHandler);
        connect("/sender/update").then(() => {
            this.getMessages();
        });
    }
});