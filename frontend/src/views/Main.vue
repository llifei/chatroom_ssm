<template>
  <div class="background">
  <div id="box">
    <span id="title" style="font-family: 'Microsoft YaHei';font-weight: bold;font-size: x-large">好友列表</span>
    <el-row style="margin-top: 30px">
      <el-dropdown style="font-size: medium; background-color: white; border-radius: 5px; opacity: 0.6"
                   @command="(command)=>{if(command === 'exit'){exit()}}">
        <span class="el-dropdown-link">
          {{curUser.username}} <el-icon class="el-icon--right"><arrow-down /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>账号：{{curUser.id}}</el-dropdown-item>
            <el-dropdown-item command="exit">注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-input v-model="newfid" type="input" placeholder="ID" style="width: 120px; margin-left: 200px" autocomplete="off"></el-input>
      <el-button class = "topBtn" @click="handleAdd(newfid)" size="small">添加好友</el-button>
      <el-button class = "topBtn" id="chatBtn" type="primary" @click="goChat">一键群聊</el-button>
      <br/>
    </el-row>
    <el-table id="list" :data="tableData" :key="Math.random()" style="width: 100%; font-size: larger">
      <el-table-column label="账号" width="300">
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <el-icon><user /></el-icon>
            <span style="margin-left: 10px; font-size: small">{{ scope.row.id }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="昵称" width="300">
        <template #default="scope">
          <el-popover effect="light" trigger="hover" placement="top">
            <template #default>
              <p>name: {{ scope.row.name }}</p>
            </template>
            <template #reference>
              <div class="name-wrapper">
                <el-tag>{{ scope.row.name }}</el-tag>
              </div>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="管理">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  </div>
</template>

<script lang="ts" setup>
import {ref, getCurrentInstance} from 'vue'
import app from '../main'
import { User, ArrowDown } from '@element-plus/icons-vue'
import {useRouter, useRoute} from "vue-router";
import {delFriendById, doExit, showErrorMsg, showSuccessMsg} from "@/utils/axios";
import {addFriendById} from "@/utils/axios";
import qs from "qs";
import {ElMessage} from "element-plus";

interface User {
  id: number
  name: string
}
const tableData: User[] = []

// 获取路由
const router = useRouter()
const route = useRoute()
// 获取上下文实例
const {ctx: _this}: any = getCurrentInstance()
// 与空间数据绑定
const newfid = ref()

// 从上个路由中获取用户信息
let curUser = qs.parse(localStorage.getItem(route.query.id.toString()))
if(curUser.friendIds != null) {
  for (let i in Object.keys(curUser.friendIds)) {
    tableData.push({
      id: curUser.friendIds[i],
      name: curUser.friendNames[i],
    })
  }
}

// 处理删除按钮的点击
const handleDelete =  (index: number, row: User) => {
  console.log(index, row)
  let uid = curUser.id, fid = row.id
  delFriendById(uid, fid)
  if(localStorage.getItem('delres') === 'true')
    tableData.splice(index, 1)
  console.log(tableData)

  //此处需要强制刷新，因为 el-from 不会随着数据变化自动更新视图
  _this.$forceUpdate()
  localStorage.removeItem('delres')
}

// 转到聊天页面
const goChat = ()=> {
  let routeUrl = router.resolve({
    path: '/chat',
    query: {
      id: curUser.id,
      name: curUser.username
    },
  })
  window.open(routeUrl.href, '_blank')
}

// 处理添加好友按钮的点击
const handleAdd = (fid) => {
  if(fid == null || fid == '')
    showErrorMsg('输入不能为空')
  else {
    console.log(curUser.id)
    console.log(fid)
    addFriendById(curUser.id, fid)
    let newname = localStorage.getItem('addres')
    console.log(newname)

    if ((newname != null) && (newname.length > 2)) {
      tableData.push({
        id: fid,
        name: newname
      })
      showSuccessMsg('添加成功')

      //此处需要强制刷新，因为 el-from 不会随着数据变化自动更新视图
      _this.$forceUpdate()
    }
    else if(newname === '-1')
      showErrorMsg('添加失败（已存在此好友）')
    else if(newname === '-2')
      showErrorMsg('添加失败（不存在此人）')
    else
      showErrorMsg('添加失败（未知错误）')
    localStorage.removeItem('addres')
  }
}

// 退出登录
const exit = () => {
  doExit(curUser.id)
  router.push('/')
}

</script>

<style scoped>
#box{
  position: absolute;
  width: 900px;

  left: 50%;
  top: 30%;
  margin: -200px 0 0 -400px;
}

.topBtn{
  width: 90px;
  height: 33px;
  font-size: 14px;
}

#chatBtn{
  position: absolute;
  right: 225px;
}

#list{
  position: absolute;
  top: 150px;
}

#title{
  margin-left: -160px;
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
