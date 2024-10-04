<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Add meal</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body>
<div style="margin: 100px">
    <form action="/topjava/meals" method="post">
        <input type="hidden" name="id" value="${meal.id}">
        <div class="form-group row">
            <label for="date_time" class="col-sm-2 col-form-label">Date/time</label>
            <div class="col-sm-5">
                <input id="dt_${meal.id}" type="datetime-local" class="form-control" id="date_time" name="date_time"
                       value="${meal.dateTime}">
            </div>
        </div>
        <div class="form-group row">
            <label for="description" class="col-sm-2 col-form-label">Description</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="description" name="description" value="${meal.description}">
            </div>
        </div>
        <div class="form-group row">
            <label for="calories" class="col-sm-2 col-form-label">Calories</label>
            <div class="col-sm-5">
                <input type="number" class="form-control" id="calories" name="calories" value="${meal.calories}">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Save</button>
        <button onclick="window.history.back()" type="button" class="btn btn-secondary">Cancel</button>
    </form>

</div>
</body>

</html>