<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-首页</title>
    <%@ include file="../base/base-css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../base/base-side.jsp"%>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-2">
                    <div class="box">
                        <div class="box-body">
                            <button class="btn btn-default" id="addDeptBtn">添加部门</button>
                            <ul id="ztree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-10">
                    <!-- Default box -->
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">员工管理</h3>
                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool"  title="Collapse">
                                    <i class="fa fa-plus"></i> 添加员工</button>
                            </div>
                        </div>
                        <div class="box-body">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>部门</th>
                                    <th>手机</th>
                                    <th>#</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- /.box -->
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp"%>


</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/tree/js/jquery.ztree.all.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function(){
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            async:{
                enable:true,
                url:"/manage/account/depts.json"
            },
            callback:{
                onClick:function(event,treeId,treeNode,clickFlag){
                    alert(treeNode.id + treeNode.name + treeNode.pId);
                }
            }
        };

       var tree = $.fn.zTree.init($("#ztree"), setting);


        //添加部门
        $("#addDeptBtn").click(function () {
            layer.prompt({"title":"请输入部门名称"},function(text,index){
                layer.close(index);//关闭对话框
                $.post("/manage/account/dept/new",{"deptName":text,"pId":1000}).done(function(data){
                    if(data.state == "success") {
                        layer.msg("添加成功");
                        tree.reAsyncChildNodes(null, "refresh");
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function(){
                    layer.msg("服务器异常");
                });
            });
        });
    });
</script>
</body>
</html>
