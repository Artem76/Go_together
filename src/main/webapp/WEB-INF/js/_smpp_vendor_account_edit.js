/**
 * Created by cmua7 on 02.09.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            return selectedItems;
        },

        insertItem: function (insertingItem) {

        },

        updateItem: function (UpdatingItem) {

        },

        deleteItem: function (deletingClient) {

        }
    };

    window.db = db;

    db.jsgrid_height = 0;

}());

$(document).ready(function () {

    $('#post_go').click(function () {
        $('#modal_save').modal('hide');
        saveSmppVendorAccount();
    });


    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_vendor').css('min-height', (heightWindow - 130) + 'px');
        if (document.flag_customer) {
            db.jsgrid_height = (((heightWindow - 280) > 200) ? (heightWindow - 280) : 200) + 'px';
        } else {
            db.jsgrid_height = (((heightWindow - 320) > 200) ? (heightWindow - 320) : 200) + 'px';
        }
    }

    if (document.getElementById('jsGrid-smpp-vendor-ips')) {
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
            var $result = $("<select>").attr("class", "form-control form-control-sm").css("height", "25.78px"),
                valueField = this.valueField,
                textField = this.textField,
                selectedIndex = this.selectedIndex;
            /*setTimeout(function () {
                $result.select2({
                    width: 200
                });
            });*/
            return $.each(this.items, function (index, item) {
                var value = valueField ? item[valueField] : index,
                    text = textField ? item[textField] : item,
                    $option = $("<option>").attr("value", value).text(text).appendTo($result);
                $option.prop("selected", selectedIndex === index);
            }), $result;
        }
    }),

        window.jsGrid_init = function () {
            $("#jsGrid-smpp-vendor-ips").jsGrid({
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
                    /*setTimeout(function () {
                        editControl.select.select2();
                    });*/

                    return editControl;
                },
                fields: [
                    {type: "control", width: 60},
                    {
                        title: "Allowed",
                        name: "allowed",
                        type: "checkbox",
                        sorting: false,
                        align: "center"
                    },
                    {title: "IP", name: "ip", type: "text", width: 150, align: "center"},
                    {title: "Port", name: "port", type: "text", width: 100, align: "center"},
                    {title: "System Id", name: "systemId", type: "text", width: 150, align: "center"},
                    {title: "Password", name: "password", type: "text", width: 150, align: "center"},
                    {title: "System-type", name: "systemType", type: "text", width: 150, align: "center"},
                    {title: "Submit Throughput", name: "submitThroughput", type: "text", width: 150, align: "center"},
                    {title: "CID", name: "cid", type: "text", width: 150, align: "center"},
                    {title: "Softswitch", name: "softswitchId", type: "select", items: softswitchList, valueField: "id", textField: "name", selectedIndex: "", width: 150},
                    {title: "Id", name: "id", type: "text", visible: false}
                ]
            });
        };
    window.jsGrid_init();

});

function saveSmppVendorAccount() {

    var result = {};

    $('input:not([type="checkbox"])').each(function () {
        result[$(this).attr("name")] = $(this).val();
    })

    $('input[type="checkbox"]').each(function () {
        result[$(this).attr("name")] = $(this).prop('checked');
    })

    $('select').each(function () {
        result[$(this).attr("name")] = $(this).val();
    })

    if ($('#dontSync').is(":checked"))
    {
        result['dontSync'] = true;
    } else {
        result['dontSync'] = false;
    }

    result['smppVendorIpsList'] = selectedItems;

    result['ipsLastNumber'] = selectedItems.length - 1;

    $.post('/admin_full_smpp_vendor_account_edit_save', result,
        function (data) {
            if ($.isEmptyObject(data)) {
                if (document.return_in_customer == 'yes'){
                    $(location).attr('href','/admin_full_customer_edit?id_customer=' + $('[name="id_customer"]').val() + '&error=&err_name=');
                }else {
                    $(location).attr('href','/admin_full_all_smpp_vendor_account_list');
                }
            } else {
                $('.tab-content .b-a-danger').removeClass('b-a-danger');
                $('#modal_error').modal('show');
                for (var i = 0, len = data.length; i < len; i++) {
                    $('[name="' + data[i] + '"]').addClass('b-a-danger');
                }
            }
        }, "json").fail(function () {
        location.reload();
    })

}