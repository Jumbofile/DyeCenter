
var gameArr = $('#gameList').children("button");


for(var i = 0; i < gameArr.length; i++) {
    var status = gameArr[i].getAttribute("data-status") ;
    var time = gameArr[i].getAttribute("data-time") ;
    var usernames = gameArr[i].getAttribute("data-usernames") ;
    var names = gameArr[i].getAttribute("data-names") ;
    var scores = gameArr[i].getAttribute("data-scores") ;
    var plunks = gameArr[i].getAttribute("data-plunks") ;

    console.log(status);
    console.log(time);
    console.log(usernames.split(','));
    console.log(names);
    console.log(scores);
    console.log(plunks);

    var date = new Date(time + "UTC");

    gameArr[i].querySelector('.game-card-header').innerHTML = date.toLocaleString();
    for(var x = 0; x < names.length; x++) {
        // gameArr[i].querySelector('.p'+ x).innerHTML = names.split(',')[x];
        // gameArr[i].querySelector('.p'+ x).innerHTML += scores.split(',')[x];
        // gameArr[i].querySelector('.p'+ x).innerHTML += plunks.split(',')[x];
    }
}
