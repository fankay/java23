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
    <title></title>
</head>
<body>

    <div class="container">
        <table class="table">
            <thead>
                <tr>
                    <th>电影名称</th>
                    <th>导演</th>
                    <th>上映时间</th>
                    <th>发行时间</th>
                    <th>评分</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${movieList}" var="movie">
                    <tr>
                        <td>${movie.title}</td>
                        <td>${movie.daoyan}</td>
                        <td>${movie.releaseyear}</td>
                        <td>${movie.sendtime}</td>
                        <td>${movie.rate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>