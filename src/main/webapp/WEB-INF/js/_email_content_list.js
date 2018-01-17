/**
 * Created by АРТЕМ on 08.09.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            filter["emailRole"] = document.emailRole;
            return $.ajax({
                type: "POST",
                url: "/admin_full_get_email_content_page",
                data: filter
            });
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
}());


$(document).ready(function () {



//=============== Resize ==============================

    var resizeBlock = function () {
        $('.content_email_content').css('min-height', ($(window).height() - 97) + 'px');

    }

    if (document.getElementsByClassName('content_email_content')) {
        var hJsGrid = ((($(window).height() - 140) > 200) ? ($(window).height() - 140) : 200);
        db.jsgrid_height = hJsGrid + 'px';
        db.pageSize = (hJsGrid - 80) / 38 | 0;
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
        $("#jsGrid-email-contents").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            paging: true,
            pageLoading: true,
            pageSize: db.pageSize,
            pageButtonCount: 5,
            controller: db,
            loadIndication: true,
            rowClick: function (args) {
                if (args.item.emailType == args.event.target.innerHTML || args.item.date == args.event.target.innerHTML || args.item.from == args.event.target.innerHTML || args.item.to == args.event.target.innerHTML || args.item.subject == args.event.target.innerHTML) {
                    if (document.emailRole == "Ratemode_SMS") {
                        document.location.href = "/admin_full_email_content_view_ratemode_sms?id_email_content=" + args.item.id;
                    } else {
                        document.location.href = "/admin_full_email_content_view?id_email_content=" + args.item.id;
                    }
                }
            },
            fields: [
                {title: "Email type", name: "emailType", type: "text", width: 100, align: "center"},
                {title: "Date", name: "date", type: "text", width: 200, align: "center"},
                {title: "From", name: "from", type: "text", width: 290, align: "center"},
                {title: "To", name: "to", type: "text", width: 290, align: "center"},
                {title: "Subject", name: "subject", type: "text", width: 400, align: "center"},
                {title: "Status", name: "status", type: "text", width: 200, align: "center"},
                {title: "Id", name: "id", type: "text", visible: false},
            ],
        });
    };

    window.jsGrid_init();
});