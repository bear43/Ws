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
        commonHandler: (json) => {
            console.log(json);
        }
    },
    created() {
        addSubscribeEventHandler(this.commonHandler);
        connect("/sender/update");
    }
});