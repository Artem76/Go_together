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
                {title: "Customer", name: "customer", type: "text", width: 300, align: "center"},
                {title: "Balance", name: "balance", type: "text", width: 100, align: "center"}
            ],
        });
    };

    window.jsGrid_init();
    $(".js-example-basic-single").select2();


});


function loadReport() {
    window.db.messages = [];
    var loadIndicator = new jsGrid.LoadIndicator();
    loadIndicator.show();
    $.get('/admin_full_report_balances', {
        id_customer: $('#customer').val()
    }, function (data) {

        for(i = 0; i < data.length; i++) {
            row = data[i];
            obj = {
                "customer" : row.customerName,
                "balance": row.balance.toFixed(2)
            }
            window.db.messages.push(obj);
        }
        window.jsGrid_init();

    }, "json").fail(function () {
        location.reload();
    })
    loadIndicator.hide();
}