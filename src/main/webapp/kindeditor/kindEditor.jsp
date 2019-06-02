<%@  page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>标题</title>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function (k) {
            k.create("#editor_id", {
                uploadJson: "${pageContext.request.contextPath}/kind/edit",
                filePostName: "img",
                fileManagerJson: "${pageContext.request.contextPath}/kind/getAll",
                allowFileManager: true
            })
        })
    </script>
</head>
<body>
<h1>
    kindeditor
</h1>

<from action="${pageContext.request.contextPath}/kind/add">
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
      <h3>请输入文章</h3>
        <input type="submit" value="按我">
        <div></div>
  </textarea>
</from>
</body>
</html>
