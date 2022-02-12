export const createWebSocket = path => {
    if(typeof(WebSocket) === 'undefined')
        alert('您的浏览器不支持socket')
    let socket = new WebSocket(path)
    socket.onopen = () => {
        console.log('websocket 连接成功！')
    }
    return socket
}