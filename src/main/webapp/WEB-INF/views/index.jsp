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
    <link rel="stylesheet" href="/css/modal.css"/>
    <script src="/js/vue.js"></script>
</head>
<body>
    <div id="app">
        <button id="show-modal" @click="showModal = true">Show Modal</button>
        <!-- use the modal component, pass in the prop -->
        <modal v-if="showModal" @close="showModal = false" header-text="TestHead" body-text="bodyTest" footer-text="footer">
        </modal>

    </div>
</body>
<script src="/js/index.js"></script>
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
</html>
