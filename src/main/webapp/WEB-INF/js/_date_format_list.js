/**
 * Created by АРТЕМ on 08.09.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            return dateFormatList;
        },

        insertItem: function (insertingItem) {
            $.post('/admin_full_date_format_save', {
                id: 0,
                format: insertingItem.format
            }, function (data) {
                insertingItem.id = data;
                window.jsGrid_init();
            }, "json").fail(function () {
                console.log("Error post dataFormat insert")
            })
        },

        updateItem: function (updatingItem) {
            $.post('/admin_full_date_format_save', {
                id: updatingItem.id,
                format: updatingItem.format
            }, function (data) {
                window.jsGrid_init();
            }, "json").fail(function () {
                console.log("Error post dataFormat update")
            })
        },

        deleteItem: function (deletingItem) {
            $.post('/admin_full_date_format_delete', {
                id: deletingItem.id,
            }, "json").fail(function () {
                console.log("Error post dataFormat delete")
            })
        }
    };

    window.db = db;

    db.jsgrid_height = 0;
}());


$(document).ready(function () {



//=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.date_format_list').css('min-height', (heightWindow - 155) + 'px');
        db.jsgrid_height = (((heightWindow - 195) > 200) ? (heightWindow - 195) : 200) + 'px';
    }

    if (document.getElementsByClassName('date_format_list')) {
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
        $("#jsGrid-date-format-list").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            editing: true,
            inserting: true,
            pageSize: 15,
            pageButtonCount: 5,
            controller: db,
            updateOnResize: true,
            loadIndication: true,
            fields: [
                {type: "control", width: 20},
                {title: "Date format", name: "format", type: "text", width: 200, align: "center"},
                {title: "Id", name: "id", type: "text", visible: false}
            ],
        });
    };

    window.jsGrid_init();
});