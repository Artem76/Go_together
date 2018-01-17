(function () {

    var db = {

        loadData: function (filter) {
            return document.numbers;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;

    document.numbers = [];
}());

$(document).ready(function () {
    var loadIndicator = new jsGrid.LoadIndicator();

    document.mcc_mnc = 0;


    //=============== Select ==============================

    var countryNetworkSelect = $(".js-example-basic-single").select2();


    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        db.jsgrid_height = (heightWindow - 190) + 'px';
    }

    if (document.getElementById('jsGrid-number-list')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }

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
            $("#jsGrid-number-list").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                editing: false,
                inserting: false,
                controller: db,
                loadIndication: true,
                rowClick: function (args) {
                    if (args.item.number == args.event.target.innerHTML || args.item.mcc == args.event.target.innerHTML || args.item.mnc == args.event.target.innerHTML || args.item.invalid == args.event.target.innerHTML) {
                        $('#invalid').prop("checked", args.item.invalid);
                        $('#number').val(args.item.number);
                        $('#mcc').val(args.item.mcc);
                        $('#mnc').val(args.item.mnc);
                        $('#id').val(args.item.id);
                        $("#modal_number").modal("show");
                    }
                },
                fields: [
                    {title: "Number", name: "number", type: "text", width: 200, align: "center"},
                    {title: "MCC", name: "mcc", type: "text", width: 100, align: "center"},
                    {title: "MNC", name: "mnc", type: "text", width: 100, align: "center"},
                    {
                        title: "Invalid",
                        name: "invalid",
                        type: "checkbox",
                        align: "center"
                    },
                    {title: "", name: "btn_del", type: "text", width: 100, align: "center"},
                    {title: "", name: "id", type: "text", visible: false},
                ],
            });
            $(".js-example-basic-single").select2();
        };
    window.jsGrid_init();
    refreshList();


    //========== Button =====================

    $("#refresh_list").click(function () {
        document.mcc_mnc = $("#countryNetwork_menu").val();
        refreshList();
    })


    function refreshList() {
        loadIndicator.show();
        document.numbers = [];
        $.post('/admin_full_test_numbers_pool', {
            mcc_mnc: document.mcc_mnc
        }, function (data) {
            document.numbers = [];
            for (var i = 0, len = data.length; i < len; i++) {
                var o2 = {};
                o2['id'] = data[i]["id"];
                o2['number'] = data[i]["number"];
                o2['mcc'] = data[i]["mcc"];
                o2['mnc'] = data[i]["mnc"];
                o2['invalid'] = (data[i]["invalid"] == "true") ? true : false;
                o2['btn_del'] = '<button type="button" class="btn btn-danger btn-sm mb-0-25 waves-effect waves-light btn_del" value="' + data[i]["id"] + '"><i class="ti-trash"></i> Delete</button>';
                document.numbers.push(o2);
            }
            console.log(data);
            window.jsGrid_init();
            loadIndicator.hide();
            $('#delete_go').val("");
            $('.btn_del').click(function () {
                $('#delete_go').val($(this).val());
                $('#modal_delete').modal("show");
            })
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        });
    }


    $("#button_edit").click(function () {
        $('#invalid').prop("checked", false);
        $('#number').val("");
        $('#mcc').val("");
        $('#mnc').val("");
        $('#id').val("0");
        $("#modal_number").modal("show");
    })

    $('#btn_fill').click(function () {
        loadIndicator.show();
        $.post('/admin_full_get_mcc_mnc_by_number', {
            number: $('#number').val()
        }, function (data) {
            $('#mcc').val(data.mcc);
            $('#mnc').val(data.mnc);
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        });
    })

    $('#save').click(function () {
        save();
    })

    function save() {
        loadIndicator.show();
        $('#modal_number .b-a-danger').removeClass('b-a-danger');
        var flag = true;
        $('#mcc').val($('#mcc').val().replace(/^0*/, ""));
        if ($('#mnc').val() == "0" || $('#mnc').val() == "00" || $('#mnc').val() == "000") {
            $('#mnc').val("0");
        } else {
            $('#mnc').val($('#mnc').val().replace(/^0*/, ""));
        }
        if ($('#mcc').val() == "" || $('#mcc').val() == "0" || $('#mcc').val().length != 3 || $('#mcc').val().match(/^[0-9][0-9][0-9]$/) == null) {
            $('#mcc').addClass('b-a-danger');
            loadIndicator.hide();
            flag = false;
        }
        if ($('#mnc').val() == "" || $('#mnc').val().length > 3 || $('#mnc').val().match(/^[0-9]*$/) == null) {
            $('#mnc').addClass('b-a-danger');
            loadIndicator.hide();
            flag = false;
        }
        if ($('#number').val() == "" || $('#number').val().match(/^[0-9]*$/) == null) {
            $('#number').addClass('b-a-danger');
            loadIndicator.hide();
            flag = false;
        }
        if (flag) {
            $.post('/admin_full_test_numbers_pool_save', {
                invalid: $('#invalid').is(":checked"),
                id: $('#id').val(),
                mcc: $('#mcc').val(),
                mnc: $('#mnc').val(),
                number: $('#number').val()

            }, function (data) {
                if ($.isEmptyObject(data)) {
                    refreshList();
                    $("#modal_number").modal("hide");
                } else {
                    for (var i = 0, len = data.length; i < len; i++) {
                        for (var i = 0, len = data.length; i < len; i++) {
                            $('#modal_number [name="' + data[i] + '"]').addClass('b-a-danger');
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

    $('#delete_go').click(function () {
        del($(this).val());
        $('#modal_delete').modal("hide");
        $('#delete_go').val("");
    })

    function del(id) {
        loadIndicator.show();
        $.post('/admin_full_test_numbers_pool_delete', {
            id: id
        }, function (data) {
            if ($.isEmptyObject(data)) {
                refreshList();
            } else {
                refreshList();
            }
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        });
    }
});

