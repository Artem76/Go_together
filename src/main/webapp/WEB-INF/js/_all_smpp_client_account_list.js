/**
 * Created by АРТЕМ on 01.12.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            var list = [];
            $.each(smppClientAccountList, function (n, row) {
                if (row.customer.match(document.filter) || row.account.match(document.filter)) list.push(row);
            })

            return list;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
}());


$(document).ready(function () {



//=============== Resize ==============================

    var resizeBlock = function () {
        $('.content_account').css('min-height', ($(window).height() - 155) + 'px');

    }

    if (document.getElementsByClassName('content_account')) {
        var hJsGrid = ((($(window).height() - 245) > 200) ? ($(window).height() - 245) : 200);
        db.jsgrid_height = hJsGrid + 'px';
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
        $("#jsGrid-accounts").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            rowClick: function (args) {
                /*if (args.item.customer == args.event.target.innerHTML) {
                    document.location.href = "/admin_full_customer_edit?id_customer=" + args.item.idCustomer + "&error=&err_name=";
                } else {*/
                    document.location.href = "/admin_full_smpp_client_account_edit?id_client_account=" + args.item.idAccount + "&id_customer=" + args.item.idCustomer + "&error=&err_name=&err_ips=&err_si=&return_in_customer=no";
                // }

            },
            fields: [
                {title: "Customer", name: "customer", type: "text", width: 100, align: "center"},
                {title: "Name", name: "account", type: "text", width: 200, align: "center"},
                {title: "System Id", name: "systemId", type: "text", width: 290, align: "center"},
                {title: "Id customer", name: "idCustomer", type: "text", visible: false},
                {title: "Id account", name: "idAccount", type: "text", visible: false}
            ],
        });
    };

    window.jsGrid_init();

    $("#regex").keyup(function () {
        document.filter = $('#regex').val();
        window.jsGrid_init();
    })
});