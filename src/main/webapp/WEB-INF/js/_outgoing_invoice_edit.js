/**
 * Created by АРТЕМ on 08.09.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            return detailList;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
}());

(function () {

    var dbAttachment = {

        loadData: function (filter) {
            return attachmentList;
        }
    };

    window.dbAttachment = dbAttachment;

}());

(function () {

    var dbEmailContent = {

        loadData: function (filter) {
            return emailContentList;
        }
    };

    window.dbEmailContent = dbEmailContent;

}());

$(document).ready(function () {

    var loadIndicator = new jsGrid.LoadIndicator();


//=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        //$('.content_customer').css('min-height', (heightWindow - 130) + 'px');
        db.jsgrid_height = heightWindow - 545 + 'px';
        //dbVendor.jsgrid_height = dbClient.jsgrid_height = (((heightWindow - 210) > 200) ? (heightWindow - 210) : 200) + 'px';
    }

    if (document.getElementById('content_header')) {
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

    window.jsGrid_init_traffic = function () {
        $("#jsGrid-traffic-list").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            pageSize: 15,
            pageButtonCount: 5,
            controller: db,
            loadIndication: true,
            fields: [
                {title: "System-Id", name: "uid", type: "text", width: 100, align: "center"},
                {title: "Country", name: "country", type: "text", width: 100, align: "center"},
                {title: "Network", name: "network", type: "text", width: 100, align: "center"},
                {title: "MCC", name: "mcc", type: "text", width: 100, align: "center"},
                {title: "MNC", name: "mnc", type: "text", width: 100, align: "center"},
                {title: "Count", name: "count", type: "text", width: 100, align: "center"},
                {title: "Sum", name: "sum", type: "text", width: 100, align: "center"}
            ],
        });
    };


    window.jsGrid_init_traffic();


    window.jsGrid_init_attachment = function () {
        $("#jsGrid-attachment-list").jsGrid({
            width: "100%",
            autoload: true,
            controller: dbAttachment,
            // rowClick: function (args) {
            //     $("<a href='/admin_full_get_email_attachment?id_email_attachment=" + args.item.id + "'>").click();
            // },
            fields: [
                {title: "Attachment", name: "name", align: "center"},
                {title: "Id", name: "id", visible: false}
            ],
        });
    };

    window.jsGrid_init_attachment();


    window.jsGrid_init_email_list = function () {
        $("#jsGrid-emailcontent-list").jsGrid({
            width: "100%",
            autoload: true,
            controller: dbEmailContent,
            rowClick: function (args) {
                document.location.href = "/admin_full_email_content_view?id_email_content=" + args.item.id;
            },
            fields: [
                {title: "Email", name: "subject", align: "center"},
                {title: "Date created", name: "date", align: "center"},
                {title: "Id", name: "id", visible: false}
            ],
        });
    };

    window.jsGrid_init_email_list();


    $('#confirmed').mouseup(function () {
        if (!$(this).is(":checked")) {
            $('#modal_confirmed').modal('show');
            setTimeout(function () {
                $('#confirmed').prop("checked", false);
            }, 50)
        }
    })

    $('#ok_confirmed').click(function () {
        loadIndicator.show();
        $('#modal_confirmed').modal('hide');
        $.post('/admin_full_sms_outgoing_invoice_confirmed', {
            confirmed: true,
            id_invoice: $('#id_invoice').val()
        }, function (data) {
            if ($.isEmptyObject(data)) {
                $('#confirmed').prop("checked", true);
                $('#confirmed').prop("disabled", true);
            } else {
                console.log(data);
            }
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        });
    })
});