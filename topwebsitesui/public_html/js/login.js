/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security
var username;
var password;

$(document).ready(function() {
    $("#userlogin").bind( "click", function(event, ui) {
        username = $("#username").val();
        password= $("#password").val();
        verifyLogin();
    });
});

fetchDomainPort = function(){
    var domainPort = window.location.href.split('/').slice(0, 3).join('/')+'/';
    console.log(domainPort);
    return domainPort;
}

fetchUIUrl = function(){
    var domainPort = window.location.href.split('/').slice(0, 4).join('/')+'/';
    console.log(domainPort);
    return domainPort;
}

verifyLogin = function(){
    $.ajax({
        url: fetchDomainPort() + 'Top5Websites/rest/user/login' ,
        type: 'GET',
        headers: {"Authorization" : "Basic " + btoa(username + ":" + password)},
        dataType: "json",
        crossDomain: true,
        async: true,
        success: function (result) {
            //set cookie on successful login
            setUserCookies(result);
	    window.location.href = fetchUIUrl() + 'report.html';
        },
        error: function (request,error) {
            $('#loginfail').css({'display': 'inline-block'});
            //console.log(request);
        }
    });  
}

setUserCookies = function(result){
    $('#loginfail').css({'display': 'none'});
    if(result !== null && (typeof result !== 'undefined')){
        //console.log("Success!!!");
        window.localStorage.setItem("webviews_username", username); //set cookie
        window.localStorage.setItem("webviews_password", password); //set cookie
    }
}

