/**
 * Created by cmua7 on 02.09.2017.
 */


$(document).ready(function () {

    $('#signature').summernote({
        popover: false
    });

    $('#textAutoAnswer').summernote({
        popover: false
    });

    $('#textAutoEmailOutgoingTT').summernote({
        popover: false
    });

    $('#textEmailClientRn').summernote({
        popover: false
    });

    $('#textEmailInvoice').summernote({
        popover: false
    })

    $('#textHtmlForPdfInvoice').summernote({
        popover: false
    })

    $('#post_go').click(function () {
        $('#modal_save').modal('hide');
        saveEmailAccount();
    });

    var hideByRole = function () {
        $(".for_noc").css("display", "block");
        $(".for_ratemode").css("display", "block");
        $(".for_invoice").css("display", "block");
        if ($("#emailRole").val() == "NOC_SMS" || $("#emailRole").val() == "NOC_VOIP"){
            $(".for_ratemode").css("display", "none");
            $(".for_invoice").css("display", "none");
        }
        if ($("#emailRole").val() == "Ratemode_SMS" || $("#emailRole").val() == "Ratemode_VOIP"){
            $(".for_noc").css("display", "none");
            $(".for_invoice").css("display", "none");
        }
        if ($("#emailRole").val() == "Invoice_SMS"){
            $(".for_ratemode").css("display", "none");
            $(".for_noc").css("display", "none");
        }
    }

    $("#emailRole").change(function () {
        hideByRole();
    })

    hideByRole();


    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_email_account_edit').css('min-height', (heightWindow - 97) + 'px');
        //$('.note-editable').css('min-height', (((heightWindow - 850) > 50) ? (heightWindow - 850) : 50) + 'px');

    }

    if (document.getElementById('content_email_account_edit')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }
});

function saveEmailAccount() {

    var result = {};

    $('#form_content input').each(function () {
        result[$(this).attr("name")] = $(this).val();
    })

    $('#form_content select').each(function () {
        result[$(this).attr("name")] = $(this).val();
    })

    result["signature"] = $('#signature').summernote('code');
    result["textAutoAnswer"] = $('#textAutoAnswer').summernote('code');
    result["textAutoEmailOutgoingTT"] = $('#textAutoEmailOutgoingTT').summernote('code');
    result["textEmailClientRN"] = $('#textEmailClientRn').summernote('code');
    result["textEmailInvoice"] = $('#textEmailInvoice').summernote('code');
    result["textHtmlForPdfInvoice"] = $('#textHtmlForPdfInvoice').summernote('code');

    console.log(result);

    $.post('/admin_full_email_account_edit_save', result,
        function (data) {
            if ($.isEmptyObject(data)) {
                $(location).attr('href', '/admin_full_email_account_list');
            } else {
                $('.content_email_account_edit .b-a-danger').removeClass('b-a-danger');
                $('#modal_error').modal('show');
                for (var i = 0, len = data.length; i < len; i++) {
                    $('[name="' + data[i] + '"]').addClass('b-a-danger');
                }
            }
        }, "json").fail(function (e) {
        location.reload();
    })

}