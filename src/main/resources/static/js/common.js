function onlyNumber(id){
    var input = document.getElementById(id);
    input.value = input.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
}