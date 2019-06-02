<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>持名法州官网</title>
    <link rel="icon" href="../img/favicon.ico">
    <%-- bootstrap 相关css --%>
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <%-- jqgrid相关css --%>
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%-- 必须先引入jquery.js --%>
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <%-- bootstrap的js --%>
    <script src="../boot/js/bootstrap.min.js"></script>
    <%-- jqgrid  js --%>
    <script src="../jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%-- ajaxFileUpload  --%>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script type="application/javascript">
        $(function () {
            $("#pager").jqGrid({
                url: "${pageContext.request.contextPath}/banner/queryAll",
                styleUI: "Bootstrap",
                colNames: ["ID", "标题", "头像", "状态", "创建时间", "内容"],
                datatype: "json",
                cellEdit: true,
                pager: "#bannerPager",
                rowNum: "5",
                rowList: [1, 3, 5, 7, 9],
                caption: "banner",
                autowidth: true,
                viewrecords: true,
                multiselect: true,
                rownumbers: true,
                editurl: "${pageContext.request.contextPath}/banner/edit",
                colModel: [
                    {name: "id", hidden: true},
                    {name: "title", editable: true},
                    {
                        name: "img_pic", editable: true, width: 40,
                        formatter: function (cellvalue, options, rowObject) {
                            var img = ("<img width=20 class='img-circle'" +
                                "src=${pageContext.request.contextPath}/upload/" + cellvalue + ">");
                            return img;
                        }, edittype: "file"
                    },

                    {
                        name: "status", editable: true, edittype: "select",
                        editoptions: {
                            value: "展示:展示;不展示:不展示"
                        }
                    },
                    {name: "create_date", editable: true, edittype: "date"},
                    {name: "description", editable: true}
                ],

            }).jqGrid("navGrid", "#bannerPager", {search: false},
                {
                    closeAfterEdit: true,
                    afterSubmit: function (response) {
                        var bannerId = response.responseJSON.bannerId;
                        var message = response.responseJSON.message;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/upload",
                            fileElementId: "img_pic",
                            type: "post",
                            data: {bannerId: bannerId},
                            success: function (data) {
                                $("#pager").trigger("reloadGrid");
                                $("#bannerMsgId").show().html(message);
                                setTimeout(function () {
                                    $("#bannerMsgId").hide()
                                }, 5000);
                            }

                        })
                        return response;
                    }
                }, {
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var bannerId = response.responseJSON.bannerId;
                        var message = response.responseJSON.message;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/upload",
                            fileElementId: "img_pic",
                            type: "post",
                            data: {bannerId: bannerId},
                            success: function (data) {
                                $("#pager").trigger("reloadGrid");
                                $("#bannerMsgId").show().html(message);
                                setTimeout(function () {
                                    $("#bannerMsgId").hide()
                                }, 5000);
                            }

                        })
                        return response;
                    }
                },
                {
                    afterComplete: function (response) {
                        var message = response.responseJSON.message;
                        $("#bannerMsgId").show().html(message);
                        setTimeout(function () {
                            $("#bannerMsgId").hide()
                        }, 5000);
                    }
                }
            )
        })
    </script>
</head>
<body>
<div id="bannerMsgId" class="alert alert-warning alert-dismissible" role="alert" style="display:none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <strong>Warning!</strong>
</div>
<table id="pager">
    <div id="bannerPager" style="height: 50px"></div>
</table>
</body>
</html>
