/**
 * Created by АРТЕМ on 07.08.2017.
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
        saveVendorDialpeer();
    });

    $('#post_delete').click(function () {
        $('#modal_delete').modal('hide');
        deleteDialpeer();
    });


    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_dialpeer').css('min-height', (heightWindow - 97) + 'px');
        db.jsgrid_height = (((heightWindow - 210) > 200) ? (heightWindow - 210) : 200) + 'px';
    }

    if (document.getElementById('jsGrid-systemId')) {
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
            $("#jsGrid-systemId").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                editing: true,
                inserting: true,
                pageSize: 15,
                pageButtonCount: 5,
                deleteConfirm: "Do you really want to delete the system id?",
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
                        title: "System Id",
                        name: "id_smppVendorIps",
                        type: "select",
                        items: smppVendorIpsList,
                        valueField: "id",
                        textField: "name",
                        selectedIndex: 0,
                        width: 150,
                        align: "center"
                    },
                    {title: "Weight", name: "weight", type: "number", width: 150, align: "center"},
                    {title: "Id", name: "id", type: "number", visible: false}
                ]
            });
        };
    window.jsGrid_init();

});

function saveVendorDialpeer() {

    var result = {};

    $('input').each(function () {
        result[$(this).attr("name")] = $(this).val();
    })

    result['vendorDialpeerChildList'] = selectedItems;

    result['vendorDialpeerChildLastNumber'] = selectedItems.length - 1;

    $.post('/admin_full_vendor_dialpeer_edit_save', result,
        function (data) {
            if ($.isEmptyObject(data)) {
                $(location).attr('href', '/admin_full_vendor_dialpeer_list');
            } else {
                $('.content_dialpeer .b-a-danger').removeClass('b-a-danger');
                $('#modal_error').modal('show');
                for (var i = 0, len = data.length; i < len; i++) {
                    $('[name="' + data[i] + '"]').addClass('b-a-danger');
                }
            }
        }, "json").fail(function () {
        location.reload();
    })
}

var deleteDialpeer = function () {
    location = '/admin_full_vendor_dialpeer_delete?id_vendor_dialpeer=' + document.id_vendor_dialpeer;
}
