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
var back = document.getElementById("back-btn");
var backValue = document.getElementById("backValue");

//teamvalues
var teamString;
var team1Value = document.getElementById("team1value");
var team2Value = document.getElementById("team2value");

//game values
var p1Name ;
var p2Name ;
var p3Name ;
var p4Name ;

var p1User ;
var p2User ;
var p3User ;
var p4User ;

//Refresh without reload function
$(document).ready(
    function() {
        setInterval(function () {
            if(doRefresh == true) {
                if(backValue.value == "true"){
                    location.reload(true);
                }
                $.ajax({
                    type: form.attr('method'),  //post method
                    url: form.attr('action'), //ajaxformexample url
                    dataType: 'json',
                    data: form.serialize(), // serialize input data values
                    success:
                        function(data, textStatus, jqXHR) {
                            console.log(data);
                            // var dataString = data.split(',');
                            p1Name     = data.p1.name;
                            p1User     = data.p1.user;

                            p2Name     = data.p2.name;
                            p2User     = data.p2.user;

                            p3Name     = data.p3.name;
                            p3User     = data.p3.user;

                            p4Name     = data.p4.name;
                            p4User     = data.p4.user;

                            teamString = data.teamString.split(',');
                            console.log(teamString);

                            updateData();
                        }
                });

                return false; // not refreshing page
            }
        }, 2000);  //Delay here = 5 seconds
    });

function updateData(){
    $('#player1').text(p1Name);
    $('#player2').text(p2Name);
    $('#player3').text(p3Name);
    $('#player4').text(p4Name);

    var playerUser  = [p1User, p2User, p3User, p4User] ;
    var team1btns = $('body').find('.team1');
    var team2btns = $('body').find('.team2')
    var nonebtns = $('body').find('.none') ;

    for(var i = 0; i < 4; i++) {
        $(team1btns[i]).val("team1," + playerUser[i]) ;
        $(team2btns[i]).val("team2," + playerUser[i]) ;
        $(nonebtns[i]).val("none," + playerUser[i]) ;
    }

    //update the value boxes
    team1Value.value = teamString[0] + ',' + teamString[1];
    team2Value.value = teamString[2] + ',' + teamString[3];
    //player 1 checkboxes
    var p1b = document.getElementById("p1b");
    var p1r = document.getElementById("p1r");
    var p1u = document.getElementById("p1u");

    //player 2 checkboxes
    var p2b = document.getElementById("p2b");
    var p2r = document.getElementById("p2r");
    var p2u = document.getElementById("p2u");

    //player 3 checkboxes
    var p3b = document.getElementById("p3b");
    var p3r = document.getElementById("p3r");
    var p3u = document.getElementById("p3u");

    //player 4 checkboxes
    var p4b = document.getElementById("p4b");
    var p4r = document.getElementById("p4r");
    var p4u = document.getElementById("p4u");

    //player 1
    if(teamString[0] == p1User || teamString[1] == p1User){
        p1b.checked = true;
    }else if(teamString[2] == p1User || teamString[3] == p1User){
        p1r.checked = true;
    }else{
        p1u.checked = true;
    }

    //player 2
    if(teamString[0] == p2User || teamString[1] == p2User){
        p2b.checked = true;
    }else if(teamString[2] == p2User || teamString[3] == p2User){
        p2r.checked = true;
    }else{
        p2u.checked = true;
    }

    //player 3
    if(teamString[0] == p3User || teamString[1] == p3User){
        p3b.checked = true;
    }else if(teamString[2] == p3User || teamString[3] == p3User){
        p3r.checked = true;
    }else{
        p3u.checked = true;
    }

    //player 4
    if(teamString[0] == p4User || teamString[1] == p4User){
        p4b.checked = true;
    }else if(teamString[2] == p4User || teamString[3] == p4User){
        p4r.checked = true;
    }else{
        p4u.checked = true;
    }
}

function dropOut(){
    backValue.value = 'true';
    window.location.href = "/dashboard";
}

function startGame(){
    //idk what im gonna do here, we will figure it out
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
        if(!t1p1){
            console.log(t1p1);
            t1p1 = info[1];
            if(t2p1 == info[1]){
                t2p1 = null;
            }
            if(t2p2 == info[1]){
                t2p2 = null;
            }
        }else if(!t1p2){
            t1p2 = info[1];
            if(t2p1 == info[1]){
                t2p1 = null;
            }
            if(t2p2 == info[1]){
                t2p2 = null;
            }

        }
    }

    //team 2
    if(info[0] == 'team2'){
        if(!t2p1){
            t2p1 = info[1];
            if(t1p1 == info[1]){
                t1p1 = null;
            }
            if(t1p2 == info[1]){
                t1p2 = null;
            }
        }else if(!t2p2){
            t2p2 = info[1];
            if(t1p1 == info[1]){
                t1p1 = null;
            }
            if(t1p2 == info[1]){
                t1p2 = null;
            }

        }

    }
    console.log(t1p1);
    console.log(t1p2);
    console.log(t2p1);
    console.log(t2p2);
    //disable
    if(t1p1 && t1p2){
        var inputs = document.querySelectorAll('.team1');

        for (var i = 0; i < inputs.length; i++) {
            inputs[i].disabled = true;
        }
    }

    if(t2p1 && t2p2){
        var inputs = document.querySelectorAll('.team2');

        for (var i = 0; i < inputs.length; i++) {
            inputs[i].disabled = true;
        }
    }

    //re eanble
    if(!t1p1 || !t1p2){
        var inputs = document.querySelectorAll('.team1');

        for (var i = 0; i < inputs.length; i++) {
            inputs[i].disabled = false;
        }
    }

    if(!t2p1 || !t2p2){
        var inputs = document.querySelectorAll('.team2');

        for (var i = 0; i < inputs.length; i++) {
            inputs[i].disabled = false;
        }
    }
    var t1p1temp;
    var t1p2temp;
    var t2p1temp;
    var t2p2temp;

    if(!t1p1){
        t1p1temp = '<<empty>>';
    }else{
        t1p1temp = t1p1;
    }
    if(!t1p2){
        t1p2temp = '<<empty>>';
    }else{
        t1p2temp = t1p2;
    }
    if(!t2p1){
        t2p1temp = '<<empty>>';
    }else{
        t2p1temp = t2p1;
    }
    if(!t2p2){
        t2p2temp = '<<empty>>';
    }else{
        t2p2temp = t2p2;
    }

    console.log(team1Value.value = t1p1temp +',' + t1p2temp);
    console.log(team2Value.value = t2p1temp +',' + t2p2temp);


    //AJAX post
    form.submit(function () {

        $.ajax({
            type: form.attr('method'),  //post method
            url: form.attr('action'), //ajaxformexample url
            data: $('#ajaxGet').serialize(), // serialize input data values
            success: function (data) {
                console.log(data);
            }
        });

        return false; // not refreshing page

    });



}
// //AJAX post
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

// $(function(){
//     function getData() {
//         var dataToBeSent  = {
//             uName : $("#userName").val() , //
//             passwd: $("#password").val()
//         }; // you can change parameter name
//
//         $.ajax({
//             url : 'getDataServlet', // Your Servlet mapping or JSP(not suggested)
//             data :dataToBeSent,
//             type : 'POST',
//             dataType : 'html', // Returns HTML as plain text; included script tags are evaluated when inserted in the DOM.
//             success : function(response) {
//                 $('#outputDiv').html(response); // create an empty div in your page with some id
//             },
//             error : function(request, textStatus, errorThrown) {
//                 alert(errorThrown);
//             }
//         });
//     }
//
// });
