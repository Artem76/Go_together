/**
 * Created by АРТЕМ on 10.01.2018.
 */
$(document).ready(function () {

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_softswitch').css('min-height', (heightWindow - 155) + 'px');
    }

    $(window).resize(resizeBlock);
    resizeBlock();


    //=============== Save Data ==============================

    $('#post_go').click(function () {
        $('#modal_save').modal('hide');
        saveSoftswitche();
    });

    function saveSoftswitche() {

        var result = {};

        $('input').each(function () {
            result[$(this).attr("name")] = $(this).val();
        })

        $('select').each(function () {
            result[$(this).attr("name")] = $(this).val();
        })

        if ($('#mdr_load_enabled').is(":checked"))
        {
            result['mdr_load_enabled'] = true;
        } else {
            result['mdr_load_enabled'] = false;
        }

        if ($('#api_exchange_enabled').is(":checked"))
        {
            result['api_exchange_enabled'] = true;
        } else {
            result['api_exchange_enabled'] = false;
        }

        $.post('/admin_full_softswitch_edit_save', result,
            function (data) {
                if ($.isEmptyObject(data)) {
                        $(location).attr('href','/admin_full_softswitch_list');
                } else {
                    $('.content_softswitch .b-a-danger').removeClass('b-a-danger');
                    $('#modal_error').modal('show');
                    for (var i = 0, len = data.length; i < len; i++) {
                        $('[name="' + data[i] + '"]').addClass('b-a-danger');
                    }
                }
            }, "json").fail(function () {
            location.reload();
        })

    }
})