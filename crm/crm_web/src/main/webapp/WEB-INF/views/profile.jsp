<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-首页</title>
    <%@ include file="base/base-css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="base/base-side.jsp">
        <jsp:param name="active" value="home"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">个人设置</h3>
                </div>
                <div class="box-body">
                    <form id="settingForm">
                        <div class="form-group">
                            <label>原始密码</label>
                            <input type="password" class="form-control" name="oldPassword">
                        </div>
                        <div class="form-group">
                            <label>新密码</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <div class="form-group">
                            <label>确认密码</label>
                            <input type="password" class="form-control" name="repassword">
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="saveBtn">保存</button>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="base/base-js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/validate/jquery.validate.js"></script>
<script>
    $(function () {
        $("#saveBtn").click(function () {
            $("#settingForm").submit();
        })

        $("#settingForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                oldPassword:{
                    required:true
                },
                password:{
                    required:true
                },
                repassword:{
                    required:true,
                    equalTo:"#password"
                }
            },
            messages:{
                oldPassword:{
                    required:"请输入原始密码"
                },
                password:{
                    required:"请输入新密码"
                },
                repassword:{
                    required:"请输入确认密码",
                    equalTo:"两次密码不一致"
                }

            },
            submitHandler:function(){
                $.post("/profile",$("#settingForm").serialize()).done(function(data){
                    if(data.state == "success") {
                        layer.alert("密码修改成功，请重新登录",function(){
                            window.location.href = "/";
                        });
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function(){
                    layer.msg("服务器异常");
                });
            }
        });
    });
</script>

</body>
</html>
