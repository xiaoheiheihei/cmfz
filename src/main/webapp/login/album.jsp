<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>

    <script type="application/javascript">

        $(function () {
            $("#pager").jqGrid({
                url: "${pageContext.request.contextPath}/album/queryAll",
                styleUI: "Bootstrap",
                colNames: ["ID", "标题", "分数", "作者", "播音员", "章节数", "专辑简介", "状态", "发行时间", "上传时间", "插图"],
                datatype: "json",
                cellEdit: true,
                pager: "#albumPager",
                rowNum: "5",
                rowList: [1, 3, 5, 7, 9],
                caption: "banner",
                height: '300px',
                autowidth: true,
                viewrecords: true,
                multiselect: true,
                rownumbers: true,
                editurl: "${pageContext.request.contextPath}/album/edit",
                colModel: [
                    {name: "id", hidden: true},
                    {name: "title", editable: true},
                    {name: "score", editable: true},
                    {name: "author", editable: true},
                    {name: "broadcast", editable: true},
                    {name: "count"},
                    {name: "brief", editable: true},
                    {
                        name: "status", editable: true, edittype: "select",
                        editoptions: {
                            value: "展示:展示;不展示:不展示"
                        }
                    },
                    {name: "create_date", editable: true, edittype: "date"},
                    {name: "upload_date", editable: true, edittype: "date"},
                    {
                        name: "cover_pic", editable: true, width: 40,
                        formatter: function (cellValue, options, rowObject) {
                            return "<img width=20 class='img-circle'" +
                                "src=${pageContext.request.contextPath}/upload/" + cellValue + "/>";
                        }, edittype: "file"
                    },
                ],
                subGrid: true,
                subGridRowExpanded: function (subGidId, album_id) {
                    addSubGrid(subGidId, album_id);
                }
            }).jqGrid("navGrid", "#albumPager", {search: false},
                {
                    closeAfterEdit: true,
                    afterSubmit: function (response) {
                        var albumId = response.responseJSON.albumId;
                        var message = response.responseJSON.message;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",
                            fileElementId: "cover_pic",
                            type: "post",
                            data: {albumId: albumId},
                            success: function (data) {
                                $("#albumMsgId").trigger("reloadGrid");
                                $("#albumMsgId").show().html(message);
                                setTimeout(function () {
                                    $("#albumMsgId").hide()
                                }, 5000);
                            }

                        })
                        return response;
                    }
                }, {
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var albumId = response.responseJSON.albumId;
                        var message = response.responseJSON.message;
                        alert(albumId)
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",
                            fileElementId: "cover_pic",
                            type: "post",
                            data: {albumId: albumId},
                            success: function (data) {
                                $("#albumMsgId").trigger("reloadGrid");
                                $("#albumMsgId").show().html(message);
                                setTimeout(function () {
                                    $("#albumMsgId").hide()
                                }, 5000);
                            }

                        })
                        return response;
                    }
                },
                {
                    afterComplete: function (response) {
                        var message = response.responseJSON.message;
                        $("#albumMsgId").show().html(message);
                        setTimeout(function () {
                            $("#albumMsgId").hide()
                        }, 5000);
                    }
                }
            )
        })

        function addSubGrid(subGidId, album_id) {
            var subGridTableId = subGidId + "table";
            var subGridPagerId = subGidId + "pager";
            $("#" + subGidId).html(
                "<table id='" + subGridTableId + "' class=\"table table-striped\"></table>" +
                "<div id='" + subGridPagerId + "' style=\"height: 50px\"></div>")
            $("#" + subGridTableId).jqGrid({
                url: "${pageContext.request.contextPath}/chapter/queryAll?album_id=" + album_id,
                styleUI: "Bootstrap",
                colNames: ["ID", "标题", "大小", "时长", "上传时间", "音频", "专辑id", "操作"],
                datatype: "json",
                cellEdit: true,
                pager: "#" + subGridPagerId,
                rowNum: "5",
                rowList: [1, 3, 5, 7, 9],
                caption: "banner",
                height: '100px',
                autowidth: true,
                viewrecords: true,
                multiselect: true,
                rownumbers: true,
                editurl: "${pageContext.request.contextPath}/chapter/edit?album_id=" + album_id,
                colModel: [
                    {name: "id", hidden: true},
                    {name: "title", editable: true},
                    {name: "size"},
                    {name: "duration"},
                    {name: "upload_date", editable: true, edittype: "date"},
                    {
                        name: "audio_path", editable: true,
                        formatter: function (cellValue, options, rowObject) {
                            alert(cellValue)
                            return ("<audio  src='${pageContext.request.contextPath}/upload1/" + cellValue + "'controls/>");

                        }, edittype: "file"
                    },
                    {name: "album_id", hidden: true},
                    {
                        name: "audio_path", entitype: "file",
                        formatter: function (cellValue, options, value) {
                            return "<button type=\"button\" class=\"btn btn-default btn-lg\"><a href='#' onclick=\"playAudio('" + cellValue + "')\"><span class='glyphicon glyphicon-play-circle'>在线播放</span></a></button>" +
                                "<button type=\"button\" class=\"btn btn-default btn-lg\"><a href='#'onclick=\"downLoadAudio ('" + cellValue + "')\"><span class='glyphicon glyphicon-download'>下载</span></a></button>";
                        }
                    }
                ],
            }).jqGrid("navGrid", "#" + subGridPagerId, {search: false},
                {
                    closeAfterEdit: true,
                    afterSubmit: function (response) {
                        var chapterId = response.responseJSON.chapterId;
                        var message = response.responseJSON.message;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/upload",
                            fileElementId: "audio_path",
                            type: "post",
                            data: {chapterId: chapterId},
                            success: function (data) {
                                $("#pager").trigger("reloadGrid");
                                $("#albumMsgId").show().html(message);
                                setTimeout(function () {
                                    $("#chapterMsgId").hide()
                                }, 5000);
                            }

                        })
                        return response;
                    }
                },
                {
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var chapterId = response.responseJSON.chapterId;
                        var message = response.responseJSON.message;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/upload",
                            fileElementId: "audio_path",
                            type: "post",
                            data: {chapterId: chapterId},
                            success: function (data) {
                                $("#pager").trigger("reloadGrid");
                                $("#albumMsgId").show().html(message);
                                setTimeout(function () {
                                    $("#chapterMsgId").hide();
                                }, 5000);
                            }

                        })
                        return response;
                    }
                },
                {
                    afterComplete: function (response) {
                        var message = response.responseJSON.message;
                        $("#albumMsgId").show().html(message);
                        setTimeout(function () {
                            $("#albumMsgId").hide()
                        }, 5000);
                    }
                }
            )
        }

        function playAudio(cellValue) {
            $("#playAudio").modal("show")
            $("#audioId").attr("src", "${pageContext.request.contextPath}/upload1/" + cellValue);
        }

        function downLoadAudio(cellValue) {
            location.href = "${pageContext.request.contextPath}/chapter/download?audio_path=" + cellValue;
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

    </ul>
</div>
<div id="playAudio" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" data-target=".bs-example-modal-sm">
        <audio id="audioId" src="" controls></audio>
    </div><!-- /.modal-dialog -->
</div>
<div id="albumMsgId" class="alert alert-warning alert-dismissible" role="alert" style="display:none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <strong>Warning!</strong>
</div>
<table id="pager" style='height:200px'>
    <div id="albumPager" style='height:50px'></div>
</table>

</body>
</html>
