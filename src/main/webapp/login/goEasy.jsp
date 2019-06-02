<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="../goEasy/echarts.min.js"></script>
    <script src="../goEasy/china.js"></script>
</head>
<body>
<div id="chartmain" style="width:600px; height: 400px;"></div>
<script type="text/javascript">
    //指定图标的配置和数据
    var option = {
        title: {
            text: 'ECharts 数据统计'
        },
        tooltip: {},
        legend: {
            data: ['用户来源']
        },
        xAxis: {
            data: ["Android", "IOS", "PC", "Ohter"]
        },
        yAxis: {},
        series: [{
            name: '访问量',
            type: 'bar',
            data: [500, 200, 360, 100]
        }]
    };
    //初始化echarts实例
    var myChart = echarts.init(document.getElementById('chartmain'));

    //使用制定的配置项和数据显示图表
    myChart.setOption(option);
</script>
</body>
</html>