/**
 * Created by АРТЕМ on 14.08.2017.
 */

(function () {

    var db = {
        loadData: function (filter) {
            return selectedItems;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
    db.messages = [];

    window.db = db;
    db.jsgrid_height = 0;

}());


$(document).ready(function () {

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.customer-list').css('min-height', (heightWindow - 190) + 'px');
        db.jsgrid_height = (((heightWindow - 140) > 200) ? (heightWindow - 190) : 200) + 'px';
    }

    if (document.getElementsByClassName('customer-list')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }


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
        $("#jsGrid-invoice-list").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            rowClick: function (args) {
                document.location.href = "/admin_full_sms_outgoing_invoice_edit?id_invoice=" + args.item.id/* + "&error=&err_name="*/;
            },
            fields: [
                {title: "id", name: "id", type: "text", width: 100, align: "center", visible: false},
                {title: "Number", name: "number", type: "text", width: 100, align: "center"},
                {title: "Date", name: "date", type: "text", width: 100, align: "center"},
                {title: "Customer", name: "customer", type: "text", width: 200, align: "center"},
                {title: "Billing period", name: "billingPeriodName", type: "text", width: 75, align: "center"},
                {title: "Start date", name: "startDate", type: "text", width: 100, align: "center"},
                {title: "End date", name: "endDate", type: "text", width: 100, align: "center"},
                {title: "Pay date", name: "payDate", type: "text", width: 100, align: "center"},
                {title: "Sum", name: "invoiceSum", type: "text", width: 100, align: "center"}
            ],
        });
    };

    window.jsGrid_init();

/*

    $('#pdf').click(function () {
        $.post('/admin_full_create_pdf',
            function (data) {

            }, "json").fail(function () {

        });
    })*/
});

