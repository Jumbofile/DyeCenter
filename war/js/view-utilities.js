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
var doRefresh = true;
var form = $('#ajaxform'); // id of form tag
var points = document.getElementById("points");
var plunkAmount= document.getElementById("plunkAmount").value;

//game values
var t1Score;
var t2Score;
var t1p1Score;
var t1p2Score;
var t2p1Score;
var t2p2Score;
var t1p1Plunk;
var t1p2Plunk;
var t2p1Plunk;
var t2p2Plunk;
var t1p1WL;
var t1p2WL;
var t2p1WL;
var t2p2WL;

//header text
var teamWon;
var captionText;


$('.modal').click(function (event)
{
    //&& !$(event.target).is('.modal-content')
    if(!$(event.target).closest('.modal-content').length ) {
        $('#statModal').modal('hide');
        doRefresh = true;
    }
});

//Refresh without reload function
$(document).ready(
    function() {
        setInterval(function () {
            if(doRefresh == true) {
                $.ajax({
                    type: form.attr('method'),  //post method
                    url: form.attr('action'), //ajaxformexample url
                    data: form.serialize(), // serialize input data values
                    success:
                        function(data, textStatus, jqXHR) {
                            console.log(data);
                            var dataString = data.split(',');
                            t1Score     = dataString[0];
                            t2Score     = dataString[1];
                            t1p1Score   = dataString[2];
                            t1p2Score   = dataString[3];
                            t2p1Score   = dataString[4];
                            t2p2Score   = dataString[5];
                            t1p1Plunk   = dataString[6];
                            t1p2Plunk   = dataString[7];
                            t2p1Plunk   = dataString[8];
                            t2p2Plunk   = dataString[9];
                            t1p1WL      = dataString[10];
                            t1p2WL      = dataString[11];
                            t2p1WL      = dataString[12];
                            t2p2WL      = dataString[13];
                            teamWon     = dataString[14];
                            captionText = dataString[15];

                            updateData();
                        },
                    dataType: 'text'
                });

                return false; // not refreshing page
            }
        }, 1000);  //Delay here = 5 seconds
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

function updateData(){
    //team 1 score
    $('#team1-score').find('.score-text')[0].innerHTML = t1Score.toString();
    $('#team2-score').find('.score-text')[0].innerHTML = t2Score.toString();

    //captions
    $('#teamWon').text(teamWon.toString());
    $('#caption').text(captionText.toString());

    //team 1 p 1
    $('#t1p1Card').find('.points')[0].innerHTML = t1p1Score.toString();
    $('#t1p1Card').find('.plunks')[0].innerHTML = t1p1Plunk.toString();
    $('#t1p1Card').find('.WL')[0].innerHTML = t1p1WL.toString() + '<sup class="small">%</sup>';
    $($('#t1p1Card').find('.progress')[0]).attr("data-value", t1p1WL.toString());

    //team 1 p 2
    $('#t1p2Card').find('.points')[0].innerHTML = t1p2Score.toString();
    $('#t1p2Card').find('.plunks')[0].innerHTML = t1p2Plunk.toString();
    $('#t1p2Card').find('.WL')[0].innerHTML = t1p2WL.toString() + '<sup class="small">%</sup>';
    $($('#t1p2Card').find('.progress')[0]).attr("data-value", t1p2WL.toString());

    //team 2 p 1
    $('#t2p1Card').find('.points')[0].innerHTML = t2p1Score.toString();
    $('#t2p1Card').find('.plunks')[0].innerHTML = t2p1Plunk.toString();
    $('#t2p1Card').find('.WL')[0].innerHTML = t2p1WL.toString() + '<sup class="small">%</sup>';
    $($('#t2p1Card').find('.progress')[0]).attr("data-value", t2p1WL.toString());

    //team 2 p 2
    $('#t2p2Card').find('.points')[0].innerHTML = t2p2Score.toString();
    $('#t2p2Card').find('.plunks')[0].innerHTML = t2p2Plunk.toString();
    $('#t2p2Card').find('.WL')[0].innerHTML = t2p2WL.toString() + '<sup class="small">%</sup>';
    $($('#t2p2Card').find('.progress')[0]).attr("data-value", t2p2WL.toString());
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

