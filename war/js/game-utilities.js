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
var playerTeam = 1;

var team1score = 0 ;
var team2score = 0 ;

var doRefresh = true;
var form = $('#ajaxform'); // id of form tag
var points = document.getElementById("points");
var plunkAmount = parseInt(document.getElementById("plunkAmount").value);

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

$('.modal').click(function (event)
{
    //&& !$(event.target).is('.modal-content')
    if(!$(event.target).closest('.modal-content').length ) {
        $('.modal').modal('hide');
        doRefresh = true;
    }
});

//Refresh without reload function
$(document).ready(
    function() {
        setInterval(function () {
            if(doRefresh == true) {
                if(points.value == "finish"){
                    location.reload(true);
                }
                points.value = "";
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

function openModal(card) {
    doRefresh = false;
    $(".selected").removeClass("selected") ;
    card.classList.add("selected") ;

    $('#statModal').modal({backdrop: 'static', keyboard: false});
    $('#statModal').modal('show');

    // ${Name},${Username},${Score},${Plunks}
    playerInfo = $(".selected").attr("value").split(',');

    playerName = playerInfo[0];
    playerUsername = playerInfo[1] ;
    playerScore = parseInt(playerInfo[2]);
    playerPlunk = parseInt(playerInfo[3]);
    playerTeam = parseInt(playerInfo[4]);

    team1score = parseInt($("#team1-score").attr("data-score"));
    team2score = parseInt($("#team2-score").attr("data-score"));

    //sorry zach
    var playerFocus = document.getElementById("playerInput");
    playerFocus.value = card.id;

    updateModal();
}

function addPoint() {
    playerScore++;
    points.value = 1;

    if(playerTeam == 1) {
        team1score++ ;
    }
    else {
        team2score++;
    }

    updateModal();
}

function delPoint() {
    playerScore--;
    points.value = -1;

    if(playerTeam == 1) {
        team1score--;
    }
    else {
        team2score--;
    }

    updateModal();
}

function addPlunk() {
    playerPlunk += 1;
    playerScore = eval(parseInt(playerScore) + parseInt(plunkAmount));
    points.value = '$' + plunkAmount;

    if(playerTeam == 1) {
        team1score += plunkAmount ;
    }
    else {
        team2score += plunkAmount;
    }

    updateModal();
}

function delPlunk() {
    playerPlunk -= 1;
    playerScore = eval(parseInt(playerScore) - parseInt(plunkAmount));
    points.value = '$' + -plunkAmount;

    if(playerTeam == 1) {
        team1score -= plunkAmount ;
    }
    else {
        team2score -= plunkAmount ;
    }

    updateModal();
}

function updateModal() {
    $('#ModalLongTitle').text(playerName);
    console.log(playerUsername) ;
    $('#modal-username').text('@' + playerUsername);
    $('#modalScore').text(playerScore);
    $('#modalPlunk').text(playerPlunk);

    $( ".selected .points" ).text(playerScore);
    $( ".selected .plunks" ).text(playerPlunk);

    $('#team1-score-text').text(team1score);
    $('#team2-score-text').text(team2score);

    rebuildVal();
}

function rebuildVal() {
    var rebuild = playerName + "," + playerUsername + "," + playerScore + "," + playerPlunk + "," + playerTeam ;
    playerInfo = rebuild ;

    $('.selected').attr("value",rebuild);
    $('#team1-score').attr("data-score",team1score);
    $('#team2-score').attr("data-score",team2score);
}

function updateData(){
    //team 1 score
    $('#team1-score').find('.score-text')[0].innerHTML = t1Score.toString();
    $('#team2-score').find('.score-text')[0].innerHTML = t2Score.toString();
    $('#team1-score').attr("data-score", t1Score.toString());
    $('#team2-score').attr("data-score", t2Score.toString());

    //team 1 p 1
    $('#t1p1Card').find('.points')[0].innerHTML = t1p1Score.toString();
    $('#t1p1Card').find('.plunks')[0].innerHTML = t1p1Plunk.toString();

    //team 1 p 2
    $('#t1p2Card').find('.points')[0].innerHTML = t1p2Score.toString();
    $('#t1p2Card').find('.plunks')[0].innerHTML = t1p2Plunk.toString();

    //team 2 p 1
    $('#t2p1Card').find('.points')[0].innerHTML = t2p1Score.toString();
    $('#t2p1Card').find('.plunks')[0].innerHTML = t2p1Plunk.toString();

    //team 2 p 2
    $('#t2p2Card').find('.points')[0].innerHTML = t2p2Score.toString();
    $('#t2p2Card').find('.plunks')[0].innerHTML = t2p2Plunk.toString();
}

function finishGame() {
    points.value = "finish";
    console.log(points.value);
        $.ajax({
            type: form.attr('method'),  //post method
            url: form.attr('action'), //ajaxformexample url
            data: form.serialize(), // serialize input data values
            success: function (data) {
                var result=data;
                $('#content').html(result); //showing result

            }
        });
    window.location.href = "/view";
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

function openFinishModal() {
    doRefresh = false;

    $('#finishModal').modal({backdrop: 'static', keyboard: false});
    $('#finishModal').modal('show');
}

function closeFinishModal() {
    doRefresh = true;
}