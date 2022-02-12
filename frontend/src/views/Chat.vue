<template>
  <el-container class="background">
    <el-header>
    </el-header>
    <el-main style="height: 550px; padding-left: 465px">
      <el-row>
        <div id="historyMsg">
          <el-scrollbar>
            <el-row id="msg" :key="Math.random()" v-for="msg in msgList" v-html="msg"></el-row>
          </el-scrollbar>
        </div>
      </el-row>
      <el-row id="inputMsg">
        <el-input rows="10" v-model="message" resize="none"
                  type="textarea" @keyup.enter.native="enterkeyDown(message, uid, $event)" autofocus="true"></el-input>
      </el-row>
    </el-main>

    <el-footer>
      <el-button id="backBtn" @click="goBack">关闭</el-button>
      <el-button id="sendBtn" type="primary" @click="handleMsg(message, uid)">发送</el-button>
    </el-footer>
  </el-container>
</template>

<script lang="ts" setup>
import {getCurrentInstance,reactive, ref} from 'vue'
import {createWebSocket} from "@/utils/websocket";
import {useRoute} from "vue-router";
import {sendMsg, showErrorMsg} from "@/utils/axios";

// 绑定的输入消息
let message = ref('')

//路由对象
const route = useRoute()

//上下文实例对象
const {ctx: _this}: any = getCurrentInstance()

//必须使用reactive，使其为响应式数据，否则视图不会随数据更新
const msgList = reactive([])

//获取上个路由保存的用户id
const uid = route.query.id

//获取 websocket 对象
const socket = createWebSocket('ws://localhost:8080/webSocketServer')

// 监听消息
socket.onmessage = (evt) => {
    msgList.push(evt.data)
    console.log(msgList)
  }

  // 处理发送按钮点击
  const handleMsg = (userMsg, userId) => {
    if(userMsg === '' || userMsg === null){
      showErrorMsg('不能发送空消息！')
    }else {
      sendMsg(userMsg, userId)
      message = ''
      // _this.$forceUpdate()
    }
  }

  // 处理关闭按钮点击
  const goBack = () => {
    window.close()
  }

  // 监听键盘的回车键
  const enterkeyDown = (userMsg, userId, evt) => {
    if(evt.keyCode == 13){
      if(!evt.metaKey){
        evt.preventDefault()
        handleMsg(userMsg, userId)
      }
    }
  }
</script>

<style scoped>

#historyMsg{
  width: 600px;
  height:300px;
  /*position: absolute;*/
  background-color: #F2F6FC;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  opacity: 0.9;
}

#inputMsg{
  width: 602px;
  height: 180px;
  position: relative;
  opacity: 0.9;
  /*top:50%;*/
  /*margin-left: -300px;*/
}

#backBtn{
  width: 80px;
}
#sendBtn{
  width: 80px;
  margin-right: -440px;
}

#msg{
  padding-left: 20px;
  padding-top: 10px;
  font-size: medium;
}

.background{
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-image: url("../assets/bg6.png");
}

</style>