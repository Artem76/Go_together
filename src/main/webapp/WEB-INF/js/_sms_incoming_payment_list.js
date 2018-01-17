(function () {

    var db = {

        loadData: function (filter) {
            return document.payments;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;

    document.payments = [];
}());

$(document).ready(function () {
    var loadIndicator = new jsGrid.LoadIndicator();

    document.page_index = 0;
    document.page_size = 50;
    document.date_start_now = document.date_start;
    document.date_end_now = document.date_end;
    document.customer_id = 0;


    //=============== Select ==============================

    $('#customer_menu').select2();
    var customerSelect = $('#customer_id').select2({
        dropdownParent: $('#modal_payment')
    });


    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        db.jsgrid_height = (heightWindow - 190) + 'px';
    }

    if (document.getElementById('jsGrid-payment-list')) {
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
            'Current month': [moment().utc().startOf('month'), moment().utc().endOf('month')],
            'Current year': [moment().utc().startOf('year'), moment().utc().endOf('year')]
        }
    }, function (start, end, lable) {
        document.date_start = start.format('YYYY-MM-DD HH:mm:ss');
        document.date_end = end.format('YYYY-MM-DD HH:mm:ss');
    });

    var start = moment().subtract(365, 'days');
    var end = moment();

    function cb(start, end) {
        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));

    }

    cb(start, end);


    $('#datetimepicker').datetimepicker({
        icons: {
            time: "fa fa-clock-o",
            date: "fa fa-calendar",
            up: "fa fa-arrow-up",
            down: "fa fa-arrow-down",
            next: "fa fa-arrow-right",
            previous: "fa fa-arrow-left"
        },
        format: 'YYYY-MM-DD HH:mm:ss'
    });


    //=============== jsGrid ==============================
    var select2 = function (config) {
        jsGrid.Field.call(this, config);
    };

    select2.prototype = new jsGrid.Field({

        itemTemplate: function (value) {
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
            setTimeout(function () {
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
            $("#jsGrid-payment-list").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                editing: false,
                inserting: false,
                controller: db,
                loadIndication: true,
                rowClick: function (args) {
                    $('#confirmed').prop("checked", args.item.confirmed);
                    customerSelect.val(args.item.customerId).trigger("change");
                    $('#datetimepicker>input').val(args.item.date);
                    $('#sum').val(args.item.sum);
                    $('#payment_id').val(args.item.id);
                    $("#modal_payment").modal("show");
                },
                fields: [
                    {title: "Customer", name: "customer", type: "text", width: 200, align: "center"},
                    {title: "Sum", name: "sum", type: "text", width: 100, align: "center"},
                    {title: "Date", name: "date", type: "text", width: 200, align: "center"},
                    {
                        title: "Confirmed",
                        name: "confirmed",
                        type: "checkbox",
                        align: "center"
                    },
                    {title: "", name: "id", type: "text", visible: false},
                    {title: "", name: "customerId", type: "text", visible: false}
                ],
            });
            $(".js-example-basic-single").select2();
        };
    window.jsGrid_init();
    refreshList();


    //========== Button =====================

    $("#refresh_list").click(function () {
        document.page_index = 0;
        $('#btn_previous').addClass("disabled");
        $('#btn_next').addClass("disabled");
        document.customer_id = $("#customer_menu").val();
        document.date_start_now = document.date_start;
        document.date_end_now = document.date_end;
        refreshList();
    })

    $('#btn_previous').click(function () {
        if (!$('#btn_previous').hasClass("disabled") && document.page_index != 0) {
            document.page_index = document.page_index - 1;
            refreshList();
        }
    })

    $('#btn_next').click(function () {
        if (!$('#btn_next').hasClass("disabled")) {
            document.page_index = document.page_index + 1;
            refreshList();
        }
    })


    function refreshList() {
        loadIndicator.show();
        document.payments = [];
        $.post('/admin_full_sms_incoming_payment_list', {
            customer_id: document.customer_id,
            date_start: document.date_start_now,
            date_end: document.date_end_now,
            page_index: document.page_index,
            page_size: document.page_size

        }, function (data) {
            document.payments = [];
            var payments = data.payments;
            for (var i = 0, len = payments.length; i < len; i++) {
                var o2 = {};
                o2['id'] = payments[i]["id"];
                o2['customer'] = payments[i]["customer"];
                o2['customerId'] = payments[i]["customerId"];
                o2['sum'] = payments[i]["sum"];
                o2['date'] = payments[i]["date"];
                o2['confirmed'] = (payments[i]["confirmed"] == "true") ? true : false;
                document.payments.push(o2);
            }
            if (document.page_index < 1) {
                $('#btn_previous').addClass("disabled");
            }else {
                $('#btn_previous').removeClass("disabled");
            }
            if (data.next == "true" || data.next == true) {
                $('#btn_next').removeClass("disabled");
            } else {
                $('#btn_next').addClass("disabled");
            }
            console.log(data);
            window.jsGrid_init();
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        });
    }


    $("#button_edit").click(function () {
        $('#confirmed').prop("checked", false);
        customerSelect.val("0").trigger("change");
        var date = new Date();
        $('#datetimepicker>input').val(date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2) + ' ' + ('0' + (date.getHours())).slice(-2) + ':' + ('0' + (date.getMinutes())).slice(-2) + ':' + ('0' + (date.getSeconds())).slice(-2));
        $('#sum').val("");
        $('#payment_id').val("0");
        $("#modal_payment").modal("show");
    })

    $('#save').click(function () {
        save();
    })

    function save() {
        loadIndicator.show();
        $('#modal_payment .b-a-danger').removeClass('b-a-danger');
        if ($('#sum').val() == 0 || $('#sum').val() == "0" || $('#sum').val() == "") {
            $('#sum').val('0');
        }
        if ($('#customer_id').val() == 0){
            $('[aria-labelledby="select2-customer_id-container"]').addClass('b-a-danger');
            loadIndicator.hide();
        }else {
            $.post('/admin_full_sms_incoming_payment_save', {
                confirmed: $('#confirmed').is(":checked"),
                customer_id: $('#customer_id').val(),
                date: $('#datetimepicker>input').val(),
                sum: $('#sum').val(),
                payment_id: $('#payment_id').val()

            }, function (data) {
                if ($.isEmptyObject(data)) {
                    document.page_index = 0;
                    refreshList();
                    $("#modal_payment").modal("hide");
                } else {
                    for (var i = 0, len = data.length; i < len; i++) {
                        if (data[i] == "customer_id"){
                            $('[aria-labelledby="select2-customer_id-container"]').addClass('b-a-danger');
                        }
                    }
                }
                loadIndicator.hide();
            }, "json").fail(function () {
                location.reload();
                loadIndicator.hide();
            });
        }
    }

});

