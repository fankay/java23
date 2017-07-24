<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-我的销售机会</title>
    <%@ include file="../base/base-css.jsp"%>
    <style>
        .table>tbody>tr:hover {
            cursor: pointer;
        }
    </style>
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

            <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
            </c:if>


            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的销售机会</h3>

                    <div class="box-tools pull-right">
                        <a href="/sales/my/new" type="button" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加机会
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>机会名称</th>
                            <th>关联客户</th>
                            <th>机会价值(元)</th>
                            <th>当前进度</th>
                            <th>最后跟进时间</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${page.list}" var="sale">
                                <tr class="sales_row" rel="${sale.id}">
                                    <td>${sale.name}</td>
                                    <td>${sale.customer.custName}</td>
                                    <td><fmt:formatNumber value="${sale.worth}"/> </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sale.progress == '成交'}">
                                                <span class="label label-success">${sale.progress}</span>
                                            </c:when>
                                            <c:when test="${sale.progress == '暂时搁置'}">
                                                <span class="label label-danger">${sale.progress}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${sale.progress}
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                    <td><fmt:formatDate value="${sale.lastTime}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
                <c:if test="${pageInfo.pages > 1}" >
                    <div class="box-footer">
                        <ul id="pagination-demo" class="pagination-sm pull-right"></ul>
                    </div>
                </c:if>
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/page/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        <c:if test="${pageInfo.pages > 1}" >
            //分页
            $('#pagination-demo').twbsPagination({
                totalPages: ${pageInfo.pages},
                visiblePages: 7,
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?p={{number}}"
            });
        </c:if>

        $(".sales_row").click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/sales/my/"+id;
        });
    });
</script>
</body>
</html>
