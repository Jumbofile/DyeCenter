function selectPlayer(elm, id) {
    $(".selected").removeClass("selected");
    elm.classList.add("selected");

    document.getElementById("playerValue").setAttribute("value", id);
}
