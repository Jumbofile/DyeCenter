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
          item = item.split('^') ;
          $('#tableDropdown').append("<button name='tid' type='submit' value=" + item[1] + "  class='collapse-item'>" + item[0]+ "</button>") ;
     }) ;

     // for(var i = 0; i < names.length) {
     //      console.log(tblName) ;
     //      $('tableDropdown').append("<a class='collapse-item'>" + tblName + "</a>")
     // }

}

function goToTable(elm) {
     var tid = $(elm).val();
     //console.log(tid);
     // $('#tidDummy').val(tid) ;
     // $('#masterForm').submit();
}