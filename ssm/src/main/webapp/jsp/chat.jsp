<%--
  Created by IntelliJ IDEA.
  User: 李非凡
  Date: 2021/12/24
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <!--sockJS cdn-->
  <script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
  <!--stomp cdn-->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

  <script src="https://cdn.bootcss.com/vue/2.5.2/vue.min.js"></script>
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

  <title>聊天</title>
  <style type="text/css">
    #chatView{
      width: 600px;
      height: 790px;
      padding: 5px;
      margin: 0 auto;
      background-color: #ddd;
    }

    .Btn{
      height: 30px;
      width: 50px;
    }

    #sendBtn{
      margin-right: 50px;
    }

    #messageInput {
      height: 170px;
      width: 600px;
      max-width: 600px;
      max-height: 170px;
      padding-inline: 2px;
    }

    #historyMsg {
      height: 370px;
      background-color: #fff;
      overflow: auto;
      padding: 2px;
    }

  </style>
</head>
<body>
  <div id="chatView">
    <input hidden value="${uid}" id="getUid"/>

    <h1>聊天</h1>
    <button class="Btn" @click="backward">返回</button>
    <div id="historyMsg">
      <li v-for="msg in msgList">
        {{msg}}
      </li>
    </div>
    <div class="controls">
      <textarea id="messageInput" placeholder="输入消息" v-model="message" @keyup="keyDown"></textarea>
      <br/><br/>
      <button class="Btn" id="sendBtn" @click="sendMsg">发送</button>
    </div>

  </div>

  <script type="text/javascript">


    var myViewModel = new Vue({
      el: '#chatView',
      data: {
        uid: null,
        message: null,
        path: "ws://localhost:8080/webSocketServer",
        socket: "",
        msgList: [],
      },
      methods: {
        sendMsg: function () {
          debugger
          if(this.message === null || this.message === "") {
            alert("不能发送空消息！");
            return;
          }
          console.log(this.message);
          params = new URLSearchParams();
          // params.append('uid', this.uid);
          params.append('content', this.message);
          params.append('uid', this.uid);
          this.message = null;
          axios.post('/sendMsg', params)
                  .then(response => {
                    if(response.data == true)
                      console.log('发送消息成功：' + this.message);
                  })
        },

        keyDown: function (e){
          if(!e.shiftKey && e.keyCode === 13){
            e.preventDefault();
            this.sendMsg();
          }
        },

        backward: function () {
          history.back();
        },

        initWebSocket: function () {
          if (typeof (WebSocket) === "undefined") {
            alert("您的浏览器不支持socket")
          } else {
            this.socket = new WebSocket(this.path);
            this.socket.onopen = this.onOpen;
            this.socket.onmessage = this.onMessage;
          }
        },

        onOpen: function (){
          console.log("websocket 连接成功！");
        },
        onMessage: function (evt){
          // debugger
          this.msgList.push(evt.data);
          // alert(evt.data);
        },
      },
      mounted() {
        this.uid = document.getElementById('getUid').value;
        this.initWebSocket();
      }
    });



  </script>



</body>
</html>
