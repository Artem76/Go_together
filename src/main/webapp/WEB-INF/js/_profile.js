/**
 * Created by АРТЕМ on 17.08.2017.
 */

$(document).ready(function () {

    var post_profile = function () {
        $.post('/profile_save', {
            name_user: $('#name').val(),
            password_user: $('#password').val(),
            confirm_password_user: $('#confirm_password').val(),
            phone_user: $('#phone').val(),
            email_user: $('#email').val()
        }, function (data) {
            if ($.isEmptyObject(data)) {
                $(location).attr('href','/enter');
            } else {
                $('input.b-a-danger').removeClass('b-a-danger');
                $('#modal_error').modal('show');
                $.each(data,function (i, id_input) {
                    $('#' + id_input).addClass('b-a-danger');
                })
            }
        }, "json").fail(function () {
            location.reload();
        })
    };

    $('#post_go').click(function () {
        $('#modal_save').modal('hide');
        post_profile();
    });

});

