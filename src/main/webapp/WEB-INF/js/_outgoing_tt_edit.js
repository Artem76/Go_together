(function () {

    var db = {

        loadData: function (filter) {
            return selectedItems;
        },

        insertItem: function (insertingItem) {
            $.post('/admin_full_get_message_by_id', {
                msgid: insertingItem.msgid
            }, function (data) {
                insertingItem.destination_addr = data['destination_addr'];
                insertingItem.vendor_name = data['vendor_name'];
                insertingItem.vendor_msgid = data['vendor_msgid'];
                insertingItem.vendor_account_id = data['vendor_account_id'];
                insertingItem.messageLogForTT_id = data['messageLogForTT_id'];
                window.jsGrid_init();
            }, "json").fail(function () {
                insertingItem.destination_addr = "Not found";
                insertingItem.vendor = "Not found";
                insertingItem.vendor_msgid = "Not found";
                insertingItem.messageLogForTT_id = 0;
                window.jsGrid_init();
            })
        },

        updateItem: function (updatingItem) {
            $.post('/admin_full_get_message_by_id', {
                msgid: updatingItem.msgid
            }, function (data) {
                updatingItem.destination_addr = data['destination_addr'];
                updatingItem.vendor_name = data['vendor_name'];
                updatingItem.vendor_msgid = data['vendor_msgid'];
                updatingItem.vendor_account_id = data['vendor_account_id'];
                window.jsGrid_init();
            }, "json").fail(function () {
                updatingItem.destination_addr = "Not found";
                updatingItem.vendor_name = "Not found";
                updatingItem.vendor_msgid = "Not found";
                window.jsGrid_init();
            })
        },

        deleteItem: function (deletingClient) {

        }
    };

    window.db = db;

    db.jsgrid_height = 0;

}());



$(document).ready(function () {

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_tt').css('min-height', (heightWindow - 195) + 'px');
        $('.box.bg-white.messenger').css('height', (heightWindow - 240) + 'px');
        $('.m-chat').css('height', (heightWindow - (175 + 65)) + 'px');
        $('.emails').css('height', (heightWindow - (175 + 154 + 28 + 65)) + 'px');
        db.jsgrid_height = (((heightWindow - 525) > 200) ? (heightWindow - 525) : 200) + 'px';
    }

    if (document.getElementById('jsGrid-outgoing-tt-complain-list')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }

    $('a[role="tab"]').click(function () {
        setTimeout(function () {
            $('#emails').scrollTop($('#emails').height());
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
            $("#jsGrid-outgoing-tt-complain-list").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                editing: true,
                inserting: true,
                pageSize: 15,
                pageButtonCount: 5,
                deleteConfirm: "Do you really want to delete the client?",
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
                    {title: "Message ID", name: "msgid", type: "text", width: 70, align: "center"},
                    {
                        title: "Issue",
                        name: "issue",
                        type: "select",
                        items: issueList,
                        valueField: "value",
                        textField: "name",
                        selectedIndex: "Other",
                        width: 150
                    },
                    {
                        title: "Destination address",
                        name: "destination_addr",
                        type: "text",
                        width: 70,
                        align: "center",
                        editing: false,
                        inserting: false
                    },
                    {
                        title: "Vendor",
                        name: "vendor_name",
                        type: "text",
                        width: 70,
                        align: "center",
                        editing: false,
                        inserting: false
                    },
                    {title: "Vendor", name: "vendor_account_id", type: "text", visible: false},
                    {
                        title: "Vendor message id",
                        name: "vendor_msgid",
                        type: "text",
                        width: 70,
                        align: "center",
                        editing: false,
                        inserting: false
                    },
                    {title: "messageLogForTT_id", name: "messageLogForTT_id", type: "number", visible: false}
                ]
            });
            $(".js-example-basic-single").select2();
        };
    window.jsGrid_init();

});

var selectedItems = [];

