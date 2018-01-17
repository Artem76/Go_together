
(function () {

    var db = {

        loadData: function (filter) {
            return document.rn;
        }
    };

    window.db = db;

    db.jsgrid_height = 0;

    document.rn = [];
}());

// var date_start;
// var date_end;


$(document).ready(function () {
    var loadIndicator = new jsGrid.LoadIndicator();
    document.page_index = 0;
    document.page_size = 50;
    document.mcc = "";
    document.mnc = "";
    document.accountIp_id = 0;


    //=============== Select ==============================

    $(".js-example-basic-single").select2({
        dropdownParent: $('#vendor_parent')
    });

    $(".js-example-basic-single").on('select2:select', function () {
        event.stopPropagation();
    });

    $('[data-plugin="select2"]').select2($(this).attr('data-options'));

    $(".select_mcc").on('select2:select', function () {
        event.stopPropagation();
        $.get('/admin_full_get_mnc_by_mcc', {
            mcc: $('.select_mcc').val()
        }, function (data) {
            //console.log(data.mncXmlList[0].network);
            var select = $('#mnc_menu');
            select.find('option').remove();
            select.append('<option value="">All MNC</option>')
            for (var n in data.mncXmlList) {
                select.append('<option value="' + data.mncXmlList[n].mnc + '">' + data.mncXmlList[n].network + ' (' + data.mncXmlList[n].mnc + ')</option>')
            }
        }, "json").fail(function () {
            location.reload();
        })
    });

    $(".select_mcc").on('select2:open', function () {
        $(".select2-search").mouseup(function () {
            event.stopPropagation();
        })
    })

    $(".select_mnc").on('select2:select', function () {
        event.stopPropagation();
    });

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        //$('#routing_list').css('min-height', (heightWindow - 97) + 'px');
        db.jsgrid_height = (heightWindow - 190) + 'px';
    }

    if (document.getElementById('jsGrid-vendor-rn-list')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }


    //=============== jsGrid ==============================
    var select2 = function(config) {
        jsGrid.Field.call(this, config);
    };

    select2.prototype = new jsGrid.Field({

        itemTemplate: function(value) {
            return $.makeArray(value).join(", ");
        },

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
            return $result;
        }
    });

    jsGrid.fields.select2 = select2;

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
            setTimeout(function() {
                $result.select2({
                    width: 200
                });
            });
            return $.each(this.items, function (index, item) {
                var value = valueField ? item[valueField] : index,
                    text = textField ? item[textField] : item,
                    $option = $("<option>").attr("value", value).text(text).appendTo($result);
                $option.prop("selected", selectedIndex === index);
            }), $result;
        }
    }),

        window.jsGrid_init = function () {
            $("#jsGrid-vendor-rn-list").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                editing: false,
                inserting: false,
                controller: db,
                loadIndication: true,
                rowClick: function(args) {
                    if (args.item.date == args.event.target.innerHTML || args.item.smppVendorIps == args.event.target.innerHTML || args.item.user == args.event.target.innerHTML){
                        document.location.href = "/admin_full_vendor_rn_edit?id_vendor_rn=" + args.item.id + "&id_email_content=0&id_email_attachment=0";
                    }
                },
                fields: [
                    {title: "Upload date", name: "date", type: "text", width: 100, align: "center"},
                    {title: "Customer - account - system id", name: "smppVendorIps", type: "text", width: 200, align: "center"},
                    {title: "User", name: "user", type: "text", width: 100, align: "center"},
                    {title: "Related emails", name: "email", type: "text", width: 200, align: "center"},
                    {title: "", name: "id", type: "text", visible: false}
                ],
            });
            $(".js-example-basic-single").select2();
        };
    refreshList();
    window.jsGrid_init();


    //========== Button =====================

    $("#refresh_list").click(function () {
        document.page_index = 0;
        $('#btn_previous').addClass("disabled");
        $('#btn_next').addClass("disabled");
        document.accountIp_id = $("#vendor_menu").val();
        document.mcc = $("#mcc_menu").val();
        document.mnc = $("#mnc_menu").val();
        refreshList();
    })

    $('#btn_previous').click(function () {
        if (!$('#btn_previous').hasClass("disabled") && document.page_index != 0) {
            document.page_index = document.page_index - 1;
            refreshList();
        }
    })

    $('#btn_next').click(function () {
        if (!$('#btn_next').hasClass("disabled")) {
            document.page_index = document.page_index + 1;
            refreshList();
        }
    })


    function refreshList() {
        loadIndicator.show();
        document.rn = [];
        $.post('/admin_full_vendor_rn_list',{
            accountIp_id: document.accountIp_id,
            mcc: document.mcc,
            mnc: document.mnc,
            page_index: document.page_index,
            page_size: document.page_size

        }, function (data) {
            document.rn = [];
            var rates = data.rates;
            for (var i = 0, len = rates.length; i < len; i++) {
                var o2 = {};
                o2['id'] = rates[i]["id"];
                o2['smppVendorIps'] = rates[i]["smppVendorIps"];
                o2['date'] = rates[i]["date"];
                o2['user'] = rates[i]["user"];
                o2['email'] = rates[i]["email"];
                document.rn.push(o2);
            }
            if (document.page_index < 1){
                $('#btn_previous').addClass("disabled");
            }else {
                $('#btn_previous').removeClass("disabled");
            }
            if (data.next == "true" || data.next == true){
                $('#btn_next').removeClass("disabled");
            }else {
                $('#btn_next').addClass("disabled");
            }
            console.log(document.rn);
            window.jsGrid_init();
            loadIndicator.hide();
        }, "json").fail(function () {
            location.reload();
            loadIndicator.hide();
        });
    }

});

