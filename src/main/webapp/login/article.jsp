<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>

    <script type="application/javascript">
        $(function () {
            $("#pager").jqGrid({
                url: "${pageContext.request.contextPath}/article/queryAll",
                styleUI: "Bootstrap",
                colNames: ["ID", "标题", "作者", "文章内容", "创建时间", "上师Id", "上传时间", "状态", "操作"],
                datatype: "json",
                cellEdit: true,
                pager: "#articlePager",
                rowNum: "5",
                rowList: [1, 3, 5, 7, 9],
                caption: "article",
                autowidth: true,
                viewrecords: true,
                multiselect: true,
                rownumbers: true,
                editurl: "${pageContext.request.contextPath}/article/delete",
                colModel: [
                    {name: "id", hidden: true},
                    {name: "title", editable: true},
                    {name: "author", editable: true},
                    {name: "content", editable: true, hidden: true},
                    {name: "create_date", edittype: "date"},
                    {name: "guru_id", hidden: true},
                    {name: "upload_date", edittype: "date"},
                    {
                        name: "status", editable: true, edittype: "select",
                        editoptions: {
                            value: "展示:展示;不展示:不展示"
                        }
                    },
                    {
                        name: "",
                        formatter: function (cellValue, opo, value) {
                            return "<button type=\"button\" class=\"btn btn-primary\" onclick=\"lookModal('" + value.id + "')\">查看详情</button>\t" +
                                "<button type=\"button\" class=\"btn btn-danger\"  onclick=\"deleteModal('" + value.id + "')\">删除</button>"
                        }
                    }
                ],

            }).jqGrid("navGrid", "#articlePager", {search: false, add: false, edit: false},
                {
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var message = response.responseJSON.message;
                        $("#pager").trigger("reloadGrid");
                        $("#bannerMsgId").show().html(message);
                        setTimeout(function () {
                            $("#bannerMsgId").hide()
                        }, 5000);
                        return response;
                    }
                }, {
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var message = response.responseJSON.message;
                        $("#pager").trigger("reloadGrid");
                        $("#bannerMsgId").show().html(message);
                        setTimeout(function () {
                            $("#bannerMsgId").hide()
                        }, 5000);
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
                        return response;
                    }

                }
            )
        })

        function showModal() {
            $("#myModal").modal("show")
            //清空前一次数据
            $("#addArticleFrom")[0].reset();
            KindEditor.html("#editor", "");
            $("#modal_footer").html(
                "<button type=\"button\" class=\"btn btn-primary\" onclick=\"addModal()\">保存</button>\n" +
                "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
            )
            KindEditor.create("#editor", {
                uploadJson: "${pageContext.request.contextPath}/kind/edit",
                filePostName: "img",
                fileManagerJson: "${pageContext.request.contextPath}/kind/getAll",
                allowFileManager: true,
                resizeType: 1,
                afterBlur: function () {
                    this.sync();
                }
            })
        }

        function addModal() {
            $("#myModal").modal("hide");
            $.ajax({
                url: "${pageContext.request.contextPath}/article/edit",
                data: $("#addArticleFrom").serialize(),
                dataType: "json",
                type: "post",
                success: function (map) {
                    change(map);
                }
            })
        }

        function lookModal(id) {
            var value = $("#pager").jqGrid("getRowData", id);
            $("#myModal").modal();
            $("#id").val(value.id);
            $("#title").val(value.title);
            $("#author").val(value.author);
            if (value.status == "展示") {
                $("title").val("展示")
            } else {
                $("title").val("不展示")
            }
            $("#modal_footer").html(
                "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateModal()\">保存</button>\n" +
                "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>")
            KindEditor.create('#editor', {
                uploadJson: "${pageContext.request.contextPath}/kind/edit",
                filePostName: "img",//默认是imgFile
                fileManagerJson: "${pageContext.request.contextPath}/kind/getAll",
                allowFileManager: true,
                resizeType: 1,
                afterBlur: function () {
                    this.sync()
                }
            });
            $("#editor").val(value.content)
            KindEditor.html("#editor", value.content);
        }

        function updateModal() {
            $("#myModal").modal("hide");
            $.ajax({
                url: "${pageContext.request.contextPath}/article/update",
                data: $("#addArticleFrom").serialize(),
                dataType: "json",
                type: "post",
                success: function (map) {
                    change(map);
                }

            })
        }

        function deleteModal(id) {
            var value = $("#pager").jqGrid("getRowData", id);
            $.ajax({
                url: "${pageContext.request.contextPath}/article/delete",
                data: "id=" + value.id,
                type: "post",
                dataType: "json",
                success: function (map) {
                    change(map);
                }
            })

        }

        function change(map) {
            var message = map.message;
            $("#bannerMsgId").show().html(message);
            setTimeout(function () {
                $("#bannerMsgId").hide()
            }, 2000);
            $("#pager").trigger("reloadGrid");
        }
    </script>
</head>
<body>
<h3>专辑管理</h3>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">专辑信息</a></li>
        <li><a href="javascript:void(0)" onclick="showModal()"><b>添加文章</b></a></li>
    </ul>
</div>
<div id="bannerMsgId" class="alert alert-warning alert-dismissible" role="alert" style="display:none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <strong>Warning!</strong>
</div>
<table id="pager">
    <div id="articlePager" style="height: 50px"></div>
</table>
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content" style="width:750px">
            <!--模态框标题-->
            <div class="modal-header">
                <!--
                    用来关闭模态框的属性:data-dismiss="modal"
                -->
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">编辑用户信息</h4>
            </div>
            <!--模态框内容体-->
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/article/queryAll" class="form-horizontal"
                      id="addArticleFrom">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">id</label>
                        <div class="col-sm-5">
                            <input type="hidden" name="id" id="id" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">作者</label>
                        <div class="col-sm-5">
                            <input type="text" name="author" id="author" placeholder="请输入作者" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="status">
                                <option value="展示">展示</option>
                                <option value="不展示">不展示</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                    <input id="addInsertImg" name="insertImg" hidden>
                </form>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">

            </div>
        </div>
    </div>
</div>
</body>
</html>
