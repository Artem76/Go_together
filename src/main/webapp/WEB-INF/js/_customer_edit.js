/**
 * Created by АРТЕМ on 08.09.2017.
 */
(function () {

    var dbVendor = {

        loadData: function (filter) {
            return vendorList;
        }
    };

    window.dbVendor = dbVendor;

    dbVendor.jsgrid_height = 0;

    var dbClient = {

        loadData: function (filter) {
            return clientList;
        }
    };

    window.dbClient = dbClient;

    dbClient.jsgrid_height = 0;


    var dbAttachment = {

        loadData: function (filter) {
            return attachmentList;
        }
    };

    window.dbAttachment = dbAttachment;

}());


$(document).ready(function () {

    var loadIndicator = new jsGrid.LoadIndicator();

    $('#post_go').click(function () {
        $('#modal_save').modal('hide');
        saveCustomer();
    });

//=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_customer').css('min-height', (heightWindow - 130) + 'px');
        dbVendor.jsgrid_height = dbClient.jsgrid_height = (((heightWindow - 210) > 200) ? (heightWindow - 210) : 200) + 'px';
    }

    if (document.getElementsByClassName('content_customer')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }

    $('a[role="tab"]').click(function () {
        setTimeout(function () {
            $("#jsGrid-smpp-vendor-accounts").jsGrid("refresh");
            $("#jsGrid-smpp-client-accounts").jsGrid("refresh");
        }, 10);
    })

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
        }
    })

    window.jsGrid_init_vendor = function () {
        $("#jsGrid-smpp-vendor-accounts").jsGrid({
            height: dbVendor.jsgrid_height,
            width: "100%",
            autoload: true,
            pageSize: 15,
            pageButtonCount: 5,
            controller: dbVendor,
            loadIndication: true,
            rowClick: function (args) {
                document.location.href = "/admin_full_smpp_vendor_account_edit?id_vendor_account=" + args.item.id + "&id_customer=" + $('[name="id_customer"]').val() + "&error=&err_name=&err_ips=&return_in_customer=yes";
            },
            fields: [
                {title: "Name", name: "name", type: "text", width: 100, align: "center"},
                {title: "System Id", name: "systemId", type: "text", width: 200, align: "center"},
                {title: "Id", name: "id", type: "text", visible: false},
            ],
        });
    };


    window.jsGrid_init_client = function () {
        $("#jsGrid-smpp-client-accounts").jsGrid({
            height: dbClient.jsgrid_height,
            width: "100%",
            autoload: true,
            pageSize: 15,
            pageButtonCount: 5,
            controller: dbClient,
            loadIndication: true,
            rowClick: function (args) {
                document.location.href = "/admin_full_smpp_client_account_edit?id_client_account=" + args.item.id + "&id_customer=" + $('[name="id_customer"]').val() + "&error=&err_name=&err_ips=&err_si=&return_in_customer=yes";
            },
            fields: [
                {title: "Name", name: "name", type: "text", width: 100, align: "center"},
                {title: "System Id", name: "systemId", type: "text", width: 200, align: "center"},
                {title: "Id", name: "id", type: "text", visible: false},
            ],
        });


    };

    window.jsGrid_init_attachment = function () {
        $("#jsGrid-attachment-list").jsGrid({
            width: "100%",
            autoload: true,
            controller: dbAttachment,
            fields: [
                {title: "Attachment", name: "name", align: "center"},
                {title: "", name: "btn_del", type: "text", width: 70, align: "center"},
                {title: "Id", name: "id", visible: false}
            ],
        });
    };

    window.jsGrid_init_attachment();
    window.jsGrid_init_vendor();
    window.jsGrid_init_client();


    function saveCustomer() {

        var result = {};

        $('#Profile input').each(function () {
            result[$(this).attr("name")] = $(this).val();
        })

        $('#Profile select').each(function () {
            result[$(this).attr("name")] = $(this).val();
        })

        $.post('/admin_full_customer_edit_save', result,
            function (data) {
            console.log(data);
                if ($.isEmptyObject(data.errors)) {
                    if (document.id_customer == 0){
                        $(location).attr('href','/admin_full_customer_edit?id_customer=' + data.id);
                    }else {
                        $(location).attr('href','/admin_full_customer_list');
                    }

                } else {
                    $('#Profile .b-a-danger').removeClass('b-a-danger');
                    $('#modal_error').modal('show');
                    for (var i = 0, len = data.errors.length; i < len; i++) {
                        $('[name="' + data.errors[i] + '"]').addClass('b-a-danger');
                        $('[aria-labelledby="select2-' + data.errors[i] + '-container"]').addClass('b-a-danger');
                    }
                }
            }, "json").fail(function () {
            // location.reload();
        })

    }

    $('#delete_go').val("");
    $('.btn_del').click(function () {
        $('#delete_go').val($(this).val());
        $('#modal_delete').modal("show");
    })

    $('#delete_go').click(function () {
        del($(this).val());
        $('#modal_delete').modal("hide");
        $('#delete_go').val("");
    })

    function del(id) {
        loadIndicator.show();
        $.post('/admin_full_customer_attachment_delete', {
            id: id
        }, function (data) {
            console.log(data);
            refreshList();
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        });
    }

    function refreshList() {
        loadIndicator.show();
        attachmentList = [];
        $.post('/admin_full_customer_attachment_list', {
            id: document.id_customer
        }, function (data) {
            attachmentList = [];
            for (var i = 0, len = data.length; i < len; i++) {
                var o2 = {};
                o2['id'] = data[i]["id"];
                o2['name'] = "<a href='/admin_full_get_email_attachment?id_email_attachment=" + data[i]["id"] + "'>" + data[i]["name"] + "</a>";
                o2['btn_del'] = '<button type="button" class="btn btn-danger btn-sm mb-0-25 waves-effect waves-light btn_del" value="' + data[i]["id"] + '"><i class="ti-trash"></i> Delete</button>';
                attachmentList.push(o2);
            }
            console.log(data);
            window.jsGrid_init_attachment();
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

    $('input[type=file]').change(function (e) {
        loadIndicator.show();
        var form = $('form')[0];
        var fd = new FormData(form);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin_full_customer_attachment_new/" + document.id_customer,
            data: fd,
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {
                refreshList();
                loadIndicator.hide();
            },
            error: function (e) {
                loadIndicator.hide();
                // location.reload();
            }
        });

        $('form')[0].reset();
    });

});