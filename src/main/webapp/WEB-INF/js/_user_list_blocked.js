/**
 * Created by АРТЕМ on 14.08.2017.
 */
$(document).ready(function () {
    function table_td_th_width(class_name, proc_width, min_width) {
        var width_table = $('#user_list').width() - 80;
        var width = (width_table * proc_width / 100) < min_width ? min_width : (width_table * proc_width / 100);
        $('.' + class_name).css({'min-width': width + 'px', 'max-width': width + 'px', 'padding-right': '0px'});
    }


    var resizeTable = function () {
        var heightWindow = $(window).height();
        $('#user_list').css('height', (heightWindow - 97) + 'px');
        $('#table_user_list').css('height', (heightWindow - 260) + 'px');
        $('.tbody_user_list').css('height', (heightWindow - 340) + 'px');
        table_td_th_width('name', 25, 190);
        table_td_th_width('login', 25, 190);
        table_td_th_width('phone', 25, 190);
        table_td_th_width('email', 25, 190);
    }


    $(window).resize(resizeTable);

    resizeTable();
});

