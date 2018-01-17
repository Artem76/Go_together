/**
 * Created by АРТЕМ on 08.09.2017.
 */
(function () {

    var db = {

        loadData: function (filter) {
            return selectedItemsAttachment;
        }
    };

    window.db = db;
}());


$(document).ready(function () {




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
        $("#jsGrid-email-attachment").jsGrid({
            width: "100%",
            autoload: true,
            controller: db,
            updateOnResize: true,
            loadIndication: true,
            rowRenderer: function (item, itemIndex) {
                if ((itemIndex % 2) == 0) {
                    return $('<tr>').css("backgroundColor", "white")
                        .hover(function () {
                            $(this).css("backgroundColor", "#9accea")
                        }, function () {
                            $(this).css("backgroundColor", "white")
                        })
                        .append($('<a>').attr("href", "/admin_full_get_email_attachment?id_email_attachment=" + item.id).css({"color": "#55595c", "width": "100%", "display": "block"})
                            .append($('<td>').css("border", "none").text(item.fileName)));
                } else {
                    return $('<tr>').css("backgroundColor", "rgba(0,0,0,0.02)")
                        .hover(function () {
                            $(this).css("backgroundColor", "#9accea")
                        }, function () {
                            $(this).css("backgroundColor", "rgba(0,0,0,0.02)")
                        })
                        .append($('<a>').attr("href", "/admin_full_get_email_attachment?id_email_attachment=" + item.id).css({"color": "#55595c", "width": "100%", "display": "block"})
                            .append($('<td>').css("border", "none").text(item.fileName)));
                }

            },
            fields: [
                {title: "Attachment"},
                {title: "Id", name: "id", visible: false}
            ],
        });
    };

    window.jsGrid_init();


//=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('.content_email_content').css('min-height', (heightWindow - 97) + 'px');
        var heightMain = $('#main').height();
        $('#body').css('min-height', ((heightWindow - 182 - heightMain) + 'px'));
    }

    if (document.getElementsByClassName('content_email_content')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }
});