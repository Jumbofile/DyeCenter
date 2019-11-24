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

var t1p1 = null;
var t1p2 = null;
var t2p1 = null;
var t2p2 = null;

var doRefresh = true;
var form = $('#ajaxform'); // id of form tag

//game values
var p1;
var p2;
var p3;
var p4;

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
                            p1     = dataString[0];
                            p2    = dataString[1];
                            p3  = dataString[2];
                            p4   = dataString[3];

                            updateData();
                        },
                    dataType: 'text'
                });

                return false; // not refreshing page
            }
        }, 1000);  //Delay here = 5 seconds
    });

function updateData(){
    $('#player1').text(p1.toString());
    $('#player2').text(p2.toString());
    $('#player3').text(p3.toString());
    $('#player4').text(p4.toString());
}

function teamSelect(button){
    console.log(button);
    var info = button.value.split(',');
    //undecided
    if(info[0] == 'none') {
        if(t1p1 == info[1]){
            t1p1 = null;
        }
        if(t1p2 == info[1]){
            t1p2 = null;
        }
        if(t2p1 == info[1]){
            t2p1 = null;
        }
        if(t2p2 == info[1]){
            t2p2 = null;
        }
    }
    //team 1
    if(info[0] == 'team1'){
        if(t1p1 == null){
            t1p1 = info[1];
            if(t2p1 == info[1]){
                t2p1 = null;
            }
            if(t2p2 == info[1]){
                t2p2 = null;
            }
        }else if(t1p2 == null){
            t1p2 = info[1];
            if(t2p1 == info[1]){
                t2p1 = null;
            }
            if(t2p2 == info[1]){
                t2p2 = null;
            }
            var inputs = document.querySelectorAll('.team1');

            for (var i = 0; i < inputs.length; i++) {
                inputs[i].disabled = true;
            }
        }
    }

    //team 2
    if(info[0] == 'team2'){
        if(t2p1 == null){
            t2p1 = info[1];
            if(t1p1 == info[1]){
                t1p1 = null;
            }
            if(t1p2 == info[1]){
                t1p2 = null;
            }
        }else if(t2p2 == null){
            t2p2 = info[1];
            if(t1p1 == info[1]){
                t1p1 = null;
            }
            if(t1p2 == info[1]){
                t1p2 = null;
            }
            var inputs = document.querySelectorAll('.team2');

            for (var i = 0; i < inputs.length; i++) {
                inputs[i].disabled = true;
            }
        }

    }
    if(t1p1 == null || t1p2 == null){
        var inputs = document.querySelectorAll('.team1');

        for (var i = 0; i < inputs.length; i++) {
            inputs[i].disabled = false;
        }
    }

    if(t2p1 == null || t2p2 == null){
        var inputs = document.querySelectorAll('.team2');

        for (var i = 0; i < inputs.length; i++) {
            inputs[i].disabled = false;
        }
    }

    console.log(t1p1);
    console.log(t1p2);
    console.log(t2p1);
    console.log(t2p2);

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
