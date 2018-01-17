
(function () {

    var db = {

        loadData: function (filter) {
            return this.tts;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;

    db.tts = [];

}());

$(document).ready(function () {

    var date_start;
    var date_end;

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

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        //$('#routing_list').css('min-height', (heightWindow - 97) + 'px');
        db.jsgrid_height = (heightWindow - 130) + 'px';
    }

    if (document.getElementById('jsGrid-incoming-tt')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }


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
            $("#jsGrid-incoming-tt").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                pageSize: 15,
                pageButtonCount: 5,
                deleteConfirm: "Do you really want to delete the client?",
                controller: db,
                loadIndication: true,
                rowClick: function(args) {
                    document.location.href = "/admin_full_incoming_tt_edit?id_incoming_tt=" + args.item.Id;
                },
                editTemplate: function(value) {
                    var editControl = this._editControl = this._createSelect(value);
                    setTimeout(function() {
                        editControl.select.select2();
                    });

                    return editControl;
                },
                fields: [
                    {title: "Ref. number", name: "Id", type: "text", width: 100, align: "center"},
                    {title: "Customer", name: "Customer", type: "text", width: 200, align: "center"},
                    {title: "Subject", name: "Subject", type: "text", width: 400, align: "center"},
                    {title: "User (Opened)", name: "UserOpened", type: "text", width: 120, align: "center"},
                    {title: "User (Closed)", name: "UserClosed", type: "text", width: 120, align: "center"},
                    {title: "Date (Opened)", name: "DateOpened", type: "text", width: 120, align: "center"},
                    {title: "Date (Closed)", name: "DateOpened", type: "text", width: 120, align: "center"},
                    {itemTemplate: function(value, item) {
                        var iconButton = "";
                        if (value == "Opened") {
                            iconButton = $('<button>', {
                                class: 'btn btn-danger btn-sm label-left b-a-0 waves-effect waves-light',
                                type: 'button',
                                id: 'test1',
                                html: '<span class="btn-label"><i class="ti-plus"></i></span>Opened',
                                style: 'height: 20px; width: 100px;'
                            });
                        } else if (value == "Closed" || value == "Closed (problem on our side)"){
                            iconButton = $('<button>', {
                                class: 'btn btn-success btn-sm label-left b-a-0 waves-effect waves-light',
                                type: 'button',
                                id: 'test1',
                                html: '<span class="btn-label"><i class="ti-check"></i></span>Closed',
                                style: 'height: 20px; width: 100px;'
                            });
                        } else if (value == "Forwarded to vendor"){
                            iconButton = $('<button>', {
                                class: 'btn btn-warning  btn-sm label-left b-a-0 waves-effect waves-light',
                                type: 'button',
                                id: 'test1',
                                html: '<span class="btn-label"><i class="ti-angle-double-right"></i></span>Forwarded',
                                style: 'height: 20px; width: 100px;'
                            });
                        }
                        return iconButton;
                    }, name: "Status", type: "text", width: 130, align: "center"}
                ]
            });
            $(".js-example-basic-single").select2();
        };
    window.jsGrid_init();
    getIncomingTTByFilteres();
});


function getIncomingTTByFilteres() {

    var dates = $('input[name="daterange-with-time"]').val().split(' - ');
    date_start = dates[0];
    date_end = dates[1];

    var customerAccountSelect = document.getElementById("customer");
    var customerValue = customerAccountSelect.options[customerAccountSelect.selectedIndex].value;

    db.tts = [];
    window.jsGrid_init();
    $.getJSON('/admin_full_get_incoming_tt_history_list',
        {
            customer_id: customerValue,
            startDate: date_start,
            endDate: date_end
        }
        ,
        function (data) {
            for (var i = 0, len = data.length; i < len; i++) {
                var o2 = {};
                o2['Id'] = data[i]["id"];
                o2['Customer'] = data[i]["customer"];
                o2['DateOpened'] = data[i]["dateOpened"];
                o2['DateClosed'] = data[i]["dateClosed"];
                o2['UserOpened'] = data[i]["userOpened"];
                o2['UserClosed'] = data[i]["userClosed"];
                o2['Subject'] = data[i]["subject"];
                o2['Status'] = data[i]["status"];
                db.tts.push(o2);
                window.jsGrid_init();
        }
    })

}
