function delete_meal(id) {
    $.ajax({
        url: "/topjava/meals?id=" + id,
        type: "DELETE",
        success: function () {
            window.location = "/topjava/meals";
        }
    });
}