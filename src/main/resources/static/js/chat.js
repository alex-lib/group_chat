$(function() {
    let getMessageElement = function(message) {
        let item = ($('<div class="message-item"></div>'));
        let header = ($('<div class="message-header"></div>'));
        header.append($('<div class="dateTime">' + message.dateTime + '</div>'));
        header.append($('<div class="userName">' + message.userName + '</div>'));
        let textElement = ($('<div class="message-text"></div>'));
        textElement.text(message.text);
        item.append(header, textElement)
        return item;
    };

    let getUser = function(user) {
        let item = ($('<div class="user-item"></div>'));
        item.append($('<div class="name">' + user.name + '</div>'));
        return item;
    };

    let updateUsers = function() {
    $('.users-list').html('<i>There are no users yet</i>');
        $.get('/user', {}, function(response) {
        if(response.length == 0) {
                return;
        }
        $('.users-list').html('<i>Users:</i>');
        for(i in response) {
            let element = getUser(response[i]);
            $('.users-list').append(element);
            }
        });
    };

    let updateMessages = function() {
        $('.messages-list').html('<i>There are no messages yet</i>');
        $.get('/message', {}, function(response) {
            if(response.length == 0) {
                return;
            }
            $('.messages-list').html('<i>Messages:</i>');
            for(i in response) {
                let element = getMessageElement(response[i]);
                $('.messages-list').append(element);
            }
        });
    };

    let initApplication = function() {
        $('.messages-and-users').css({display: 'flex'});
        $('.controls').css({display: 'flex'});
        $('.send-message').on('click', function() {
            let message = $('.new-message').val();
            if (!message) {
                    alert('Please enter a message');
                    return;
                }
            $.post('/message', {message: message}, function(response) {
                if(response.result) {
                    $('.new-message').val('');
                } else {
                    alert('Error is: ' + response.message);
                }
            });
        });
        setInterval(updateUsers, 2000);
        setInterval(updateMessages, 2000);
    };

    let registerUser = function(name) {
        $.post('/auth', {name: name}, function(response) {
            if(response.result) {
                initApplication();
            }
        });
    };

    $.get('/init', {}, function(response) {
        if(!response.result) {
            let name = prompt('Please, enter your name: ')
            registerUser(name);
            return;
        }
        initApplication();
    });
});