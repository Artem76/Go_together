(function () {

    var db = {

        loadData: function (filter) {
            return window.rates;
        },

        insertItem: function (insertingItem) {
            $.post('/admin_full_get_country_network_by_mcc_mnc', {
                mcc: insertingItem.mcc,
                mnc: insertingItem.mnc
            }, function (data) {
                if (insertingItem.mcc !== "" && insertingItem.mnc == "") {
                    insertingItem.country = data['country'];
                    insertingItem.network = "FLAT";
                } else {
                    insertingItem.country = data['country'];
                    insertingItem.network = data['network'];
                }
                window.jsGrid_init();
            }, "json").fail(function () {
                insertingItem.country = "Not found";
                insertingItem.network = "Not found";
                window.jsGrid_init();
            })
        },

        updateItem: function (updatingItem) {
            $.post('/admin_full_get_country_network_by_mcc_mnc', {
                mcc: updatingItem.mcc,
                mnc: updatingItem.mnc
            }, function (data) {
                if (updatingItem.mcc !== "" && updatingItem.mnc == "") {
                    updatingItem.country = data['country'];
                    updatingItem.network = "FLAT";
                } else {
                    updatingItem.country = data['country'];
                    updatingItem.network = data['network'];
                }
                window.jsGrid_init();
            }, "json").fail(function () {
                updatingItem.country = "Not found";
                updatingItem.network = "Not found";
                window.jsGrid_init();
            })
        },

        deleteItem: function (deletingItem) {

        }
    };

    window.db = db;

    db.jsgrid_height = 0;


    var db_email_list = {

        loadData: function (filter) {
            return window.email_list;
        }
    };

    window.db_email_list = db_email_list;

    db_email_list.jsgrid_height = 0;
}());

function fillOpenedCodes(mode) {
    $.get('/admin_full_get_opened_mcc_by_account', {
        account_id: $("#account_id").val(),
        mcc: $("#country_id").val(),
        mode: mode
    }, function (data) {
        for (i = 0; i < data.length; i++) {
            refbook = data[i];
            if (!rowExists(data[i].mcc, data[i].mnc)) {
                newRow = {
                    "mcc": data[i].mcc,
                    "mnc": data[i].mnc,
                    "country": data[i].country,
                    "network": data[i].network
                };
                window.rates.push(newRow);
            }
        }
        window.jsGrid_init();
    }, "json").fail(function () {
        window.jsGrid_init();
    })
}

function rowExists(mcc, mnc) {
    returnValue = false;
    for (j = 0; j < window.rates.length; j++) {
        if (window.rates[j].mcc == mcc && window.rates[j].mnc == mnc) {
            returnValue = true;
            break;
        }
    }

    return returnValue;
}

