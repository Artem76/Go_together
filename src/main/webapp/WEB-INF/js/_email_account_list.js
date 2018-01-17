/**
 * Created by АРТЕМ on 08.09.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            return emailAccountList;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
}());


$(document).ready(function () {



//=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_email_account').css('min-height', (heightWindow - 97) + 'px');
        db.jsgrid_height = (((heightWindow - 140) > 200) ? (heightWindow - 140) : 200) + 'px';
    }

    if (document.getElementsByClassName('content_email_account')) {
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
        $("#jsGrid-email-accounts").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            pageSize: 15,
            pageButtonCount: 5,
            controller: db,
            updateOnResize: true,
            loadIndication: true,
            rowClick: function (args) {
                document.location.href = "/admin_full_email_account_edit?id_email_account=" + args.item.id;
            },
            fields: [
                {title: "Email", name: "email", type: "text", width: 200, align: "center"},
                {title: "Email role", name: "emailRole", type: "text", width: 150, align: "center"},
                {title: "SMTP server", name: "smtpServer", type: "text", width: 200, align: "center"},
                {title: "IMAP server", name: "imapServer", type: "text", width: 200, align: "center"},
                {title: "Id", name: "id", type: "text", visible: false},
            ],
        });
    };

    window.jsGrid_init();
});