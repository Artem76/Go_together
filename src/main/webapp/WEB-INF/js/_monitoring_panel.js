/**
 * Created by АРТЕМ on 18.07.2017.
 */


(function () {

    var db = {
        loadData: function (filter) {
            return db.report_visible;
        }
    };

    var db_last_minute = {
        loadData: function (filter) {
            return db_last_minute.data;
        }
    };


    window.db = db;

    db.jsgrid_statistic_height = 0;
    db_last_minute.jsgrid_last_minute_height = 0;
    db_last_minute.data = [];
    db.report = [];
    db.report_visible = [];
    db.report_object = {};
    db.closed_element = [];

    window.db = db;
    window.db_last_minute = db_last_minute;


}());


window.lastIncomingSum = 0;
window.lastOutgoingSum = 0;
window.lastProfit = 0;
window.incomingSum = 0;
window.outgoingSum = 0;
window.profit = 0;




$(document).ready(function () {

    var date_start;
    var date_end;
    var id_customer = 0;
    var mcc = 0;
    var report_option = 'client';



    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        db.jsgrid_statistic_height = (heightWindow - 200) / 6 * 4;
        db_last_minute.jsgrid_last_minute_height = (heightWindow - 209) - db.jsgrid_statistic_height;

        //db.jsgrid_statistic_height = (((heightWindow - 140) > 200) ? (heightWindow - 500) : 500);
        //db_last_minute.jsgrid_last_minute_height = heightWindow - db.jsgrid_statistic_height - 209;

        db.jsgrid_statistic_height = db.jsgrid_statistic_height + 'px';
        db_last_minute.jsgrid_last_minute_height = db_last_minute.jsgrid_last_minute_height + 'px';

    }

    if (document.getElementsByClassName('content_email_account')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }


    $('button.generate').click(function () {

        refreshReport();
        if (date_start == undefined || date_end == undefined) {
            var dates = $('input[name="daterange-with-time"]').val().split(' - ');
            date_start = dates[0];
            date_end = dates[1];
        }

        var loadIndicator = new jsGrid.LoadIndicator();
        loadIndicator.show();


        id_customer = $('.customer_menu').val();          //выбор Customer
        mcc = $('.mcc_menu').val();                       //выбор MCC
        report_option = $('.report_option_menu').val();   //выбор report_option

        var request = $.get('/admin_full_report_sms_statistic', {
            id_customer: id_customer,
            mcc: mcc,
            date_start: date_start,
            date_end: date_end,
            report_option: report_option
        }, function (data) {

            db.report = [];
            db.report_object = data;
            window.profit = 0;
            window.outgoingSum = 0;
            window.incomingSum = 0;

            fillReportTable(data);
            refillVisibleArray();

            $("#indicator_profit").text("Profit: " + window.profit.toFixed(2));
            $("#indicator_outgoing_sum").text("Outgoing Sum: " + window.outgoingSum.toFixed(2));
            $("#indicator_incoming_sum").text("Incoming Sum: " + window.incomingSum.toFixed(2));



            db_last_minute.data = [];

            for (i=0; i < data.lastMinuteRowXml.length; i++) {
                LMRow = data.lastMinuteRowXml[i];
                newLMRow = {};
                newLMRow['client_account'] = LMRow.customerAccount;
                newLMRow['direction'] = LMRow.network;
                newLMRow['vendor_account'] = LMRow.vendorAccount;
                newLMRow['count'] = LMRow.count;
                newLMRow['client_sum'] = LMRow.clientSum.toFixed(2);
                newLMRow['vendor_sum'] = LMRow.vendorSum.toFixed(2);
                newLMRow['profit'] = LMRow.profit.toFixed(2);

                db_last_minute.data.push(newLMRow);
            }




            window.jsGrid_init();
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
        });

    });

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
        $("#jsGrid-statistics").jsGrid({
            height: db.jsgrid_statistic_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            rowClass: function(item, itemIndex) {
                if (item.level == 1) {
                    return "level1"
                }
                if (item.level == 2) {
                    return "level2"
                }
                if (item.level == 3) {
                    return "level3"
                }

            },
            rowClick: function (args) {
                row_click(args.item);
            },
            fields: [
                {title: "id", name: "id", type: "text", width: 100, align: "center", visible: false},
                {title: "row_parent", name: "row_parent", type: "text", width: 100, align: "center", visible: false},
                {itemTemplate: function(value, item) {
                    if (item['state'] == 'opened') {
                        return "<i class='fa fa-minus-circle'></i>"
                    }
                    if (item['state'] == 'closed') {
                        return "<i class='fa fa-plus-circle'></i>"
                    }
                    //if (item['state'] == 'entity') {
                    //    return "<i class='fa fa-minus'></i>"
                    //}

                }, title: "", name: "button", type: "text", width: 45, align: "center"},
                {itemTemplate: function(value, item) {
                    if (item['level'] == 1) {
                        return "<div style='text-align: left;'>" + value + "</div>";
                    }
                    if (item['level'] == 2) {
                        return "<div style='text-align: left;'>&nbsp;&nbsp;" + value + "</div>";
                    }
                    if (item['level'] == 3) {
                        return "<div style='text-align: left;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + value + "</div>";
                    }
                    if (item['level'] == 4) {
                        return "<div style='text-align: left;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + value + "</div>";
                    }
                    if (item['level'] == 5) {
                        return "<div style='text-align: left;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + value + "</div>";
                    }

                }, title: "Dimensions", name: "dimensions", type: "text", width: 300, align: "center"},
                {title: "Client price", name: "client_price", type: "text", width: 110, align: "center"},
                {title: "Vendor price", name: "vendor_price", type: "text", width: 110, align: "center"},
                {itemTemplate: function(value, item) {
                    if (value <= 50) {
                        return "<span class='tag tag-pill tag-danger'><b>" + value + "%</b></span>";
                    }
                    if (value > 50 && value <= 80) {
                        return "<span class='tag tag-pill tag-warning'><b>" + value + "%</b></span>";
                    }
                    if (value > 80) {
                        return "<span class='tag tag-pill tag-success'><b>" + value + "%</b></span>";
                    }
                }, title: "DLR, %", name: "dlr", type: "text", width: 85, align: "center"},
                {itemTemplate: function(value, item) {
                    if (value > 30) {
                        return "<span class='tag tag-pill tag-danger'><b>" + value + "</b></span>";
                    }
                    if (value >= 15 && value <= 30) {
                        return "<span class='tag tag-pill tag-warning'><b>" + value + "</b></span>";
                    }
                    if (value < 15) {
                        return "<span class='tag tag-pill tag-success'><b>" + value + "</b></span>";
                    }
                },title: "ADT, sec", name: "adt", type: "text", width: 75, align: "center"},
                {title: "Attempts", name: "attempts", type: "text", width: 130, align: "center"},
                {title: "Success Attempts", name: "attempts_success", type: "text", width: 150, align: "center"},
                {itemTemplate: function(value, item) {
                    return "<a href='ya.ru' class='text-success'>" + value + "</a>"
                }, title: "Delivered", name: "delivered", type: "text", width: 150, align: "center"},
                {itemTemplate: function(value, item) {
                    pending_value = item.attempts - item.delivered - item.undelivered - item.rejected - item.other;
                    if (pending_value > 0) {
                        return "<a class='text-danger'>" + (pending_value).toString() + "</a>"
                    } else {
                        return "<a class='text-danger'>0</a>"
                    }
                }, title: "Pending", name: "pending", type: "text", width: 120, align: "center"},
                {itemTemplate: function(value, item) {
                    return "<a>" + value + "</a>"
                },title: "Undelivered", name: "undelivered", type: "text", width: 120, align: "center"},
                {itemTemplate: function(value, item) {
                    return "<a>" + value + "</a>"
                },title: "Rejected", name: "rejected", type: "text", width: 120, align: "center"},
                {itemTemplate: function(value, item) {
                    return "<a>" + value + "</a>"
                },title: "Other", name: "other", type: "text", width: 120, align: "center"},
                {title: "Incoming Sum", name: "incoming_sum", type: "text", width: 150, align: "center"},
                {title: "Outgoing sum", name: "outgoing_sum", type: "text", width: 150, align: "center"},
                {title: "Profit", name: "profit", type: "text", width: 120, align: "center"},
            ],
        });
        $("#jsGrid-last-minute").jsGrid({
            height: db_last_minute.jsgrid_last_minute_height,
            width: "100%",
            autoload: true,
            controller: db_last_minute,
            selecting: false,
            loadIndication: true,
            fields: [
                {title: "Customer account", name: "client_account", type: "text", width: 200, align: "center"},
                {title: "Direction", name: "direction", type: "text", width: 200, align: "center"},
                {title: "Vendor account", name: "vendor_account", type: "text", width: 130, align: "center"},
                {title: "Count", name: "count", type: "text", width: 130, align: "center"},
                {title: "Client sum", name: "client_sum", type: "text", width: 75, align: "center"},
                {title: "Vendor sum", name: "vendor_sum", type: "text", width: 75, align: "center"},
                {itemTemplate: function(value, item) {
                    if (value < 0) {
                        return "<a class='text-danger'>" + value + "</a>"
                    } else {
                        return "<a>" + value + "</a>"
                    }
                },title: "Profit", name: "profit", type: "text", width: 150, align: "center"}
            ],
        });
    };

    window.jsGrid_init();

});

