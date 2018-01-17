/**
 * Created by АРТЕМ on 14.08.2017.
 */

(function () {

    var db = {

        loadData: function (filter) {
            return vendorDialpeerList;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
}());


$(document).ready(function () {



//=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.vendorDialpeerList').css('min-height', (heightWindow - 97) + 'px');
        db.jsgrid_height = (((heightWindow - 140) > 200) ? (heightWindow - 140) : 200) + 'px';
    }

    if (document.getElementsByClassName('vendorDialpeerList')) {
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
        $("#jsGrid-vendorDialpeerList").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            updateOnResize: true,
            loadIndication: true,
            rowClick: function (args) {
                document.location.href = "/admin_full_vendor_dialpeer_edit?id_vendor_dialpeer=" + args.item.id;
            },
            fields: [
                {title: "Name", name: "name", type: "text", width: 200, align: "center"},
                {title: "Customer - System Id", name: "system_id", type: "text", width: 500, align: "center"},
                {title: "Id", name: "id", type: "text", visible: false},
            ],
        });
    };

    window.jsGrid_init();
});