var id_tt = document.getElementById("id_tt");
var idValue = id_tt.value;
if (idValue != 0) {
    $.post('/admin_full_get_messages_by_outgoing_tt_id', {
        tt_id: idValue
    }, function (data) {
        for (var i = 0, len = data.length; i < len; i++) {
            jsonRow = data[i];
            newMsgRow = {};
            newMsgRow['msgid'] = jsonRow['msgid'];
            newMsgRow['issue'] = jsonRow['issue'];
            newMsgRow['destination_addr'] = jsonRow['destination_addr'];
            newMsgRow['vendor_name'] = jsonRow['vendor_name'];
            newMsgRow['vendor_msgid'] = jsonRow['vendor_msgid'];
            newMsgRow['vendor_account_id'] = jsonRow['vendor_account_id'];
            newMsgRow['messageLogForTT_id'] = jsonRow['messageLogForTT_id'];
            selectedItems.push(newMsgRow);
        }
        window.jsGrid_init();
    }, "json").fail(function () {
    })
}


//=========Save OutgoingTT and Send first email====================

document.result = {};

$('#first_email_send').click(function () {
    $('#modal_first_email').modal('hide');
    document.result["first_email"] = true;
    postSaveTT();
});

$('#first_email_cancel').click(function () {
    $('#modal_first_email').modal('hide');
    document.result["first_email"] = false;
    postSaveTT();
});

function saveTT() {

    document.result = {};

    document.result["first_email"] = false;

    $('.form_outgoingTT input').each(function () {
        document.result[$(this).attr("name")] = $(this).val();
    })

    $('.form_outgoingTT select').each(function () {
        document.result[$(this).attr("name")] = $(this).val();
    })

    document.result['messageLogForTTList'] = {};

    document.result['arrLastNumber'] = -1;

    $.each(selectedItems, function (number, item) {
        document.result.messageLogForTTList[number + "msgid"] = item.msgid;
        document.result.messageLogForTTList[number + "issue"] = item.issue;
        document.result.messageLogForTTList[number + "messageLogForTT_id"] = item.messageLogForTT_id;
        document.result.arrLastNumber = number;
    })

    if (document.emailsEmpty){
        $('#modal_first_email').modal('show');
    }else{
        postSaveTT();
    }
}

function postSaveTT() {
    $.post('/admin_full_save_outgoing_tt', document.result,
        function (data) {
            if ($.isEmptyObject(data)) {
                $(location).attr('href', '/admin_full_outgoing_tt_list');
            } else {
                console.log(data)
                $('.tab-content .b-a-danger').removeClass('b-a-danger');
                $('#modal_error').modal('show');
                for (var i = 0, len = data.length; i < len; i++) {
                    $('[name="' + data[i] + '"]').addClass('b-a-danger');
                    if (data[i] == "customer_account"){
                        $('[aria-labelledby="select2-customer_account-container"]').addClass('b-a-danger');
                    }
                }
            }
        }, "json").fail(function () {
        location.reload();
    })
}


//============== New email ================

$('#email_send').click(function () {
    $('#modal_send').modal('hide');
    new_email();
});

var new_email = function () {
    var body_email = $('.new_email_body').val();
    $('.new_email_body').val("");

    if (body_email != null && body_email != "") {
        $.post('/admin_full_outgoing_tt_new_email', {
            id_tt: document.id_tt,
            body_email: body_email
        }, function (data) {
            var frag = '<div class="mc-item right clearfix">' +
                '<div class="mc-content">' +
                '<div class="email_content">' +
                data.body_email_content +
                '</div>' +
                '<a href="/admin_full_email_content_view?id_email_content=' + data.id_email_content + '">' +
                '<div class="font-90 text-xs-right text-muted">Open full e-mail (from: ' + data.from_email_content + ' ' + data.date_email_content + ')</div>' +
                '</a>' +
                '</div>' +
                '</div>';
            $('.emails').append(frag);
            $('#emails').scrollTop($('#emails').height());
        }, "json").fail(function () {
            location.reload();
        })
    }
}