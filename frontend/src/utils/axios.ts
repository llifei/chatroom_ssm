import router from "../router/index";
import axios from 'axios';
import qs from 'qs';
import { ElMessage } from 'element-plus'

export const showErrorMsg = (message) => {
    ElMessage({
        type: 'error',
        message: message
    })
}

export const showSuccessMsg = (message) => {
    ElMessage({
        type: 'success',
        message: message
    })
}

export const submitLoginForm = (id: any, password: any) => {
    console.log('submit!')
    axios.post('/api/login', qs.stringify({
        id: id,
        password: password,
    })).then((res) => {
        console.log(res.data)
        if(res.data === null)
            showErrorMsg('登录失败')
        else {
            showSuccessMsg('登录成功')
            console.log(res.data)
            localStorage.setItem(id.toString(), qs.stringify(res.data))
            router.push({
                path: '\main',
                query: {
                    id: id
                }
            })
        }
    })
}

export const submitLogupForm = (id: any, username: any, password: any) => {
    console.log('submit!')
    axios.post('/api/register', qs.stringify({
        id: id,
        username: username,
        password: password,
    })).then((res) => {
        console.log(res.data)
        if(res.data === false) {
            showErrorMsg('注册失败（账号已注册）！')
            localStorage.setItem('upres', 'false')
        }
        else {
            showSuccessMsg( '注册成功！')
            localStorage.setItem('upres', 'true')
        }
    })
}

export const delFriendById = (uid: any, fid: any) => {
    axios.post('/api/delFriendById', qs.stringify({
        uid: uid,
        fid: fid,
    }))
        .then((res) => {
            if(res.data === true) {
                showSuccessMsg("删除成功")
                localStorage.setItem('delres', 'true')
            }
            else {
                showErrorMsg("删除失败")
                localStorage.setItem('delres', 'false')
            }
        })
}

export const addFriendById = (uid: any, fid: any) => {
    // console.log(uid)
    // console.log(fid)
    axios.post('/api/addFriendById', qs.stringify({
        uid: uid,
        fid: fid,
    }))
    .then((res) => {
        localStorage.setItem('addres', res.data)
    })
}

export const sendMsg = (message, uid) => {
    axios.post('/api/sendMsg',  qs.stringify({
        content: message,
        uid: uid
    })).then((res) => {
        if(res.data == true)
            console.log('发送消息成功：' + message);
        else {
            console.log('发送失败')
            showErrorMsg('发送失败')
        }
    })
}

export const doExit = (uid) => {
    axios.post('/api/exit', qs.stringify(uid)).then((res) => {
        console.log(res.data)
    })
}
