/**
 * Created by АРТЕМ on 21.08.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            return db.clients;
        }

    };

    window.db = db;
    db.jsgrid_height = 0;

    db.clients = [];
    db.height = 0;

}());


var loadIndicator = new jsGrid.LoadIndicator();

var selectedItems = [];

var selectItem = function(item) {
    selectedItems.push(item);
};

var unselectItem = function(item) {
    selectedItems = $.grep(selectedItems, function(i) {
        return i !== item;
    });
};

$(document).ready(function () {


    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.messaging_flow_list').css('min-height', (heightWindow - 190) + 'px');
        db.jsgrid_height = (((heightWindow - 140) > 200) ? (heightWindow - 190) : 190) + 'px';
    }
    resizeBlock();

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
            return $.each(this.items, function (index, item) {
                var value = valueField ? item[valueField] : index,
                    text = textField ? item[textField] : item,
                    $option = $("<option>").attr("value", value).text(text).appendTo($result);
                $option.prop("selected", selectedIndex === index);
            }), $result

        }
    })


    window.jsGrid_init = function () {
        $("#jsGrid-messaging_flow").jsGrid({
            height: db.jsgrid_height,
            width: "100%",

            sorting: true,

            data: window.db.clients,
            fields: [
                {
                    itemTemplate: function(_, item) {
                        return $("<input>").attr("type", "checkbox")
                            .prop("checked", $.inArray(item, selectedItems) > -1)
                            .on("change", function () {
                                $(this).is(":checked") ? selectItem(item) : unselectItem(item);
                            });
                    },
                    align: "center",
                    width: 50,
                    title: "",
                    visible: false
                },
                {title: "Client", name: "client", type: "text", width: 150, align: "center"},
                {title: "Account", name: "clientAccount", type: "text", width: 150, align: "center"},
                {title: "Country", name: "countryName", type: "text", width: 140, align: "center"},
                {title: "Network", name: "networkName", type: "text", width: 100, align: "center"},
                {title: "SA", name: "sourceAddr", type: "text", width: 130, align: "center"},
                {title: "DA", name: "destinationAddr", type: "text", width: 130, align: "center"},
                {title: "Vendor", name: "vendor", type: "text", width: 150, align: "center"},
                {title: "Account", name: "vendorAccount", type: "text", width: 150, align: "center"},
                {title: "Short Message", name: "shortMessage", type: "text", width: 700, align: "center"}
            ]
        });
        $(".js-example-basic-single").select2();
    };

    window.jsGrid_init();

});

function getSelectedItems() {
    var daArray = "";
    selectedItems.forEach(
        function(item) {
            alert(item["destinationAddr"])
        })
}

function generateRaport() {
    loadIndicator.show();

    var request = $.get('/admin_full_messaging_flow_list', function (data) {
        window.db.clients = [];
        for (i = 0; i < data.length; i++) {
            newRow = {};
            newRow['client'] = data[i].customer;
            newRow['clientAccount'] = data[i].customerAccount;
            newRow['countryName'] = data[i].country;
            newRow['networkName'] = data[i].network;
            newRow['sourceAddr'] = data[i].sourceAddress;
            newRow['destinationAddr'] = data[i].destinationAddress;
            newRow['vendor'] = data[i].vendor;
            newRow['vendorAccount'] = data[i].vendorAccount;
            newRow['shortMessage'] = SplitLongText(data[i].shortMessage);

            window.db.clients.push(newRow);
        }
        window.jsGrid_init();
        loadIndicator.hide();
    }, "json").fail(function () {
        location.reload();
    });

}
