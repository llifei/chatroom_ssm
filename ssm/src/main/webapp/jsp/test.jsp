<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>初步认识vue.js操作②</title>
</head>
<body>
　　<div id="myView">
    　　　　<ol>
    　　　　　　<!-- 配置为循环项 -->
    　　　　　　<li v-for="student in studentList">{{student.name}}</li>
    　　　　</ol>
    　　</div>
</body>
<script src="https://cdn.bootcss.com/vue/2.5.2/vue.min.js"></script>
<script src="https://cdn.bootcss.com/qs/6.7.0/qs.min.js"></script>
<script type="text/javascript">
    console.log(QS);
    var myModel = {studentList:[{name:'小明'},{name:'小红'},{name:'小刚'}]};
    var myViewModel1 = new Vue({
        el:'#myView',
        data:myModel
    });


</script>
</html>