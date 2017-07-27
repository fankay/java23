<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户级别统计</title>
    <%@ include file="../base/base-css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="charts_customer"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户级别数量统计</h3>
                </div>
                <div class="box-body">
                    <div id="bar" style="height: 300px;width: 100%"></div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp" %>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp" %>
<script src="/static/plugins/echarts/echarts.common.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {
        var bar = echarts.init(document.getElementById("bar"));

        var option = {
            title: {
                text: "客户级别数量统计",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['人数'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {},
            series: {
                name: "人数",
                type: 'bar',
                data: []
            }
        }
        bar.setOption(option);
        
        $.get("/charts/customer/bar.json").done(function (resp) {
            var levelArray = [];
            var dataArray = [];
            //[{level:'*',num:3},{level:'**',num:5}]
            for(var i = 0;i < resp.data.length;i++) {
                var obj = resp.data[i];
                levelArray.push(obj.level);
                dataArray.push(obj.num);
            }

            bar.setOption({
                xAxis: {
                    data: levelArray
                },
                series: {
                    data: dataArray
                }
            });
        }).error(function () {
            layer.msg("获取数据异常");
        });


    });
</script>

</body>
</html>
