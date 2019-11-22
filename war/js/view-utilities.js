// function selectPlayer(elm, id) {
//     $(".selected").removeClass("selected");
//     elm.classList.add("selected");
//
//     document.getElementById("playerValue").setAttribute("value", id);
// }

var playerInfo = "";
var playerName = "";
var playerUsername = "" ;
var playerScore = 0;
var playerPlunk = 0;
var doRefresh = false;
var form = $('#ajaxform'); // id of form tag
var points = document.getElementById("points");
var plunkAmount= document.getElementById("plunkAmount").value;

$('.modal').click(function (event)
{
    //&& !$(event.target).is('.modal-content')
    if(!$(event.target).closest('.modal-content').length ) {
        $('#statModal').modal('hide');
        doRefresh = true;
    }
});

$(function() {

    $(".progress").each(function() {

        var value = $(this).attr('data-value');
        var left = $(this).find('.progress-left .progress-bar');
        var right = $(this).find('.progress-right .progress-bar');

        if (value > 0) {
            if (value <= 50) {
                right.css('transform', 'rotate(' + percentageToDegrees(value) + 'deg)')
            } else {
                right.css('transform', 'rotate(180deg)')
                left.css('transform', 'rotate(' + percentageToDegrees(value - 50) + 'deg)')
            }
        }

    })
    function percentageToDegrees(percentage) {
        return percentage / 100 * 360
    }

});


function addPoint() {
    playerScore += 1;
    points.value = 1;

    updateModal();
}

function delPoint() {
    playerScore -= 1;
    points.value = -1;

    updateModal();
}

function addPlunk() {
    playerPlunk += 1;
    playerScore = eval(parseInt(playerScore) + parseInt(plunkAmount));
    points.value = plunkAmount;

    updateModal();
}

function delPlunk() {
    playerPlunk -= 1;
    playerScore = eval(parseInt(playerScore) - parseInt(plunkAmount));
    points.value = -plunkAmount;

    updateModal();
}

function updateModal() {
    $('#ModalLongTitle').text(playerName);
    $('#modalScore').text(playerScore);
    $('#modalPlunk').text(playerPlunk);

    $( ".selected .points" ).text(playerScore);
    $( ".selected .plunks" ).text(playerPlunk);

    rebuildVal();
}

function rebuildVal() {
    var rebuild = playerName + "," + playerUsername + "," + playerScore + "," + playerPlunk ;
    playerInfo = rebuild ;

    $('.selected').attr("value",rebuild);
}

function finishGame() {
    points.value = "finish";
}

//AJAX post
form.submit(function () {

    $.ajax({
        type: form.attr('method'),  //post method
        url: form.attr('action'), //ajaxformexample url
        data: form.serialize(), // serialize input data values
        success: function (data) {
            var result=data;
            $('#content').html(result); //showing result

        }
    });

    return false; // not refreshing page

});

function refreshPage(){
    $.ajax({
        type: 'GET',  //post method
        url: form.attr('action'), //ajaxformexample url
        data: form.serialize(), // serialize input data values
        success: function (data) {
            var result=data;
            $('#content').html(result); //showing result

        }
    });

    return false; // not refreshing page
}