function row_click(row) {
    if (row.state == 'entity') {
        return;
    }
    if (row.state == 'closed') {
        invisibleRowsArray = getRowsForUpdate(row.id, row.level);
        for (i = 0;i < invisibleRowsArray.length;i++) {

        }
        updateMainArray(row.id, 'opened', invisibleRowsArray, true, row.level);
    } else {
        invisibleRowsArray = getRowsForUpdate(row.id, row.level);
        updateMainArray(row.id, 'closed', invisibleRowsArray, false, row.level);
    }
    refillVisibleArray();
    //var t0 = performance.now();
    $("#jsGrid-statistics").jsGrid("loadData");
    $("#jsGrid-statistics").jsGrid("refresh");
    //window.jsGrid_init();
    //var t1 = performance.now();
    //console.log("Call to doSomething took " + (t1 - t0) + " milliseconds.")


    //window.jsGrid_init();



}

function updateMainArray(id, state, visibleArray, visible, level) {
    for (i=0; i < visibleArray.length; i++) {
        currentInvisibleRow = visibleArray[i];

        for (it=0; it < db.report.length; it++) {
            currentRow = db.report[it];
            if (currentInvisibleRow.id == currentRow.id) {
                if (visible) {
                    currentRow.visible = true;
                    if (currentRow.state != 'entity') {
                        if(currentRow.level <= level + 1) {
                            currentRow.state = 'closed';
                        } else {
                            currentRow.state = 'closed';
                            currentRow.visible = false;
                        }
                    } else {
                        if (level != 4) {
                            currentRow.visible = false;
                        }
                    }
                } else {
                    currentRow.visible = false;
                    if (currentRow.state != 'entity') {
                        currentRow.state = 'closed';
                    }
                }
                //currentRow.visible = visible;
                break;
            }
        }
    }

    for (i=0; i < db.report.length; i++) {
        currentRow = db.report[i];
        if (currentRow.id == id) {
            currentRow.state = state;
            break;
        }
    }
}

