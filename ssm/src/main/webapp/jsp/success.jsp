<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: 李非凡
  Date: 2021/12/24
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>聊天室</title>
    <style>
        [v-clock]{
            display: none;
        }
        #myView{
            width: 400px;
            height: 400px;
            margin: 100px auto;
        }
        .Btn{
            position: absolute;
            width: 80px;
            height: 25px;
            left:55%
        }

    </style>
    <script src="https://cdn.bootcss.com/vue/2.5.2/vue.min.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>

<body>
　　<div id="myView" v-clock>
        <input hidden type="text" value="${uid}" id="getUid"/>


       <h1>好友列表</h1>
        欢迎回来~ &nbsp&nbsp&nbsp ${username}
        <button class="Btn" id="outBtn" @click="loginout">退出账号</button>

        <br/><br/>

        用户id：${uid}
        <button class="Btn" @click="chatWithAllFriends">一键群聊</button>

        <br/><br/><br/><br/>

        <div id="addFriend">
            <input type="text" placeholder="好友ID" v-model="newId"/>
            <button class="Btn" id="addBtn" @click="addFriend(newId)">添加</button>
    　　</div>

        <br/>　
　　　　<!-- 配置为循环项 -->
　　　　<li v-for="(friend, index) in friends">
            {{friend}}
            &nbsp&nbsp&nbsp&nbsp
            <button class="Btn" id="delBtn" @click="delfriend(index, friend)">删除</button>
       </li>
　　　　
　　</div>
</body>

<script type="text/javascript">
    var myModel = {
        user: null,
        friends: [],
        newId: null,
        uid: null,
        username: null,
    };

    var myViewModel1 = new Vue({
        el:'#myView',
        data(){
            return myModel
        },
        methods:{
          delfriend: function (curIndex, curfid){
              params = new URLSearchParams();
              params.append('uid', this.user.id);
              params.append('fid', curfid);
              console.log(curfid);
              axios.post('/delFriendById', params)
                .then(response => {
                    if(response.data == false)
                        alert("删除失败！");
                    else{
                        alert("删除成功！");
                        this.friends.splice(curIndex, 1);
                    }
                })
          },
            chatWithAllFriends: function (){
                // window.location.href = '/chat?uid=' + this.user.id;
                window.location.href = '/toChatPage?uid=' + this.user.id;
            },
            addFriend: function (fid){
              params = new URLSearchParams();
              params.append('uid', this.user.id);
              params.append("fid", fid);
              axios.post('/addFriendById', params)
                .then(response =>{
                    const r = response.data;
                    if(r == 0)
                        alert("此用户不存在！");
                    else if(r == 1) {
                        alert("添加好友成功");
                        this.friends.push(fid);
                    }else if(r == 2)
                        alert("你们已经是好友，无需重复添加！");
                });
            },
            loginout: function(){
              window.location.href="/exit?uid=" + this.user.id;
            }
        },
        mounted(){
            this.uid = document.getElementById('getUid').value;
            let params = new URLSearchParams();
            params.append('uid', this.uid);
            axios.post('/initUser', params).then(response => {
                debugger
                this.user = response.data;
                this.username = this.user.username;
                this.friends = this.user.friendIds;
                console.log(this.user.id);
            });

        }
    });


</script>

</html>
