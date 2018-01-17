/**
 * Created by АРТЕМ on 28.08.2017.
 */
$(document).ready(function () {
    var loadIndicator = new jsGrid.LoadIndicator();
    var begin_num_tr = 0;
    var end_num_tr = 0;
    var end_num_tr_max = 0;
    var mcc_num_td = null;
    var mccmnc_num_td = null;
    var mnc_num_td = null;
    var price_num_td = null;
    var date_num_td = null;


    //=====Создание объекта с данными==========

    document.get_data_excel = function () {
        if ((mccmnc_num_td != null && price_num_td != null) || (mcc_num_td != null && mnc_num_td != null && price_num_td != null)) {
            var data_excel = {};
            data_excel['type'] = (mccmnc_num_td == null) ? 'mcc_mnc_price' : 'mccmnc_price';
            data_excel['number_last_row'] = end_num_tr - begin_num_tr;

            var num_row = 0;
            for (var i = begin_num_tr; i <= end_num_tr; i++) {
                var string = "";
                if (mccmnc_num_td != null) {
                    string = string + $('#excel tr.tr_' + i + ' td.td_' + mccmnc_num_td).text();
                } else {
                    string = string + $('#excel tr.tr_' + i + ' td.td_' + mcc_num_td).text() + '_' + $('#excel tr.tr_' + i + ' td.td_' + mnc_num_td).text();
                }
                string = string + '_' + $('#excel tr.tr_' + i + ' td.td_' + price_num_td).text();
                string = string + '_' + $('#excel tr.tr_' + i + ' td.td_' + date_num_td).text();
                data_excel['row_' + num_row++] = string;
            }
            console.log(data_excel);
            return data_excel;
        }
    }


    //=====Изменение цветов ячеек==============

    var reColorTable = function () {
        for (var i = begin_num_tr; i <= end_num_tr; i++) {
            if (mcc_num_td != null) {
                $('#excel tr.tr_' + i + ' td.td_' + mcc_num_td).css('background-color', 'rgba(255,44,14,0.13)');
            }
            if (mccmnc_num_td != null) {
                $('#excel tr.tr_' + i + ' td.td_' + mccmnc_num_td).css('background-color', 'rgba(255,44,14,0.13)');
            }
            if (mnc_num_td != null) {
                $('#excel tr.tr_' + i + ' td.td_' + mnc_num_td).css('background-color', 'rgba(50,255,12,0.18)');
            }
            if (price_num_td != null) {
                $('#excel tr.tr_' + i + ' td.td_' + price_num_td).css('background-color', 'rgba(9,73,255,0.18)');
            }
            if (date_num_td != null) {
                $('#excel tr.tr_' + i + ' td.td_' + date_num_td).css('background-color', 'rgba(250,250,2,0.18)');
            }
        }

    }


    //=====Передача и получение данных=========

    var excelTableView = function (data) {
        $('#excel tr').remove();
        if (data.error == undefined) {
            end_num_tr = Number(data.tr_number);
            end_num_tr_max = Number(data.tr_number);
            $('#excel').prepend(data.table);
            $('#modal_table').modal('show');


            $('#excel tr.head select').change(function () {

                var revalueSelectTop = function (v, num_td) {
                    $('#excel tr.head td:not("#td_' + num_td + '") select').each(function () {
                        if ($(this).val() == v) {
                            $(this).val('none');
                        }
                    });
                }

                $('.td_con').css('background-color', 'white');
                if (this.value == 'mcc') {
                    mcc_num_td = $(this).parent().attr('id').split('_')[1];
                    revalueSelectTop('mcc', mcc_num_td);
                    mccmnc_num_td = null;
                    revalueSelectTop('mccmnc', mcc_num_td);
                } else if (this.value == 'mccmnc') {
                    mccmnc_num_td = $(this).parent().attr('id').split('_')[1];
                    revalueSelectTop('mccmnc', mccmnc_num_td);
                    mcc_num_td = null;
                    revalueSelectTop('mcc', mccmnc_num_td);
                    mnc_num_td = null;
                    revalueSelectTop('mnc', mccmnc_num_td);
                } else if (this.value == 'mnc') {
                    mnc_num_td = $(this).parent().attr('id').split('_')[1];
                    revalueSelectTop('mnc', mnc_num_td);
                    mccmnc_num_td = null;
                    revalueSelectTop('mccmnc', mnc_num_td);
                } else if (this.value == 'price') {
                    price_num_td = $(this).parent().attr('id').split('_')[1];
                    revalueSelectTop('price', price_num_td);
                } else if (this.value == 'date') {
                    date_num_td = $(this).parent().attr('id').split('_')[1];
                    revalueSelectTop('date', date_num_td);
                } else if (this.value == 'none') {
                    n = $(this).parent().attr('id').split('_')[1];
                    if (mcc_num_td == n) mcc_num_td = null;
                    if (mnc_num_td == n) mnc_num_td = null;
                    if (mccmnc_num_td == n) mccmnc_num_td = null;
                    if (price_num_td == n) price_num_td = null;
                    if (date_num_td == n) price_num_td = null;

                }
                reColorTable();
            })


            $('#excel td.td_-1 select').change(function () {

                var revalueSelectLeft = function (v, num_tr) {
                    $('#excel td.td_-1:not("#tr_' + num_tr + '") select').each(function () {
                        if ($(this).val() == v) {
                            $(this).val('none');
                        }
                    });
                }

                $('.td_con').css('background-color', 'white');
                if (this.value == 'begin') {
                    begin_num_tr = Number($(this).parent().attr('id').split('_')[1]);
                    revalueSelectLeft('begin', begin_num_tr);
                    if (Number(begin_num_tr) > Number(end_num_tr)) {
                        end_num_tr = Number(end_num_tr_max);
                        revalueSelectLeft('end', begin_num_tr);
                    }
                } else if (this.value == 'end') {
                    end_num_tr = Number($(this).parent().attr('id').split('_')[1]);
                    revalueSelectLeft('end', end_num_tr);
                    if (Number(begin_num_tr) > Number(end_num_tr)) {
                        begin_num_tr = 0;
                        revalueSelectLeft('begin', end_num_tr);
                    }
                } else if (this.value == 'none') {
                    n = Number($(this).parent().attr('id').split('_')[1]);
                    if (begin_num_tr == n) begin_num_tr = 0;
                    if (end_num_tr == n) end_num_tr = Number(end_num_tr_max);
                }
                reColorTable();
            })
        } else {
            $('#modal_excel_error').modal('show');
        }
    }

    $('input[type=file]').change(function (e) {
        loadIndicator.show();
        var form = $('form')[0];
        var file = new FormData(form);
        begin_num_tr = 0;
        mcc_num_td = null;
        mnc_num_td = null;
        mccmnc_num_td = null;
        price_num_td = null;
        date_num_td = null;
        end_num_tr = 0;
        end_num_tr_max = 0;

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin_full_set_excel_and_get_string",
            data: file,
            //http://api.jquery.com/jQuery.ajax/
            //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            success: function (data) {
                excelTableView(data);
                loadIndicator.hide();
            },
            error: function (e) {
                loadIndicator.hide();
                location.reload();
            }
        });

        $('form')[0].reset();
    });

    if ((document.id_rn == 0) && (document.id_email_content > 0) && (document.id_email_attachment > 0)) {
        loadIndicator.show();
        begin_num_tr = 0;
        mcc_num_td = null;
        mnc_num_td = null;
        mccmnc_num_td = null;
        price_num_td = null;
        date_num_td = null;
        end_num_tr = 0;
        end_num_tr_max = 0;
        $.post('/admin_full_set_attachment_excel_and_get_string', {
                id_email_attachment: document.id_email_attachment
            },
            function (data) {
                excelTableView(data);
                loadIndicator.hide();
            }, "json").fail(function () {
            loadIndicator.hide();
            location.reload();
        });
    }

})