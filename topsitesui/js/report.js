/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var username;
var password;

fetchDomainPort = function(){
    var domainPort = window.location.href.split('/').slice(0, 3).join('/')+'/';
    console.log(domainPort);
    return domainPort;
}

parseWebsites = function(websites){
    var isEmpty = true;
    
    $.each(websites, function(index, website) {
        isEmpty = false;
        var websiteView = "<li style='max-width:800px;list-style-position:inside;border: 10px solid black;'>";
        websiteView = "<div>";
        websiteView += "<h3 style='text-transform:uppercase;'>" + website.website + "</h3>";
        websiteView += "Views: </strong><strong class='viewable-text'>" +
                website.count + "</strong>";
        websiteView += "</div>";
        websiteView += "</li>";
        $("#websiteviewslist").append(websiteView);
    });
    
    $("#websiteviewslist").listview('refresh');
    $("#websiteviewslist").trigger('create');
    
    if(isEmpty){
        $('#websitesfound').html('No Websites data found. Please change the date and try again!');
    }
};

//$(document).bind('pagecreate', '#useradspage', function(){
//    username = window.localStorage.getItem('webviews_username'); //get cookie
//    password = window.localStorage.getItem('webviews_password'); //get cookie  
//});

var getWebsiteViews = function(){
    $('#websiteviewslist').html('');
    $("#websiteviewslist").listview('refresh');
    $("#websiteviewslist").trigger('create');
    
    $('#websitesfound').html('');
    var date = $("#reportinputdate").val();
    date = date.replace("/","-");
    date = date.replace("/","-");
    $.ajax({
        url: fetchDomainPort() + 'topsites/rest/websites/topsites/'+date+'?count=5' ,
        type: 'GET',
        dataType: "json",
        headers: {"Authorization":"Basic " + btoa(username + ":" + password)},
        async: true,
        success: function (result) {
            parseWebsites(result);
            $(".se-pre-con").fadeOut("slow");
        },
        error: function (request,error) {
            
            if(request.status === 401){
                $( "#popupLogin" ).popup( "open" );
            }
        }
    });  
};

$(document).ready(function() { 
    username = window.localStorage.getItem('webviews_username'); //get cookie
    password = window.localStorage.getItem('webviews_password'); //get cookie 
    
     $("#reportinputdate").bind( "focusout", function(event, ui) {
        getWebsiteViews();
    }); 
});
