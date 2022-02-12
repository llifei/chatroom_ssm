<template>
  <div id="box">
  <div id="login">
    <span id="title">欢迎登录</span>
    <el-tabs id="tabs" v-model="pageName" @tab-click="handleClick" stretch="true">
      <el-tab-pane label="登录" name="signIn">
        <el-form class="el-form" ref="FormRef"
                 label-width="120px" :model="loginForm">
          <el-form-item label="账号" >
            <el-input v-model="loginForm.id" prop="id" class="el-input" autofocus="true" type="input" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" >
            <el-input v-model="loginForm.password" prop="password" class="el-input" type="password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="el-button" @click="handleLoginIn" type="primary" >登录</el-button>
            <!--        <el-button class="el-button" >注册</el-button>-->
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="注册" name="signUp">
        <el-form class="el-form" ref="FormRef"
                 label-width="120px" :model="logupForm">
          <el-form-item label="账号" >
            <el-input v-model="logupForm.id" prop="id" class="el-input" autofocus="true" type="input" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称" >
            <el-input v-model="logupForm.username" prop="username" class="el-input" autofocus="true" type="input" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" >
            <el-input v-model="logupForm.password" prop="password" class="el-input" type="password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认" >
            <el-input v-model="logupForm.checkpass" prop="checkpass" class="el-input" type="password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="el-button" type="primary" @click="handleLogUp">注册</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>

  </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import type { ElForm} from 'element-plus'
import {useRouter} from 'vue-router'
import {showErrorMsg, submitLoginForm, submitLogupForm} from "@/utils/axios"

const FormRef = ref<InstanceType<typeof ElForm>>()
// 获取 router
const router = useRouter();
// 初始化 登录/注册 导航标签的默认值
let pageName = ref('signIn')

// 验证输入值是否是空值
const validateIsNull = (value: any) => {
  if(value === ''){
    showErrorMsg('输入存在空值')
    return false
  }
  return true
}

// 检查输入的确认密码与密码是否一致
const validateCheckPass = (value1: any, value2: any) => {
  if(validateIsNull(value1) === false){
    return false;
  }else if(value1 != value2) {
    showErrorMsg('两次输入密码不一致')
    return false;
  }
  return true;
}

// 登录表单数据  使用 reacttive 使其为响应式数据
const loginForm = reactive({
  id: '',
  password: '',
})

// 注册表单数据
const logupForm = reactive({
  id: '',
  username: '',
  password: '',
  checkpass: ''
})

// 处理登录按钮的点击
const handleLoginIn = () => {
  if(validateIsNull(loginForm.id) === false || validateIsNull(loginForm.password) === false)
      return
  submitLoginForm(loginForm.id, loginForm.password)
}

// 处理注册按钮的点击
const handleLogUp = () => {
  if(validateIsNull(logupForm.id) === false || validateIsNull(logupForm.username) === false
      || validateCheckPass(logupForm.password, logupForm.checkpass) === false) {
    console.log('注册失败')
  }else {
    console.log(logupForm)
    submitLogupForm(logupForm.id, logupForm.username, logupForm.password)
    if (localStorage.getItem('upres') === 'true')
      pageName = 'signUp'
  }
}
</script>





<style scoped>

#login{
  position: absolute;
  width: 420px;
  height: 600px;
  left: 50%;
  top: 30%;
  margin: -200px 0 0 -200px;
  background-color: #F2F6FC;
  border-radius: 20px;
  opacity: 0.9;
}

.el-input{
  width: 200px;
}

#tabs{
  position: relative;
  margin-top: 140px;
  width: 300px;
  margin-left: 50px;
}

.el-form{
  margin-top: 60px;
  margin-left: -50px;
}

#title{
  font-family: "Microsoft YaHei";
  font-size: xx-large;
  font-weight: bold;
  position: absolute;
  margin-top: 70px;
  margin-left: -70px;

}

#box{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  /*background-color: #708090;*/
  background-image: url("../assets/bg6.png");
  background-size: 100% 100%;
}

.el-button{
  width: 200px;
  height: 35px;
  margin-top: 40px;
  border-radius: 20px;
}


</style>