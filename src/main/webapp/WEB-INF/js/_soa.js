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

    loadIndicator = new jsGrid.LoadIndicator();


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
        dropdownParent: $('#customer_parent')
    });

    $(".js-example-basic-single").on('select2:select', function () {
        event.stopPropagation();
    });
    
    //=============== jsGrid ==============================

    var select2 = function(config) {
        jsGrid.Field.call(this, config);
    };

    select2.prototype = new jsGrid.Field({

        itemTemplate: function(value) {
            return $.makeArray(value).join(", ");
        },

        _createSelect: function () {
            var $result = $("<select>").attr("class", "form-control form-control-sm js-example-basic-single"),
                valueField = this.valueField,
                textField = this.textField,
                selectedIndex = this.selectedIndex;
            return $.each(this.items, function (index, item) {
                var value = valueField ? item[valueField] : index,
                    text = textField ? item[textField] : item,
                    $option = $("<option>").attr("value", value).text(text).appendTo($result);
                $option.prop("selected", selectedIndex === index);
            }), $result;
            return $result;
        }
    });

    jsGrid.fields.select2 = select2;

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
            var $result = $("<select>").attr("class", "form-control form-control-sm js-example-basic-single"),
                valueField = this.valueField,
                textField = this.textField,
                selectedIndex = this.selectedIndex;
            setTimeout(function() {
                $result.select2({
                    width: 200
                });
            });
            return $.each(this.items, function (index, item) {
                var value = valueField ? item[valueField] : index,
                    text = textField ? item[textField] : item,
                    $option = $("<option>").attr("value", value).text(text).appendTo($result);
                $option.prop("selected", selectedIndex === index);
            }), $result;
        }
    }),
    window.jsGrid_init = function () {
        $("#jsGrid-mdr").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            fields: [
                {title: "Event", name: "event", type: "text", width: 100, align: "center"},
                {title: "Billing period", name: "billing_period", type: "text", width: 50, align: "center"},
                {title: "Start date", name: "start_date", type: "text", width: 100, align: "center"},
                {title: "End date", name: "end_date", type: "text", width: 100, align: "center"},
                {title: "Start balance", name: "start_balance", type: "text", width: 100, align: "center"},
                {title: "Debit", name: "debit", type: "text", width: 75, align: "center"},
                {title: "Credit", name: "credit", type: "text", width: 75, align: "center"},
                {title: "End balance (customer debt)", name: "end_balance", type: "text", width: 90, align: "center"}
            ],
        });
    };

    window.jsGrid_init();
    $(".js-example-basic-single").select2();


});


function loadReport() {

    window.db.messages = [];

    if ($('#customer').val() == 0) {
        toastr.options = {
            positionClass: 'toast-top-right'
        };
        toastr.warning('Customer must be selected!');
        return;
    }

    var startBalance = 0.00;
    var dates = $('input[name="daterange-with-time"]').val().split(' - ');
    var loadIndicator = new jsGrid.LoadIndicator();
    loadIndicator.show();
    $.get('/admin_full_report_soa', {
        id: -1,
        date_start_string: dates[0],
        date_end_string: dates[1],
        id_customer: $('#customer').val()
    }, function (data) {
        startBalance = startBalance + parseFloat(data['startBalance']);

        for(i = 0; i < data['list'].length; i++) {
            row = data['list'][i];
            balance = startBalance;

            debit = 0.00;
            credit = 0.00;

            event = "";
            if (row.event == "Current_Traffic") {
                event = "Current period traffic";
            }
            if (row.event == "Incoming_Invoice") {
                event = "Incoming invoice";
            }
            if (row.event == "Outgoing_Invoice") {
                event = "Outgoing invoice";
            }
            if (row.event == "Incoming_Payment") {
                event = "Incoming payment";
            }
            if (row.event == "Outgoing_Payment") {
                event = "Outgoing payment";
            }

            var current_start_date = new Date(row.startDate);


            if (row.event == "Current_Traffic") {
                event = "Current period traffic";
                debit = parseFloat(row.sum);
                credit = parseFloat(row.sumAdditional);

                endBalance = (startBalance + parseFloat(row.sum) - parseFloat(row.sumAdditional)).toFixed(2);
                startBalance = startBalance + parseFloat(row.sum) - parseFloat(row.sumAdditional);
            } else {
                if (row.debitCredit == "Debit") {
                    debit = parseFloat(row.finalSum);

                    endBalance = (startBalance + parseFloat(row.finalSum)).toFixed(2);
                    startBalance = startBalance + parseFloat(row.sum);
                } else {
                    credit = parseFloat(row.finalSum);

                    endBalance = (startBalance - parseFloat(row.finalSum)).toFixed(2);
                    startBalance = startBalance - parseFloat(row.sum);
                }
            }


            obj = {
                "start_balance" : balance.toFixed(2),
                "state": row.debitCredit,
                "event": event,
                "billing_period" : row.billingPeriodName,
                "debit" : debit.toFixed(2),
                "credit" : credit.toFixed(2),
                "start_date" : getFormatedUTCDate(new Date(row.startDate)),
                "end_date" : getFormatedUTCDate(new Date(row.endDate)),
                "sum": row.finalSum.toFixed(2),
                "end_balance": endBalance
                }
            window.db.messages.push(obj);
        }
        window.jsGrid_init();

    }, "json").fail(function () {
        location.reload();
    })
    loadIndicator.hide();
}