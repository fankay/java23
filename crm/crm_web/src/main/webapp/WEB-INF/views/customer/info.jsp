<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户详情</title>
    <%@ include file="../base/base-css.jsp"%>
    <style>
        .td_title {
            font-weight: bold;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="customer_my"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/customer/my/${customer.id}/edit" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                        <button id="tranBtn" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                        <button rel="${customer.id}" id="sharePublicBtn" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>
                        <button id="delBtn" rel="${customer.id}" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.custName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.mobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td class="star">${customer.level}</td>
                        </tr>
                        <c:if test="${not empty customer.address}">
                            <tr>
                                <td class="td_title">地址</td>
                                <td colspan="5">${customer.address}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty customer.mark}">
                            <tr>
                                <td class="td_title">备注</td>
                                <td colspan="5">${customer.mark}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <div class="box-footer">
                    <c:if test="${not empty customer.reminder}">
                        <span style="color: #ccc;"><i class="fa fa-exchange"></i> ${customer.reminder}</span>
                    </c:if>
                    <span style="color: #ccc" class="pull-right">
                        创建日期：<fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd HH:mm"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${not empty customer.updateTime}">
                            最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                        </c:if>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>


        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <div class="modal fade" id="accountModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择转入员工</h4>
                </div>
                <div class="modal-body">
                    <select id="accountId" class="form-control">
                        <option value=""></option>
                        <c:forEach items="${accountList}" var="account">
                            <option value="${account.id}">${account.userName} ( ${account.mobile} )</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="tranBtnOk">转交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->


    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {

        var custId = ${customer.id};

        //删除客户
        $("#delBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("删除客户会自动删除相关数据，确定吗?",function(){
                window.location.href = "/customer/my/"+id+"/del";
            });
        });
        //放入公海
        $("#sharePublicBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要将该客户放入公海吗？",function () {
                window.location.href = "/customer/my/"+id+"/share/public";
            });
        });
        //转交他人
        $("#tranBtn").click(function () {
            $("#accountModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#tranBtnOk").click(function () {
            var accountId = $("#accountId").val();
            if(!accountId) {
                layer.msg("请选择转入账号");
                return;
            }
            window.location.href = "/customer/my/"+custId+"/tran/"+accountId;
        });
    })
</script>

</body>
</html>
