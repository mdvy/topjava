<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div style="margin: 50px">
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h1>Meals</h1>
    <h5><a href="meals/new">Add meal</a></h5>
    <table class="table">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <tr style="color: ${meal.excess ? 'red' : 'green'}">
                <td>${fn:replace(meal.dateTime, "T", " ")}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <button type="button" class="btn btn-primary" onclick="location.href = 'meals/edit?id=${meal.id}'">
                        Update
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-warning" onclick="delete_meal(${meal.id})">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="js/scripts.js"></script>
</html>