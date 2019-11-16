
var gameArr = $('#gameList').children("button");

console.log(gameArr[0]) ;

console.log(gameArr[0].children);

console.log(gameArr.length) ;

for(var i = 0; i < gameArr.length; i++) {
    var status = gameArr[i].getAttribute("data-status") ;
    var time = gameArr[i].getAttribute("data-time") ;
    var usernames = gameArr[i].getAttribute("data-usernames") ;
    var names = gameArr[i].getAttribute("data-names") ;
    var scores = gameArr[i].getAttribute("data-scores") ;
    var plunks = gameArr[i].getAttribute("data-plunks") ;

    var date = new Date(time + "UTC");
    console.log(date.toString());

    console.log(time);
    console.log(gameArr[i].querySelector('.game-card-header'));
    gameArr[i].querySelector('.game-card-header').innerHTML = date.toLocaleString();
}
