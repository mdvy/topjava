function delete_meal(id) {
    let path = window.location.href;
    let relPath =  path.substring(window.location.origin.length);
    $.ajax({
        url: relPath + "?id=" + id,
        type: "DELETE",
        success: function () {
            window.location = relPath;
        }
    });
}