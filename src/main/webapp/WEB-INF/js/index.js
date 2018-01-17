


(function () {
    var db = {
        loadData: function (filter) {
            return db.profit;
        }
    };

    db.profit = [];

    window.db = db;

    var db_client_consumption = {
        loadData: function (filter) {
            return db_client_consumption.consumption;
        }
    };

    db_client_consumption.consumption = [];

    window.db_client_consumption = db_client_consumption;

    var db_vendor_consumption = {
        loadData: function (filter) {
            return db_vendor_consumption.consumption;
        }
    };

    db_vendor_consumption.consumption = [];

    window.db_vendor_consumption = db_vendor_consumption;

}());

$(document).ready(function(){



    window.lastMessagesCount = 0;
    window.lastIncomingSum = 0;
    window.lastOutgoingSum = 0;
    window.lastProfit = 0;

    /* Main Chart */

    window.data1 = [];

    window.labels = ["Messages activity"];
    window.colors = [
        '#20b9ae',
        tinycolor('#20b9ae').darken(4).toString(),
        tinycolor('#20b9ae').darken(8).toString()
    ];

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

        $("#jsGrid-profit").jsGrid({
            height: 285,
            width: "100%",
            autoload: true,
            controller: db,
            selecting: false,
            loadIndication: false,
            fields: [
                {title: "Customer account", name: "client_account", type: "text", width: 200, align: "center"},
                {title: "Direction", name: "direction", type: "text", width: 200, align: "center"},
                {title: "Messages count", name: "messages_count", type: "text", width: 75, align: "center"},
                {title: "Profit", name: "profit", type: "text", width: 150, align: "center"}
            ]
        });

        $("#jsGrid-vendor-consumption").jsGrid({
            height: 285,
            width: "100%",
            autoload: true,
            controller: db_vendor_consumption,
            selecting: false,
            loadIndication: false,
            fields: [
                {title: "Customer", name: "customer", type: "text", width: 200, align: "center"},
                {title: "Account", name: "account", type: "text", width: 200, align: "center"},
                {title: "Sum", name: "sum", type: "text", width: 75, align: "center"}
            ]
        });

        $("#jsGrid-client-consumption").jsGrid({
            height: 285,
            width: "100%",
            autoload: true,
            controller: db_client_consumption,
            selecting: false,
            loadIndication: false,
            fields: [
                {title: "Customer", name: "customer", type: "text", width: 200, align: "center"},
                {title: "Account", name: "account", type: "text", width: 200, align: "center"},
                {title: "Sum", name: "sum", type: "text", width: 75, align: "center"}
            ]
        });

    };

    window.jsGrid_init();



    fillIndexData();

});




function fillIndexData() {
    var loadIndicator = new jsGrid.LoadIndicator();
    loadIndicator.show();
    var request = $.get('/admin_full_index_report', function (data) {
        $("#messagesCount").text(function () {
            return data.messagesCount;
        });

        $("#incomingSum").text(function () {
            return data.incomingSum.toFixed(2);
        });

        $("#outgoingSum").text(function () {
            return data.outgoingSum.toFixed(2);
        });

        $("#profit").text(function () {
            return data.profit.toFixed(2);
        });

        $("#lastMessageCount").text(function () {
            return "+" + (data.messagesCount - window.lastMessagesCount);
        });

        $("#lastIncomingSum").text(function () {
            return "+" + (data.incomingSum - window.lastIncomingSum).toFixed(2);
        });

        $("#lastOutgoingSum").text(function () {

            return "+" + (data.outgoingSum - window.lastOutgoingSum).toFixed(2);
        });

        $("#lastProfit").text(function () {
            var profit = data.profit - window.lastProfit;
            if (profit > 0 ) {
                return "+" + profit.toFixed(2);
            } else {
                return profit.toFixed(2);
            }

        });

        window.data1 = [];

        for (rowIndex in data.messageActivityTable) {
            tempArray = [];
            tempArray.push(data.messageActivityTable[rowIndex].hour);
            tempArray.push(data.messageActivityTable[rowIndex].count);
            window.data1.push(tempArray);
        }


        db.profit = [];
        for (rowIndex in data.profitTable) {
            newObj = {};
            newObj["client_account"] = data.profitTable[rowIndex].clientAccount;
            newObj["direction"] = data.profitTable[rowIndex].direction;
            newObj["messages_count"] = data.profitTable[rowIndex].messagesCount;
            newObj["profit"] = data.profitTable[rowIndex].profit.toFixed(2);
            db.profit.push(newObj)
        }


        db_vendor_consumption.consumption = [];
        for (rowIndex in data.vendorConsumptionTable) {
            newObj = {};
            newObj["customer"] = data.vendorConsumptionTable[rowIndex].customer;
            newObj["account"] = data.vendorConsumptionTable[rowIndex].account;
            newObj["sum"] = data.vendorConsumptionTable[rowIndex].sum.toFixed(2);
            db_vendor_consumption.consumption.push(newObj)
        }

        db_client_consumption.consumption = [];
        for (rowIndex in data.clientConsumptionTable) {
            newObj = {};
            newObj["customer"] = data.clientConsumptionTable[rowIndex].customer;
            newObj["account"] = data.clientConsumptionTable[rowIndex].account;
            newObj["sum"] = data.clientConsumptionTable[rowIndex].sum.toFixed(2);
            db_client_consumption.consumption.push(newObj)
        }

        $("#jsGrid-profit").jsGrid("loadData");
        $("#jsGrid-profit").jsGrid("refresh");

        $("#jsGrid-vendor-consumption").jsGrid("loadData");
        $("#jsGrid-vendor-consumption").jsGrid("refresh");

        $("#jsGrid-client-consumption").jsGrid("loadData");
        $("#jsGrid-client-consumption").jsGrid("refresh");

        window.lastMessagesCount = data.messagesCount;
        window.lastIncomingSum = data.incomingSum;
        window.lastOutgoingSum = data.outgoingSum;
        window.lastProfit = data.profit;

        initializeCharts();

        loadIndicator.hide();
    }, "json").fail(function () {
        location.reload();
    });
}

function initializeCharts() {
    $.plot($("#main-chart"), [{
        data : window.data1,
        color : window.colors[0]
    }], {
        series : {
            lines : {
                show : true,
                fill : true,
                lineWidth : 3,
                fillColor : {
                    colors : [{
                        opacity : 1
                    }, {
                        opacity : 1
                    }]
                }
            },
            points : {
                show : true,
                radius: 0
            },
            shadowSize : 0,
            curvedLines: {
                apply: true,
                active: true,
                monotonicFit: true
            }
        },
        grid : {
            labelMargin: 10,
            color: "#aaa",
            hoverable : true,
            borderWidth : 0,
            backgroundColor : "#fff",
        },
        legend : {
            position : "ne",
            margin : [0, -40],
            noColumns : 0,
            labelBoxBorderColor : null,
            labelFormatter : function(label, series) {
                // adding space to labes
                return '' + label + '&nbsp;&nbsp;';
            }
        },
        tooltip : true,
        tooltipOpts : {
            content : '%s: %y',
            shifts : {
                x : -60,
                y : 25
            },
            defaultTheme : false
        }
    });
}