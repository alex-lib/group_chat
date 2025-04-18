$(function() {
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