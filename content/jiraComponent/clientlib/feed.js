
var query = window.location.search.substring(1);
if (query.length === 0){
    document.getElementById("myBtn2").disabled = true;
}

var oldStatus = document.getElementById('oldStatus').value;
console.log(oldStatus);
if (oldStatus === "Open"){
    document.getElementById("statusUpdate").getElementsByTagName('option')[0].selected = 'selected';
}
else if(oldStatus === "Working in progress") {
    document.getElementById("statusUpdate").getElementsByTagName('option')[1].selected = 'selected';
}
else {
    document.getElementById("statusUpdate").getElementsByTagName('option')[2].selected = 'selected';
}

var user = document.getElementById('role').value;

if (user != "Admin"){
    document.getElementById('myBtn3').style.display = 'none';
}


console.log(user);

// Get the modal
var modal = document.getElementById("myModal");
var modal2 = document.getElementById("myModal2");


// Get the button that opens the modal
var btn = document.getElementById("myBtn");
var btn2 = document.getElementById("myBtn2");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];
var span2 = document.getElementsByClassName("close2")[0];


// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

btn2.onclick = function() {
    modal2.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

span2.onclick = function() {
    modal2.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

window.onclick = function(event) {
    if (event.target === modal2) {
        modal2.style.display = "none";
    }
}

function myFunction() {
    var x = document.getElementById("myTopnav");
    if (x.className === "navbar") {
        x.className += " responsive";
    } else {
        x.className = "navbar";
    }
}
