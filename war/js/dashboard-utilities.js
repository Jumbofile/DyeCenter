
function loadTableDropdown(names){
     //console.log(names);
     names = names.split(',') ;
     //console.log(names);

     names.forEach(function(item, index){
          console.log(item, index) ;
          item = item.split('^') ;
          $('#tableDropdown').append("<button name='tid' type='submit' value=" + item[1] + "  class='collapse-item btn'>" + item[0]+ "</button>") ;
     }) ;

}

function logout() {
    
}
