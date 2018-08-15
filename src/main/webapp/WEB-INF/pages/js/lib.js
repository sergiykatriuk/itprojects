function showAndDismissAlerts(messages) {
    if (messages !== "") {
        var messagesArray = JSON.parse(messages);
        messagesArray.forEach(function (m) {
            var htmlAlert = '<div class="alert alert-' + m.type + '">' + m.message + '</div>';

            // Prepend so that alert is on top, could also append if we want new alerts to show below instead of on top.
            $(".alert-messages").prepend(htmlAlert);

            // Since we are prepending, take the first alert and tell it to fade in and then fade out.
            // Note: if we were appending, then should use last() instead of first()
            $(".alert-messages .alert").first().hide().fadeIn(200).delay(3000).fadeOut(2000, function () {
                $(this).remove();
            });
        })
    }

}

function showNav(item) {

    var nav = $("#nav");
    var div = $("<div />", {
        class: "btn-group mr-2",
        role: "group",
        'aria-label': "groupButton"
    }).appendTo(nav);

    createMenuButton("profile", "Your profile", (item === "profile" ? true : false)).appendTo(div);
    createMenuButton("register", "Register new", (item === "register" ? true : false)).appendTo(div);

}

function createMenuButton(address, text, active) {

    return $("<button />", {
        onclick: "location.href='" + address + "'",
        text: text,
        type: "button",
        class: "btn btn-outline-success" + (active ? " active" : "")
    });
}