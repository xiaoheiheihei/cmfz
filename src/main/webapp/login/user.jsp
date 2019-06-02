<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <script type="application/javascript">
        $(function () {
            $("#pager").jqGrid({
                url: "${pageContext.request.contextPath}/user/queryAll",
                styleUI: "Bootstrap",
                colNames: ["ID", "手机号", "密码", "头像", "姓名", "法名", "性别", "省", "市", "简介", "状态", "创建时间", "上师ID", "操作"],
                datatype: "json",
                cellEdit: true,
                pager: "#userPager",
                rowNum: "5",
                rowList: [1, 3, 5, 7, 9],
                /* caption: "user",*/
                autowidth: true,
                viewrecords: true,
                multiselect: true,
                rownumbers: true,
                editurl: "${pageContext.request.contextPath}/user/edit",
                colModel: [
                    {name: "id", hidden: true},
                    {name: "phone_num", editable: true},
                    {name: "password", editable: true, hidden: true},
                    {
                        name: "head_pic", editable: true, width: 40,
                        formatter: function (cellvalue, options, rowObject) {
                            var img = ("<img width=20 class='img-circle'" +
                                "src=${pageContext.request.contextPath}/upload/" + cellvalue + ">");
                            return img;
                        }, edittype: "file"
                    },
                    {name: "name", editable: true},
                    {name: "dharma", editable: true},
                    {
                        name: "sex", editable: true, edittype: "select",
                        editoptions: {
                            value: "男:男;女:女"
                        }
                    },
                    {name: "province", editable: true, hidden: true},
                    {name: "city", editable: true, hidden: true},
                    {name: "sign", editable: true, hidden: true},
                    {
                        name: "status", editable: true, edittype: "select",
                        editoptions: {
                            value: "激活:激活;冻结:冻结"
                        }
                    },
                    {name: "create_date", editable: true, edittype: "date"},
                    {name: "guru_id", hidden: true},
                    {
                        name: "",
                        formatter: function (cellValue, opo, value) {
                            return "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + value.id + "')\">更改状态</button>"
                        }
                    }
                ],

            }).jqGrid("navGrid", "#userPager", {search: false},
                {
                    closeAfterEdit: true,
                    afterSubmit: function (response) {
                        var userId = response.responseJSON.userId;
                        var message = response.responseJSON.message;
                        alert(userId)
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/user/upload",
                            fileElementId: "head_pic",
                            type: "post",
                            data: {userId: userId},
                            success: function (data) {
                                $("#bannerMsgId").trigger("reloadGrid");
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
                        var userId = response.responseJSON.userId;
                        var message = response.responseJSON.message;
                        alert(userId)
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/user/upload",
                            fileElementId: "head_pic",
                            type: "post",
                            data: {userId: userId},
                            success: function (data) {
                                $("#bannerMsgId").trigger("reloadGrid");
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

        function update(id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/user/update",
                data: "id=" + id,
                dataType: "json",
                type: "post",
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

        function find2() {
            $("#myModal").modal("show");
            var myChart = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: 'ECharts 入门示例'
                },
                tooltip: {},
                legend: {
                    data: ['销量']
                },
                xAxis: {
                    data: ["1", "2", "3", "4", "5", "6", "7"]
                },

                yAxis: {}

            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/user/getCount",
                type: "get",
                dataType: "json",
                success: function (data) {

                    /*//go-easy 接收数据
                      var goEasy = new GoEasy({
                          appkey: "BC-db8c5a58233845fba8b8e9063d54aa3f"
                      });
                      goEasy.subscribe({
                          channel: "my_channel",
                          onMessage: function (data) {
                              // console.log("Channel:" + message.channel + " content:" + message.content);
                          }
                      });
 */
                    myChart.setOption({
                        series: [
                            {
                                name: '人数',
                                type: 'line',
                                data: data
                            }
                        ],
                    });
                }
            })

        }

        function find3() {
            $("#myModal").modal("show");
            //初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            //指定图标的配置和数据
            option = {
                title: {
                    text: 'iphone销量',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['iphone3']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: 'iphone3',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/user/getP1",
                type: "post",
                dataType: "json",
                success: function (list) {
                    myChart.setOption({
                        series: [
                            {
                                data: list
                            }
                        ]
                    })
                }
            })

        }

        function find1() {
            $("#myModal").modal("show");
            var myChart = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: 'ECharts 入门示例'
                },
                tooltip: {},
                legend: {
                    data: ['销量']
                },
                xAxis: {
                    data: []
                },
                yAxis: {}

            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.ajax({
                url: "${pageContext.request.contextPath}/user/getCount",
                type: "get",
                dataType: "json",
                success: function (data) {
                    /*//go-easy 接收数据
                      var goEasy = new GoEasy({
                          appkey: "BC-db8c5a58233845fba8b8e9063d54aa3f"
                      });
                      goEasy.subscribe({
                          channel: "my_channel",
                          onMessage: function (data) {
                              // console.log("Channel:" + message.channel + " content:" + message.content);
                          }
                      });
 */
                    myChart.setOption({
                        xAxis: {
                            data:
                            data[0].name
                        },
                        /*xAxis: {
                            data: ["5/29","5/28","5/27","5/26","5/25","5/24","5/23"]
                        },*/
                        series: [
                            {
                                name: '人数',
                                type: 'line',
                                data: data[0].value
                            }
                        ],
                    });
                }
            })

        }

    </script>
</head>
<body>

<div id="bannerMsgId" class="alert alert-warning alert-dismissible" role="alert" style="display:none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <strong>Warning!</strong>
</div>
<div class="btn-group" role="group" aria-label="...">
    <button type="button" class="btn btn-default"><a href="#"><span style="color: #449d44">用户详情信息</span></a></button>
    <button type="button" class="btn btn-success"><a href="${pageContext.request.contextPath}/user/export">导出文件</a>
    </button>
    <button type="button" class="btn btn-default" onclick="find1()">查看七天用户注册信息走势图</button>
    <button type="button" class="btn btn-default" onclick="find2()">查看每月用户注册信息走势图</button>
    <button type="button" class="btn btn-default" onclick="find3()">用户分布图</button>
</div>
<table id="pager">
    <div id="userPager" style="height: 50px"></div>
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
                <h4 class="modal-title">查看用户注册信息走势图</h4>
            </div>
            <div class="modal-body">
                <div id="main" style="width:600px; height: 400px;"></div>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">

            </div>
        </div>
    </div>
</div>
</body>
</html>
