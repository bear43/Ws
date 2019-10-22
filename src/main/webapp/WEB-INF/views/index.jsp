<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kotikov
  Date: 22.10.2019
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MainPage</title>
    <link rel="stylesheet" href="<c:url value='/static/css/modal.css'/>"/>
    <script src="<c:url value='/static/js/vue.js'/>"></script>
    <script src="<c:url value='/static/js/sockjs.js'/>"></script>
    <script src="<c:url value='/static/js/stomp.js'/>"></script>
    <script src="<c:url value='/static/js/util.js'/>"></script>
</head>
<body>
    <div id="app">
        <button id="show-modal" @click="showModal = true">Show Modal</button>
        <modal
                v-if="showModal"
                @close="showModal = false"
                header-text="Add message"
                body-text="bodyTest"
                footer-text="">
            <add-message-form slot="body" @on-submit-message="onSubmitMessage"></add-message-form>
        </modal>

    </div>
</body>
<script src="<c:url value='/static/js/index.js'/>"></script>
<script type="text/x-template" id="modal-template">
    <transition name="modal">
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">

                    <div class="modal-header">
                        <slot name="header">
                            <h3>{{headerText}}</h3>
                        </slot>
                    </div>

                    <div class="modal-body">
                        <slot name="body">
                            {{bodyText}}
                        </slot>
                    </div>

                    <div class="modal-footer">
                        <slot name="footer">
                            {{footerText}}
                            <button class="modal-default-button" @click="$emit('close')">
                                OK
                            </button>
                        </slot>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</script>
<script type="text/x-template" id="add-message-form">
    <div>
        <input type="text" v-model="text"/>
        <button @click="$emit('on-submit-message', text)">Отправить</button>
    </div>
</script>
</html>
