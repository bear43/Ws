Vue.component('modal', {
    props: ["headerText", "bodyText", "footerText"],
    template: '#modal-template'
});

// start app
new Vue({
    el: '#app',
    data: {
        showModal: false
    }
});