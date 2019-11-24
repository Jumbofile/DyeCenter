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
