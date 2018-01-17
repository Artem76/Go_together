/**
 * Created by АРТЕМ on 09.01.2018.
 */
(function () {

    var db = {

        loadData: function (filter) {
            var list = [];
            $.each(softswitchList, function (n, row) {
                if (row.name.match(document.filter) || row.host.match(document.filter)) list.push(row);
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
        $('.content_list').css('min-height', ($(window).height() - 155) + 'px');

    }

    if (document.getElementsByClassName('content_list')) {
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
        $("#jsGrid-softswitches").jsGrid({
            height: db.jsgrid_height,
            width: "100%",
            autoload: true,
            controller: db,
            loadIndication: true,
            rowClick: function (args) {
                document.location.href = "/admin_full_softswitch_edit?id_softswitch=" + args.item.id + "&error=";
            },
            fields: [
                {title: "Name", name: "name", type: "text", width: 200, align: "center"},
                {title: "Host", name: "host", type: "text", width: 200, align: "center"},
                {title: "Type", name: "type", type: "text", width: 200, align: "center"},
                {title: "Id", name: "id", type: "text", visible: false}
            ],
        });
    };

    window.jsGrid_init();

    $("#regex").keyup(function () {
        document.filter = $('#regex').val();
        window.jsGrid_init();
    })
});