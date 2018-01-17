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


selectedItems = [];
var loadIndicator = new jsGrid.LoadIndicator();

function modeOnChange() {
    loadIndicator.show();
    $.post('/admin_full_get_accounts_by_client_vendor', {
        mode: $("#client_vendor").val()
    }, function (data) {
        $('#customer').html("");
        $("#customer").append($('<option>', {value: 0, text: 'Select customer ...'}));

        for (i = 0; i < data.length; i++) {
            $("#customer").append($('<option>', {value: data[i].id, text: data[i].text}));
        }
        window.jsGrid_init();
        loadIndicator.hide();
    }, "json").fail(function () {
        window.jsGrid_init();
    })
}

function refreshReport() {

    loadIndicator.show();
    $.post('/admin_full_get_actual_rate_report', {
        mode: $("#client_vendor").val(),
        account : $("#customer").val(),
        country : $("#country").val()
    }, function (data) {
        selectedItems = [];
        for (i = 0; i < data.length; i++) {
            selectedItems.push(data[i]);
        }
        window.jsGrid_init();
        loadIndicator.hide();
    }, "json").fail(function () {
        window.jsGrid_init();
    })
}


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
        $("#jsGrid-customer-list").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            fields: [
                {title: "id", name: "id", type: "text", width: 100, align: "center", visible: false},
                {title: "Customer", name: "systemId", type: "text", width: 150, align: "center"},
                {title: "Country", name: "country", type: "text", width: 100, align: "center"},
                {title: "Network", name: "network", type: "text", width: 100, align: "center"},
                {title: "MCC", name: "mcc", type: "text", width: 50, align: "center"},
                {title: "MNC", name: "mnc", type: "text", width: 50, align: "center"},
                {title: "Actual rate", name: "rate", type: "text", width: 75, align: "center"},
                //{title: "Effective date", name: "effectiveDate", type: "text", width: 100, align: "center"},
                {title: "Future rate", name: "rateFuture", type: "text", width: 130, align: "center"},
                {title: "Manager", name: "manager", type: "text", width: 75, align: "center"}
            ],
        });
    };

    window.jsGrid_init();
    $(".js-example-basic-single").select2();

    loadIndicator.show();
    $.post('/admin_full_get_accounts_by_client_vendor', {
        mode: 'client'
    }, function (data) {
        $('#customer').html("");
        $("#customer").append($('<option>', {value: 0, text: 'Select customer ...'}));

        for (i = 0; i < data.length; i++) {
            $("#customer").append($('<option>', {value: data[i].id, text: data[i].text}));
        }
        window.jsGrid_init();
        loadIndicator.hide();
    }, "json").fail(function () {
        window.jsGrid_init();
    })

});

