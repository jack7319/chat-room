'use strict';
var serverIP = "";
var serverPort = "";
// var userId = userId1;
// var username = username1;
// var groupUserId = groupUserId1;
$(function () {
    startWebSocket();
});
var socket;
function startWebSocket() {
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = null;
        try{
            // socket = new WebSocket("ws://10.1.1.7:6006/websocket");
            socket = new WebSocket("ws://10.1.3.18:9688/websocket");
            console.info("web socket connect success");
        }catch (e) {
            console.info("web socket 连接异常!",e);
            //断开后接着连接
            funcReconnect();
        }
        socket.onmessage = function (event) {
//            funcProcessMsg(event.data);
            console.log(event.data);
            $('#msgDiv').append(event.data + '<br/>');
            heartCheck.reset().start();
        };

        socket.onopen = function (event) {
            var obj ={};
            obj.userId = userId;
            obj.username = username;
            obj.groupId = groupId;
            obj.type = 0;
            funcSocketSend(obj);
            heartCheck.reset().start();
        };

        socket.onclose = function (event) {
            console.info("web socket 已断开!",event);
            //断开后接着连接
            funcReconnect();
        };

        /*socket.onerror  = function (event) {
            console.info("web socket 发生异常已断开!",event);
            //断开后接着连接
            funcReconnect();
        };*/
    } else {
        console.info("您的浏览器不支持WebSocket协议！");
    }
}

/**
 * 重连
 */
function funcReconnect(){
    setTimeout(startWebSocket,6000);
}

//发送信息
function funcSocketSend(message){
    if(!window.WebSocket){return;}
    if(socket.readyState == WebSocket.OPEN){
        console.info("发送",JSON.stringify(message));
        socket.send(JSON.stringify(message));
    }else{
        console.info("WebSocket 连接没有建立成功！");
    }

}


//心跳
var heartCheck = {
    timeout: 60000,//60秒
    timeoutObj: null,
    reset: function(){
        clearTimeout(this.timeoutObj);
        return this;
    },
    start: function(){
        this.timeoutObj = setInterval(function(){
            //这里发送一个心跳，后端收到后，返回一个心跳消息，
            //onmessage拿到返回的心跳就说明连接正常
            var obj ={};
            obj.web = "heartbeat";
            obj.type = 9;
            funcSocketSend(obj);
        }, this.timeout)
    }
}

