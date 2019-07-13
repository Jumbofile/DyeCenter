// console.log("here1") ;
// $(document).ready(function(){
//      loadTableDropdown("test") ;
// });


//
function loadTableDropdown(names){
     //console.log(names);
     names = names.split(',') ;
     //console.log(names);

     names.forEach(function(item, index){
          console.log(item, index) ;
          $('#tableDropdown').append("<a class='collapse-item'>" + item + "</a>") ;
     }) ;

     // for(var i = 0; i < names.length) {
     //      console.log(tblName) ;
     //      $('tableDropdown').append("<a class='collapse-item'>" + tblName + "</a>")
     // }

}