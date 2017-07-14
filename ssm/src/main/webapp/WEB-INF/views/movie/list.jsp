<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <title></title>
</head>
<body>

    <div class="container">

        <div class="panel panel-info">
            <div class="panel-heading">
                搜索
            </div>
            <div class="panel-body">
                <form class="form-inline" method="get">
                    <input type="text" class="form-control" name="title" placeholder="电影名称" value="${title}">
                    <input type="text" class="form-control" name="daoyan" placeholder="导演" value="${daoyan}">
                    <input type="text" class="form-control" name="min" placeholder="最小分值" value="${min}">
                    <input type="text" class="form-control" name="max" placeholder="最大分支" value="${max}">
                    <button class="btn btn-default"><i class="fa fa-search"></i> 搜</button>
                </form>
            </div>
        </div>


        <a href="/movie/new" class="btn btn-success">添加新数据</a>
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                ${message}
            </div>
        </c:if>
        <h3>共${page.total}条数据</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>电影名称</th>
                    <th>导演</th>
                    <th>上映时间</th>
                    <th>发行时间</th>
                    <th>评分</th>
                    <th>#</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.list}" var="movie">
                    <tr>
                        <td>${movie.title}</td>
                        <td>${movie.daoyan}</td>
                        <td>${movie.releaseyear}</td>
                        <td>${movie.sendtime}</td>
                        <td>${movie.rate}</td>
                        <td>
                            <a href="/movie/${movie.id}/edit"><i class="fa fa-pencil"></i></a>
                            <a href="javascript:;" rel="${movie.id}" class="del"><i class="fa fa-trash text-danger"></i></a>
                        </td>
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
                totalPages: ${page.pages},
                visiblePages: 7,
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?title=${title}&daoyan=${daoyan}&min=${min}&max=${max}&p={{number}}"
            });



            $(".del").click(function () {
                var id = $(this).attr("rel");
                layer.confirm("确定要删除吗?",function(){
                    // movie/1/del
                    window.location.href = "/movie/"+id+"/del";
                });
            });
        });
        
    </script>
</body>
</html>