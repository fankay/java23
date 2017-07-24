<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-新增销售机会</title>
    <%@ include file="../base/base-css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="sales_my"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

          <div class="box">
              <div class="box-header with-border">
                  <h3 class="box-title">新增销售机会</h3>
                  <div class="box-tools pull-right">
                      <a href="/sales/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                  </div>
              </div>
              <div class="box-body">
                  <form id="newForm" method="post">
                      <input type="hidden" name="accountId" value="${sessionScope.curr_user.id}">
                      <div class="form-group">
                          <label>机会名称</label>
                          <input type="text" class="form-control" name="name">
                      </div>
                      <div class="form-group">
                          <label>关联客户</label>
                          <select name="custId" class="form-control">
                              <option value=""></option>
                              <c:forEach items="${customerList}" var="cust">
                                <option value="${cust.id}">${cust.custName}</option>
                              </c:forEach>
                          </select>
                      </div>
                      <div class="form-group">
                          <label>机会价值(元)</label>
                          <input type="text" name="worth" class="form-control">
                      </div>
                      <div class="form-group">
                          <label>当前进度</label>
                          <select name="progress" class="form-control">
                              <c:forEach items="${progressList}" var="progress">
                                    <option value="${progress}">${progress}</option>
                              </c:forEach>
                          </select>
                      </div>
                      <div class="form-group">
                          <label>详细内容</label>
                          <textarea name="content" class="form-control"></textarea>
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

    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/validate/jquery.validate.js"></script>
<script>
    $(function () {
        $("#saveBtn").click(function () {
            $("#newForm").submit();
        });
        $("#newForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                name:{
                    required:true
                },
                custId:{
                    required:true
                },
                worth:{
                    required:true
                }
            },
            messages:{
                name:{
                    required:"请输入机会名称"
                },
                custId:{
                    required:"请选择关联客户"
                },
                worth:{
                    required:"请输入机会价值"
                }
            }
        });
    });
</script>
</body>
</html>
