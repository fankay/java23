<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">

        <div class="panel panel-default">
            <div class="panel-heading">
                搜索
            </div>
            <div class="panel-body">
                <form method="get" class="form-inline">
                    <input type="text" placeholder="客户名称 或 联系方式" class="form-control" name="q_like_s_custName_or_mobile" value="${q_like_s_custName_or_mobile}">
                    <input type="text" placeholder="客户ID" class="form-control" name="q_eq_i_id" value="${q_eq_i_id}">
                    <input type="text" placeholder="账号ID" class="form-control" name="q_eq_i_account.id" value="${requestScope['q_eq_i_account.id']}">
                    <input type="text" placeholder="账号名称" class="form-control" name="q_eq_s_a.userName" value="${requestScope['q_eq_s_a.userName']}">
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>


        <table class="table">
            <thead>
            <tr>
                <th>客户名称</th>
                <th>地址</th>
                <th>级别</th>
                <th>联系电话</th>
                <th>来源</th>
                <td>所属账号</td>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.items}" var="cust">
                    <tr>
                        <td>${cust.custName}</td>
                        <td>${cust.address}</td>
                        <td>${cust.level}</td>
                        <td>${cust.mobile}</td>
                        <td>${cust.source}</td>
                        <td>${cust.account.userName}</td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
        <ul id="pagination-demo" class="pagination-sm"></ul>
    </div>
    <script src="/static/js/jquery.js"></script>
    <script src="/static/layer/layer.js"></script>
    <script src="/static/js/jquery.twbsPagination.min.js"></script>
    <script>
        $(function () {
            //分页
            $('#pagination-demo').twbsPagination({
                totalPages: ${page.totalPageSize},
                visiblePages: 5,
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?p={{number}}&custName=${custName}&source=${source}"
            });
        });
    </script>
</body>
</html>