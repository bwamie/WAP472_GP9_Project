/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function intitialTweetLoad() {
    fetchTweets("documentary");
}

function loadMap(data) {
    $('#maincontent').html(data);
}

$(function () {
    loadTweetsMenu();
    intitialTweetLoad();
    $('.loadtrends').click(function () {
        $.get("trends")
                .done(loadMap)
                .fail(ajaxFailure);
    });
    $('.loadtweets').click(function () {
        loadTweetsMenu();
        intitialTweetLoad();
    });

    $('.mitem').click(function () {
        addRemoveClass(this);
    });
    $('#docdocumentaryTag').click(function () {
        fetchTweets("documentary");
    });
    $('#actionTag').click(function () {
        fetchTweets("action");
    });
    $('#adventureTag').click(function () {
        fetchTweets("adventure");
    });
    $('#comedyTag').click(function () {
        fetchTweets("comedy");
    });

});

function getTrends(lat, lng) {
    $.ajax({
        url: "trends",
        type: "post",
        data: {lat: lat, lng: lng},
        success: printTrends,
        error: ajaxFailure
    });
}

function printTrends(json) {
    var str = "<ol class='trendslist'>";
    $.each(json, function (i, j) {
        str += "<li><a href='" + j.url + "' target='blank'>" + j.name + "</a></li>";
    });
    $('#trendsdiv').empty().html(str + "</ol>");
}

function fetchTweets(tag) {
    $.get('tweets', {
        'tag': tag
    })
            .done(loadJson)
            .fail(ajaxFailure);
}

function loadJson(json) {
    var str = "";
    $.each(json, function (i, j) {
        var url = "https://twitter.com/" + j.user
                + "/status/" + j.id;
        str += '<div class="contentbody">' +
                '<div class="tweet">' +
                '<div>' +
                '<span>' +
                '<img src="' + j.image + '" />' +
                '</span>' +
                '<span>' + j.user + '</span>' +
                '</div>' +
                '<div>' +
                '<a href="' + url + '" target="blank">' + j.body + '</a>' +
                '</div>' +
                '</div>';
    });
    $('#contentbody').html(str);
}

function makeImg(src) {
    return $('<img>', {
        'src': src,
    });
}

function makeTitle(name) {
    return $('<h1>', {
        'html': name,
        'alt': name
    });
}

function makeIngList(img, title, inc) {
    var list = $('<ul>');
    $.each(inc, function (i2, e) {
        list.append(
                $('<li>', {
                    'html': e
                })
                );
    });
    $('#main').append(img).append(title).append(list);
}

function ajaxFailure(xhr, status, exception) {
    console.log(xhr, status, exception);
}

function addRemoveClass(e) {
    $('#documentaryTag').removeClass("selectedTag");
    $('#actionTag').removeClass("selectedTag");
    $('#adventureTag').removeClass("selectedTag");
    $('#comedyTag').removeClass("selectedTag");
    $(e).addClass("selectedTag");
}

function loadTweetsMenu() {
    var str = '<div class="contentmenu">' +
            '<ul>' +
            '<li><a href="#" onclick="addRemoveClass(this)" id="documentaryTag" class="selectedTag mitem" >Documentary</a></li>' +
            '<li><a href="#" onclick="addRemoveClass(this)" id="actionTag" class="mitem">Action</a></li>' +
            '<li><a href="#" onclick="addRemoveClass(this)" id="adventureTag" class="mitem">Adventure</a></li>' +
            '<li><a href="#" onclick="addRemoveClass(this)" id="comedyTag" class="mitem">Comedy</a></li>' +
            '</ul>' +
            '</div>' +
            '<div id="contentbody"></div>';
    $('#maincontent').html(str);
}

function createLoadingMesage() {
    var throbber = $('<img>').attr('src', 'static/images/loading.gif');
    var div = $('<div>', {
        'text': 'Loading...',
        'id': 'loading'
    })
            .append(throbber)
            .hide()
            .ajaxStart(function () {
                $(this).show();
                $('#boot').empty();
            }).ajaxStop(function () {
        $(this).hide();
    });

    $('#container').append(div);
}