<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>

    <title>Meal list</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
    <script>
        function filter() {
            let path = window.location.href;
            let relPath = path.substring(window.location.origin.length);
            $.ajax({
                url: relPath,
                type: "get",
                data: {
                    action: 'filter',
                    startDate: $('#start_date').val(),
                    endDate: $('#end_date').val(),
                    startTime: $('#start_time').val(),
                    endTime: $('#end_time').val(),
                },
                success: function (response) {
                    $("#jsp_container").html(response);
                }
            });
        }
    </script>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    start date<input id="start_date" type="date" name="startDate" required>
    end date<input id="end_date" type="date" name="endDate" required>
    <br>
    <br>
    start time<input id="start_time" type="time" name="startTime" required>
    end time<input id="end_time" type="time" name="endTime" required>
    <input type="button" value="Apply filter" id="submit_date_time" onclick="filter()"/>
    <br>

    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <div id="jsp_container">
        <jsp:include page="mealsTable.jsp"/>
    </div>
</section>
</body>
</html>