$(document).ready(function () {
    var loadIndicator = new jsGrid.LoadIndicator();

    $('#textEmail').summernote({
        popover: false
    });

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('#content_rn').css('min-height', (heightWindow - 190) + 'px');
        db.jsgrid_height = (((heightWindow - 310) > 200) ? (heightWindow - 310) : 200) + 'px';
        db_email_list.jsgrid_height = (((heightWindow - 230) > 200) ? (heightWindow - 230) : 200) + 'px';
        $(".note-editable").css('min-height', (heightWindow - 310) + 'px');
        $("[name='textEmail']").css('min-height', (heightWindow - 270) + 'px');
    }

    if (document.getElementById('jsGrid-rate-list')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }

    $('a[role="tab"]').click(function () {
        setTimeout(function () {
            $("#jsGrid-email-list").jsGrid("refresh");
        }, 10);
    })

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

    var dateTimeField = function (config) {
        jsGrid.Field.call(this, config);
    };
    dateTimeField.prototype = new jsGrid.Field({

        itemTemplate: function (value) {
            if (value === null) {
                return '';
            } else {
                return value;
            }
        },

        insertTemplate: function (value) {
            this._insertInput = $('<input>').attr({
                type: "text",
                placeholder: "",
                "data-mask": "99-99-2099 99:99:99",
                class: "form-control"
            })
            this._insertInput.val("");
            return this._insertInput;
        },

        editTemplate: function (value) {
            this._editInput = $('<input>').attr({
                type: "text",
                placeholder: "",
                "data-mask": "99-99-2099 99:99:99",
                class: "form-control"
            })
            this._editInput.val(value);
            return this._editInput;
        },

        insertValue: function () {
            var insertValue = this._insertInput.val();
            return insertValue;
        },

        editValue: function () {
            var editValue = this._editInput.val();
            return editValue;
        }
    });

    jsGrid.fields.date = dateTimeField;

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
            $("#jsGrid-rate-list").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                editing: document.insAndEdit,
                inserting: document.insAndEdit,
                pageSize: 15,
                pageButtonCount: 5,
                deleteConfirm: "Do you really want to delete the rate?",
                controller: db,
                loadIndication: true,
                editTemplate: function (value) {
                    var editControl = this._editControl = this._createSelect(value);
                    setTimeout(function () {
                        editControl.select.select2();
                    });

                    return editControl;
                },
                fields: [
                    {type: "control", width: 20},
                    {
                        title: "Country",
                        name: "country",
                        type: "text",
                        width: 50,
                        align: "center",
                        editing: false,
                        inserting: false
                    },
                    {
                        title: "MCC", name: "mcc", type: "text", width: 20, align: "center", validate: {
                        validator: "pattern",
                        message: "Please enter a valid mcc, example (255)",
                        param: /^[1-9][0-9]{2}$/
                    }
                    },
                    {
                        title: "Network",
                        name: "network",
                        type: "text",
                        width: 50,
                        align: "center",
                        editing: false,
                        inserting: false
                    },
                    {
                        title: "MNC", name: "mnc", type: "text", width: 20, align: "center", validate: {
                        validator: "pattern",
                        message: "Please enter a valid mnc, example (254 or 25...)",
                        param: /^[1-9][0-9]{0,2}$|^$/
                    }
                    },
                    {title: "Price", name: "rate", type: "text", width: 30, align: "center", filtering: false},
                    {
                        title: "Effective date, example (31-01-2017 23:59:59)",
                        name: "date",
                        type: "date",
                        align: "center",
                        validate: {
                            validator: "pattern",
                            message: "Please enter a valid date, example (31-01-2017 23:59:59)",
                            param: /^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(20|21)\d\d\ ([0-1]\d|2[0-3])(:[0-5]\d){2}$/
                        }
                    }
                ]
            });
            $(".js-example-basic-single").select2();
        };

    window.jsGrid_init();

    window.jsGrid_init_email_list = function () {
        $("#jsGrid-email-list").jsGrid({
            height: db_email_list.jsgrid_height,
            width: "100%",
            autoload: true,
            editing: false,
            inserting: false,
            controller: db_email_list,
            loadIndication: true,
            fields: [
                {
                    title: "Email",
                    name: "email",
                    type: "text",
                    width: 200,
                    align: "center",
                    editing: false,
                    inserting: false
                },
                {
                    title: "Status",
                    name: "status",
                    type: "text",
                    width: 50,
                    align: "center",
                    editing: false,
                    inserting: false
                }
            ]
        });
    };

    window.jsGrid_init_email_list();


    $('#get_data_excel').click(function () {
        $('#modal_table').modal('hide');
        loadIndicator.show();
        window.rates = [];
        $.post('/admin_full_get_rn_history_list_full',
            document.get_data_excel(),
            function (data) {
                console.log(data);
                window.rates = data;
                window.jsGrid_init();
                loadIndicator.hide();
            }, "json").fail(function () {
            loadIndicator.hide();
            location.reload();
        });
    })

    
    

    $('#post_go').click(function () {
        $('#modal_save').modal('hide');
        saveRN();
    });
    
    $('#send_email').click(function () {
        $('#modal_send').modal('hide');
        sendEmail();
    });
    
    $('#save_send').click(function () {
        $('#modal_save_send').modal('hide');
        saveRNAndSandEmail();
    })

    function saveRN() {
        loadIndicator.show();
        var result = dataSave();
        console.log(result);
        $.post('/admin_full_save_client_rn_update', result,
            function (data) {
                if ($.isEmptyObject(data.errors)) {
                    $(location).attr('href', '/admin_full_client_rn_list');
                } else {
                    console.log(data.errors)
                    $('#content .b-a-danger').removeClass('b-a-danger');
                    $('#modal_error').modal('show');
                    for (var i = 0, len = data.errors.length; i < len; i++) {
                        $('[name="' + data.errors[i] + '"]').addClass('b-a-danger');
                    }
                }
                loadIndicator.hide();
            }, "json").fail(function () {
            loadIndicator.hide();
            location.reload();
        })
    }
    
    function sendEmail() {
        loadIndicator.show();
        $.post('/admin_full_client_rn_send_email',
            {
                id_rn: document.id_rn
            },
            function (data) {
                if ($.isEmptyObject(data)) {
                    $(location).attr('href', '/admin_full_client_rn_list');
                } else {
                    console.log(data);
                    $('#myModalErrorSend').html(data.error);
                    $('#modal_error_send').modal('show');
                }
                loadIndicator.hide();
            }, "json").fail(function () {
            loadIndicator.hide();
            location.reload();
        })
    }
    
    function saveRNAndSandEmail() {
        loadIndicator.show();
        var result = dataSave();
        console.log(result);
        $.post('/admin_full_save_client_rn_update', result,
            function (data) {
                if ($.isEmptyObject(data.errors)) {
                    document.id_rn = data.id;
                    sendEmail();
                }else {
                    console.log(data.errors)
                    $('#content .b-a-danger').removeClass('b-a-danger');
                    $('#modal_error').modal('show');
                    for (var i = 0, len = data.errors.length; i < len; i++) {
                        $('[name="' + data.errors[i] + '"]').addClass('b-a-danger');
                    }
                }
                loadIndicator.hide();
            }, "json").fail(function () {
            loadIndicator.hide();
            location.reload();
        })
    }

    function dataSave() {
        var result = {};
        $('.form_rn input').each(function () {
            result[$(this).attr("name")] = $(this).val();
        })

        $('.form_rn select').each(function () {
            result[$(this).attr("name")] = $(this).val();
        })

        result["textEmail"] = $('#textEmail').summernote('code');

        result['rates'] = {};

        result['arrLastNumber'] = -1;

        $.each(window.rates, function (number, item) {
            result.rates[number + "mcc"] = item.mcc;
            result.rates[number + "mnc"] = item.mnc;
            result.rates[number + "rate"] = item.rate;
            result.rates[number + "date"] = item.date;
            result.arrLastNumber = number;
        })

        return result;
    }

});




