
var gameArr = $('#gameList').children("button");


for(var i = 0; i < gameArr.length; i++) {
    var status = gameArr[i].getAttribute("data-status") ;
    var time = gameArr[i].getAttribute("data-time") ;
    var usernames = gameArr[i].getAttribute("data-usernames").split(",") ;
    var names = gameArr[i].getAttribute("data-names").split(",") ;
    var scores = gameArr[i].getAttribute("data-scores").split(",") ;
    var plunks = gameArr[i].getAttribute("data-plunks").split(",") ;

    console.log(status);
    console.log(time);
    console.log(usernames);
    console.log(names);
    console.log(scores);
    console.log(plunks);

    var date = new Date(time + "UTC");

    gameArr[i].querySelector('.game-card-header').innerHTML = date.toLocaleString();
    for(var x = 0; x < names.length; x++) {
        gameArr[i].querySelector('.p'+ (x+1)).innerHTML = names[x].trim();
        gameArr[i].querySelector('.p'+ (x+1)).innerHTML += scores[x].trim();
        gameArr[i].querySelector('.p'+ (x+1)).innerHTML += plunks[x].trim();
    }
}