function refreshReport() {

}


function fillReportTable(data) {
    it_id = 0;

    for (i1=0; i1 < data.customersRssXml.length; i1++) {
        it_id++;
        customerJSON = data.customersRssXml;
        db.report.push(fillRowObject(customerJSON[i1], 'closed', 1, it_id, 0));
        level1Parent = it_id;

        for (i2=0; i2 < customerJSON[i1].accountsRssXml.length; i2++) {
            it_id++;
            accountJSON = customerJSON[i1].accountsRssXml;
            db.report.push(fillRowObject(accountJSON[i2], 'opened', 2, it_id, level1Parent));
            level2Parent = it_id;


            for (i3=0; i3 < accountJSON[i2].countriesRssXml.length; i3++) {
                it_id++;
                countryJSON = accountJSON[i2].countriesRssXml;
                db.report.push(fillRowObject(countryJSON[i3], 'opened', 3, it_id, level2Parent));
                level3Parent = it_id;


                for (i4=0; i4 < countryJSON[i3].networksRssXml.length; i4++) {
                    it_id++;
                    networkJSON = countryJSON[i3].networksRssXml;
                    db.report.push(fillRowObject(networkJSON[i4], 'opened', 4, it_id, level3Parent));
                    level4Parent = it_id;


                    for (i5=0; i5 < networkJSON[i4].vendorsRssXml.length; i5++) {
                        it_id++;
                        //alert(it_id);
                        vendorJSON = networkJSON[i4].vendorsRssXml;
                        db.report.push(fillRowObject(vendorJSON[i5], 'entity', 5, it_id, level4Parent));
                        window.incomingSum = window.incomingSum + vendorJSON[i5]['incoming_sum'];
                        window.outgoingSum = window.outgoingSum + vendorJSON[i5]['outgoing_sum'];
                        window.profit = window.profit + vendorJSON[i5]['profit'];
                    }
                }
            }
        }
    }
}


function fillRowObject(row, state, level, id, row_parent) {
    rowObject = {};
    rowObject['id'] = id;
    rowObject['row_parent'] = row_parent;
    rowObject['level'] = level;
    rowObject['state'] = state;
    rowObject['dimensions'] = row.name;
    rowObject['attempts'] = row.attempts_count;
    rowObject['attempts_success'] = row.attempts_success;
    if (row.client_price !== 0) {
        rowObject['client_price'] = row.client_price.toFixed(5);
    }
    if (row.vendor_price !== 0) {
        rowObject['vendor_price'] = row.vendor_price.toFixed(5);
    }
    rowObject['dlr'] = row.dlr.toFixed(2);
    rowObject['adt'] = row.adt.toFixed(0);
    rowObject['incoming_sum'] = row.incoming_sum.toFixed(2);
    rowObject['outgoing_sum'] = row.outgoing_sum.toFixed(2);
    rowObject['profit'] = row.profit.toFixed(2);
    rowObject['delivered'] = row.delivered;
    rowObject['undelivered'] = row.undelivered;
    rowObject['rejected'] = row.rejected;
    rowObject['other'] = row.other;
    if (state == 'opened' || state == 'entity') {
        rowObject['visible'] = false;
    } else {
        rowObject['visible'] = true;
    }

    return rowObject;
}


function refillVisibleArray() {
    db.report_visible = [];
    for (i=0; i < db.report.length; i++) {
        currentRow = db.report[i];
        if (currentRow.visible) {
            db.report_visible.push(currentRow);
        }

    }
}


function getRowsForUpdate(parentId, level) {
    startWriting = false;
    level = 0;
    returnArray = [];
    for (i=0; i < db.report.length; i++) {
        deleteRow = db.report[i];
        if (startWriting) {
            if (deleteRow.level <= level) {
                break;
            }
            returnArray.push(deleteRow);
        }

        if (deleteRow.id == parentId) {
            startWriting = true;
            level = deleteRow.level;
        }
    }
    return returnArray;
}