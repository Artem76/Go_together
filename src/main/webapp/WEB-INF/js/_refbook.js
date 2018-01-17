/**
 * Created by АРТЕМ on 18.07.2017.
 */

(function () {

    var db = {
        loadData: function (filter) {
            return selectedItems;
        }
    };

    window.db = db;
    db.jsgrid_height = 0;
    db.list = [];

    window.db = db;

}());



$(document).ready(function () {

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.refbook').css('min-height', (heightWindow - 190) + 'px');
        db.jsgrid_height = (((heightWindow - 140) > 200) ? (heightWindow - 190) : 200) + 'px';
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
        $("#jsGrid-refbook").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            rowClick: function (args) {
                if (args.event.target.classList.contains("btn") || args.event.target.classList.contains("ti-pencil")) {
                    document.location.href = "/admin_full_refbook_edit?id_refbook=" + args.item.id + "&type=country" + "&error=";
                } else {
                    document.location.href = "/admin_full_refbook_network_list?mcc=" + args.item.mcc;
                }
            },
            fields: [
                {itemTemplate: function(value, item) {
                    return "<button type='button' class='btn btn-outline-primary btn-sm mb-0-25 waves-effect waves-light'><i class='ti-pencil'></i></button>";
                }, title: "", name: "edit", type: "text", width: 13, align: "center"},
                {title: "Country", name: "country", type: "text", width: 200, align: "center"},
                {title: "MCC", name: "mcc", type: "text", width: 50, align: "center"},
                {title: "Dialcode", name: "dialcode", type: "text", width: 100, align: "center"},
                {title: "Min. number length", name: "min_length", type: "text", width: 100, align: "center"},
                {title: "Max. number length", name: "max_length", type: "text", width: 100, align: "center"}
            ],
        });
    };

    window.jsGrid_init();
});