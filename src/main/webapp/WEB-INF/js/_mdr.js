/**
 * Created by АРТЕМ on 03.08.2017.
 */

(function () {

    var db = {
        loadData: function (filter) {
            return db.messages;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
    db.messages = [];

    window.db = db;

}());


db.pageSize = 5;



$(document).ready(function () {

    var date_start;
    var date_end;

    var new_first_number = 0;
    var new_last_number = 0;

    var new_min_id = 0;
    var new_max_id = 0;

    var amount_of_rows = 20;
    var loadIndicator = new jsGrid.LoadIndicator();


//=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_email_account').css('min-height', (heightWindow - 190) + 'px');
        db.jsgrid_height = (((heightWindow - 140) > 200) ? (heightWindow - 190) : 200) + 'px';
    }

    if (document.getElementsByClassName('content_email_account')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }


//=============== Календарь ==============================


    $('input[name="daterange-with-time"]').daterangepicker({
        timePicker: true,
        timePickerIncrement: 1,
        timePicker24Hour: true,
        timePickerSeconds: true,
        locale: {
            format: 'YYYY-MM-DD HH:mm:ss'
        },
        buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-success',
        cancelClass: 'btn-inverse',
        ranges: {
            'Last hour': [moment().utc().subtract(1, 'hours'), moment().utc()],
            'Today': [moment().utc().startOf('day'), moment().utc().endOf('day')],
            'Yesterday': [moment().utc().subtract(1, 'days').startOf('day'), moment().utc().subtract(1, 'days').endOf('day')]
        }
    }, function (start, end, lable) {
        date_start = start.format('YYYY-MM-DD HH:mm:ss');
        date_end = end.format('YYYY-MM-DD HH:mm:ss');
    });

    var start = moment().subtract(365, 'days');
    var end = moment();

    function cb(start, end) {
        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));

    }

    cb(start, end);

//=============== Select ==============================

    $(".js-example-basic-single").select2({
        dropdownParent: $('#client_parent')
    });

    $(".js-example-basic-single").on('select2:select', function () {
        event.stopPropagation();
    });

    $('[data-plugin="select2"]').select2($(this).attr('data-options'));

    $(".select_mcc").on('select2:select', function () {
        event.stopPropagation();
        $.get('/admin_full_get_mnc_by_mcc', {
            mcc: $('.select_mcc').val()
        }, function (data) {
            var select = $('#mnc_menu');
            select.find('option').remove();
            select.append('<option value="0">All MNC</option>')
            for (var n in data.mncXmlList) {
                select.append('<option value="' + data.mncXmlList[n].mnc + '">' + data.mncXmlList[n].network + ' (' + data.mncXmlList[n].mnc + ')</option>')
            }
        }, "json").fail(function () {
            location.reload();
        })
    });

    $(".select_mcc").on('select2:open', function () {
        $(".select2-search").mouseup(function () {
            event.stopPropagation();
        })
    })

    $(".select_mnc").on('select2:select', function () {
        event.stopPropagation();
    });

    $(".select_sort").on('select2:select', function () {
        event.stopPropagation();
    });


//================================= Generate ============================

    var post_mdr = function (first_number, last_number, min_id, max_id,  direction) {
        loadIndicator.show();
        $.post('/admin_full_report_mdr', {
            msgid_list_filter: $('[name="msgid_list_filter"]').tagsinput('items').toString(),
            vendor_msgid_list_filter: $('[name="vendor_msgid_list_filter"]').tagsinput('items').toString(),
            created_at_start_filter: date_start,
            created_at_end_filter: date_end,
            smpp_client_account_id_filter: $('#client_menu').val(),
            smpp_vendor_account_id_filter: $('#vendor_menu').val(),
            destination_addr_list_filter: $('[name="destination_addr_list_filter"]').tagsinput('items').toString(),
            source_addr_list_filter: $('[name="source_addr_list_filter"]').tagsinput('items').toString(),
            mcc_filter: $('#mcc_menu').val(),
            mnc_list_filter: ($('#mnc_menu').val() == null) ? null : $('#mnc_menu').val().toString(),
            first_number: first_number,
            last_number: last_number,
            min_id: min_id,
            max_id: max_id,
            amount_of_rows: amount_of_rows,
            direction: direction,
            sort_list: ($('#sort_menu').val() == null) ? null : $('#sort_menu').val().toString()
        }, function (data) {
            var mdrXmlList = data.mdrXmlList;
            db.messages = [];
            var it = 0;
            for (var n in mdrXmlList) {
                newElement = {};
                newElement['source_connector'] = mdrXmlList[n].source_connector;

                var d = new Date(mdrXmlList[n].created_at);
                newElement['created_at'] = getFormatedUTCDate(d);

                newElement['mcc'] = mdrXmlList[n].mcc;
                if (mdrXmlList[n].mnc == "Flt") {
                    newElement['mnc'] = "FLAT";
                } else {
                    newElement['mnc'] = mdrXmlList[n].mnc;
                }
                newElement['uid'] = mdrXmlList[n].uid;
                newElement['msgid'] = mdrXmlList[n].msgid;
                newElement['source_addr'] = mdrXmlList[n].source_addr;
                newElement['destination_addr'] = mdrXmlList[n].destination_addr;
                newElement['routed_cid'] = mdrXmlList[n].routed_cid;
                newElement['client_price'] = mdrXmlList[n].client_price;
                newElement['vendor_price'] = mdrXmlList[n].vendor_price;
                newElement['vendor_msgid'] = mdrXmlList[n].vendor_msgid;
                newElement['submit_status'] = mdrXmlList[n].submit_status;
                newElement['delivery_status'] = mdrXmlList[n].delivery_status;
                newElement['registered_delivery'] = mdrXmlList[n].registered_delivery;
                newElement['delivery_time'] = mdrXmlList[n].delivery_time;
                newElement['pdu_count'] = mdrXmlList[n].pdu_count;
                newElement['trial'] = mdrXmlList[n].trials;
                var d = new Date(mdrXmlList[n].status_at);
                newElement['status_at'] = getFormatedUTCDate(d);
                newElement['short_message'] = SplitLongText(mdrXmlList[n].short_message);

                db.messages.push(newElement);
                it++;
            }
            window.jsGrid_init();
            new_first_number = data.first_number;
            new_last_number = data.last_number;
            new_min_id = data.min_id;
            new_max_id = data.max_id;

            if (direction == 'generate') {
                $("#btn_previous").addClass("disabled");

                if (it > 0) {
                    $("#btn_save").removeClass("disabled");
                } else {
                    $("#btn_save").addClass("disabled");
                    $("#btn_next").addClass("disabled");
                    $("#btn_previous").addClass("disabled");
                }

            }
            if (direction == 'next' && it < 20) {
                $("#btn_next").addClass("disabled");
            } else {
                if (it > 0) {
                    $("#btn_next").removeClass("disabled");
                }
            }
            if (direction == 'next') {
                $("#btn_previous").removeClass("disabled");
            }

            if (direction == 'previous' && new_first_number == 1) {
                $("#btn_previous").addClass("disabled");
            }
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        })
    }


    $('#btn_generate').click(function () {
        if (date_start == undefined || date_end == undefined) {
            var dates = $('input[name="daterange-with-time"]').val().split(' - ');
            date_start = dates[0];
            date_end = dates[1];
        }

        post_mdr(0, 0, 0, 0, 'generate');
    })

    $('#btn_previous').click(function () {
        if ($('#btn_previous').hasClass("disabled")) {
            return;
        }
        if (date_start == undefined || date_end == undefined) {
            var dates = $('input[name="daterange-with-time"]').val().split(' - ');
            date_start = dates[0];
            date_end = dates[1];
        }

        post_mdr(new_first_number, new_last_number, new_min_id, new_max_id, 'previous');
    })

    $('#btn_next').click(function () {
        if ($('#btn_next').hasClass("disabled")) {
            return;
        }
        if (date_start == undefined || date_end == undefined) {
            var dates = $('input[name="daterange-with-time"]').val().split(' - ');
            date_start = dates[0];
            date_end = dates[1];
        }

        post_mdr(new_first_number, new_last_number, new_min_id, new_max_id, 'next');
    })


    //=============== jsGrid ==============================

    jsGrid.setDefaults({
        tableClass: "jsgrid-table table table-striped table-hover"
    }), jsGrid.setDefaults("text", {
        _createTextBox: function () {
            return $("<input>").attr("type", "text").attr("class", "form-control input-sm")
        }
    }), jsGrid.setDefaults("number", {
        _createTextBox: function () {
            return $("<input>").attr("type", "number").attr("class", "form-control input-sm")
        }
    }), jsGrid.setDefaults("textarea", {
        _createTextBox: function () {
            return $("<input>").attr("type", "textarea").attr("class", "form-control")
        }
    }), jsGrid.setDefaults("control", {
        _createGridButton: function (cls, tooltip, clickHandler) {
            var grid = this._grid;
            return $("<button>").addClass(this.buttonClass).addClass(cls).attr({
                type: "button",
                title: tooltip
            }).on("click", function (e) {
                clickHandler(grid, e)
            })
        }
    }), jsGrid.setDefaults("select", {
        _createSelect: function () {
            var $result = $("<select>").attr("class", "form-control form-control-sm"),
                valueField = this.valueField,
                textField = this.textField,
                selectedIndex = this.selectedIndex;
            return $.each(this.items, function (index, item) {
                var value = valueField ? item[valueField] : index,
                    text = textField ? item[textField] : item,
                    $option = $("<option>").attr("value", value).text(text).appendTo($result);
                $option.prop("selected", selectedIndex === index);
            }), $result;
        }
    })

    window.jsGrid_init = function () {
        $("#jsGrid-mdr").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            fields: [
                {title: "Protocol", name: "source_connector", type: "text", width: 100, align: "center"},
                {title: "Submit date", name: "created_at", type: "text", width: 250, align: "center"},
                {title: "MCC", name: "mcc", type: "text", width: 75, align: "center"},
                {title: "MNC", name: "mnc", type: "text", width: 75, align: "center"},
                {title: "Client account", name: "uid", type: "text", width: 200, align: "center"},
                {title: "Price", name: "client_price", type: "text", width: 200, align: "center"},
                {title: "Message id", name: "msgid", type: "text", width: 300, align: "center"},
                {title: "Vendor message id", name: "vendor_msgid", type: "text", width: 300, align: "center"},
                {title: "Source Address", name: "source_addr", type: "text", width: 150, align: "center"},
                {title: "Destination Address", name: "destination_addr", type: "text", width: 150, align: "center"},
                {title: "Vendor account", name: "routed_cid", type: "text", width: 200, align: "center"},
                {title: "Price", name: "vendor_price", type: "text", width: 200, align: "center"},
                {title: "Submit status", name: "submit_status", type: "text", width: 150, align: "center"},
                {title: "Delivery status", name: "delivery_status", type: "text", width: 150, align: "center"},
                {title: "Registered delivery", name: "registered_delivery", type: "text", width: 150, align: "center"},
                {title: "Delivery time", name: "delivery_time", type: "text", width: 100, align: "center"},
                {title: "PDU count", name: "pdu_count", type: "text", width: 100, align: "center"},
                {title: "Trials", name: "trials", type: "text", width: 100, align: "center"},
                {title: "Status date", name: "status_at", type: "text", width: 250, align: "center"},
                {title: "Short message", name: "short_message", type: "text", width: 400, align: "center"},
            ],
        });
    };

    window.jsGrid_init();


});