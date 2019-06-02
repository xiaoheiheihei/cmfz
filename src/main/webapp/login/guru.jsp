<%@  page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<html>
<head>
    <script type="application/javascript">
        $(function () {
            $("#pager").jqGrid({
                url: "${pageContext.request.contextPath}/guru/queryAll",
                styleUI: "Bootstrap",
                colNames: ["ID", "法号", "头像", "状态", "创建时间"],
                datatype: "json",
                cellEdit: true,
                pager: "#guruPager",
                rowNum: "5",
                rowList: [1, 3, 5, 7, 9],
                /* caption: "user",*/
                autowidth: true,
                viewrecords: true,
                multiselect: true,
                rownumbers: true,
                editurl: "${pageContext.request.contextPath}/guru/edit",
                colModel: [
                    {name: "id", hidden: true},
                    {name: "dharma", editable: true},
                    {
                        name: "head_pic", editable: true, width: 40,
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
                ],

            }).jqGrid("navGrid", "#guruPager", {search: false},
                {

                    closeAfterEdit: true,
                    afterSubmit: function (response) {
                        var bannerId = response.responseJSON.bannerId;
                        var message = response.responseJSON.message;
                        alert(bannerId)
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
</div>
<table id="pager">
    <div id="guruPager" style="height: 50px"></div>
</table>
</body>
</html>
