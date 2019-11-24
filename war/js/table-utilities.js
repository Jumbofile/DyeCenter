
var gameArr = $('#gameList').children("button");


for(var i = 0; i < gameArr.length; i++) {
    var status = gameArr[i].getAttribute("data-status") ;
    var time = gameArr[i].getAttribute("data-time") ;
    var usernames = gameArr[i].getAttribute("data-usernames").split(",") ;
    var names = gameArr[i].getAttribute("data-names").split(",") ;
    var scores = gameArr[i].getAttribute("data-scores").split(",") ;
    var plunks = gameArr[i].getAttribute("data-plunks").split(",") ;
    var winningTeam = parseInt(gameArr[i].getAttribute("data-leader"));

    var t1p1score = parseInt(scores[0]);
    var t1p2score = parseInt(scores[1]);
    var t2p1score = parseInt(scores[2]);
    var t2p2score = parseInt(scores[3]);

    var t1score = eval(t1p1score + t1p2score);
    var t2score = eval(t2p1score + t2p2score);

    var statusString = "In-Progress...";

    console.log(status);
    console.log(time);
    console.log(usernames);
    console.log(names);
    console.log(scores);
    console.log(plunks);

    var date = new Date(time + "UTC");
    console.log(scores[0]);

    if(status != 0) {
        statusString = "Completed"
        if(winningTeam == 1) {
            $(gameArr[i]).addClass("t1-win") ;
        }
        else if(winningTeam == 2) {
            $(gameArr[i]).addClass("t2-win") ;
        }
        else {
            $(gameArr[i]).addClass("draw") ;
        }

    } else {
        $(gameArr[i]).addClass("in-progress");
    }


    gameArr[i].querySelector('.game-card-header').innerHTML = date.toLocaleString() + " - " + statusString;
    gameArr[i].querySelector('.team1').innerHTML = "Team 1 - " + t1score ;
    gameArr[i].querySelector('.team2').innerHTML = "Team 2 - " + t2score ;
    for(var x = 0; x < names.length; x++) {
        gameArr[i].querySelector('.p'+ (x+1)).innerHTML = names[x].trim();
        gameArr[i].querySelector('.p'+ (x+1)).innerHTML += " Score: "+scores[x].trim();
        gameArr[i].querySelector('.p'+ (x+1)).innerHTML += " Plunks: " + plunks[x].trim();
    }
}
