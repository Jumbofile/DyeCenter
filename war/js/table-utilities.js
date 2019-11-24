
var gameArr = $('#game-btn-container').children("button");


for(var i = 0; i < gameArr.length; i++) {
    var status = gameArr[i].getAttribute("data-status") ;
    var time = gameArr[i].getAttribute("data-time") ;
    var usernames = gameArr[i].getAttribute("data-usernames").split(",") ;
    var names = gameArr[i].getAttribute("data-names").split(",") ;
    var scores = gameArr[i].getAttribute("data-scores").split(",") ;
    var plunks = gameArr[i].getAttribute("data-plunks").split(",") ;
    var winningTeam = parseInt(gameArr[i].getAttribute("data-leader"));
    var gamehash = gameArr[i].getAttribute("data-hash");

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

    if(status == 1) {
        if(winningTeam == 1) {
            $(gameArr[i]).addClass("t1-win") ;
            statusString = "Blue Team Wins" ;
            $($(gameArr[i]).find(".t1")).addClass("winning-team");
        }
        else if(winningTeam == 2) {
            $(gameArr[i]).addClass("t2-win") ;
            statusString = "Red Team Wins" ;
            $($(gameArr[i]).find(".t2")).addClass("winning-team");
        }
        else {
            $(gameArr[i]).addClass("draw") ;
            statusString = "Draw" ;
        }

    }

    else if(status == -1) {
        $(gameArr[i]).addClass("pre-game") ;
        statusString = "Waiting for players..." ;
    }

    else {
        $(gameArr[i]).addClass("in-progress");
    }


    gameArr[i].querySelector('.game-card-header').innerHTML = "<span>" + date.toLocaleString() + "<span class='game-hash-span'>" + gamehash + "</span></span><span>" + statusString + "</span>";
    gameArr[i].querySelector('#team1-score').innerHTML = t1score;
    gameArr[i].querySelector('#team2-score').innerHTML = t2score;
    for(var x = 0; x < names.length; x++) {
        var name = names[x].trim();
        if(name == "null") {
            name = "TBD...";
        }
        gameArr[i].querySelector('#p' + (x+1) + '-name').innerHTML = name;
        gameArr[i].querySelector('#p' + (x+1) + '-points').innerHTML = "<i class='icon fas fa-dice-five fa-1x'></i>\t" + scores[x].trim() ;
        gameArr[i].querySelector('#p' + (x+1) + '-plunks').innerHTML = "<i class='icon fas fa-beer fa-1x'></i>\t" + plunks[x].trim() ;
    }
}
