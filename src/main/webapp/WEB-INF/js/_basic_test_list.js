(function () {

    var db = {

        loadData: function (filter) {
            return document.payments;
        }
    };

    var dbaccounts = {

        loadData: function (filter) {
            return window.accounts;
        }
    };

    window.db = db;

    window.dbaccounts = dbaccounts;

    db.jsgrid_height = 0;

    document.payments = [];

    window.accounts = [];

    window.page = 0;
}());

$(document).ready(function () {
    var loadIndicator = new jsGrid.LoadIndicator();

    document.page_index = 0;
    document.page_size = 50;


    //=============== Select ==============================

    var customerSelect = $(".js-example-basic-single").select2({dropdownParent: $('.modal')});

    $('[data-plugin="select2"]').select2($(this).attr('data-options'));

    $(".select_mnc").on('select2:select', function () {
        event.stopPropagation();
    });



    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        db.jsgrid_height = (heightWindow - 190) + 'px';
    }

    if (document.getElementById('jsGrid-payment-list')) {
        $(window).resize(resizeBlock);
        resizeBlock();
    }


    //=============== jsGrid ==============================
    var select2 = function (config) {
        jsGrid.Field.call(this, config);
    };

    select2.prototype = new jsGrid.Field({

        itemTemplate: function (value) {
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
            setTimeout(function () {
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
            $("#jsGrid-payment-list").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                editing: false,
                inserting: false,
                controller: db,
                loadIndication: true,
                rowClick: function (args) {
                    $.post('/admin_full_get_basic_test_details_list', {
                        testId: args.item.id
                    }, function (data) {
                        var lastVendor = "firstRow";
                        var reportText = "";

                        for (i=0;i<data.length;i++) {

                            if (lastVendor != data[i].vendorName || lastVendor == "firstRow") {
                                reportText = reportText + "<b>&nbsp;</b>";
                                reportText = reportText + "<h4 class='h-underline h-underline-50 h-underline-warning'>" + data[i].vendorName + "</h4>"
                                reportText = reportText + "<table class='table table-hover mb-md-0'>";
                                reportText = reportText + "   <thead>";
                                reportText = reportText + "      <tr>";
                                reportText = reportText + "         <th style='text-align: center'>Country - network</th>";
                                reportText = reportText + "         <th style='text-align: center'>Destination address</th>";
                                reportText = reportText + "         <th style='text-align: center'>Message-Id</th>";
                                reportText = reportText + "         <th style='text-align: center'>Result</th>";
                                reportText = reportText + "      </tr>";
                                reportText = reportText + "   </thead>";
                                reportText = reportText + "<tbody>";
                            }
                            for (j=0;j<data[i].details.length;j++) {

                                var countryName = data[i].countryName;
                                var newtorkName = data[i].networkName;
                                var destinationAddr = data[i].details[j].destinationAddr;
                                var msgid = data[i].details[j].vendorMsgid;
                                var status = data[i].details[j].deliveryStatus;
                                var submit_status = data[i].details[j].submitStatus;

                                if (submit_status == "ESME_ROK" || submit_status == null) {
                                    if (status == null) {
                                        statusText = "<i class='fa fa-circle text-warning mr-0-5'></i>PENDING";
                                    } else {
                                        if (status == "DELIVRD" && (data[i].details[j].mustBeDelivered == true)) {
                                            statusText = "<i class='fa fa-check text-success mr-0-5'></i>PASSED VALID";
                                        }
                                        if (status != "DELIVRD" && (data[i].details[j].mustBeDelivered == true)) {
                                            statusText = "<i class='fa fa-close text-danger mr-0-5'></i>UNDELIV ON VALID";
                                        }
                                        if (status == "DELIVRD" && (data[i].details[j].mustBeDelivered == false)) {
                                            statusText = "<i class='fa fa-close text-danger mr-0-5'></i>DELIVRD ON INVALID";
                                        }
                                        if (status != "DELIVRD" && (data[i].details[j].mustBeDelivered == false)) {
                                            statusText = "<i class='fa fa-check text-success mr-0-5'></i>PASSED INVALID";
                                        }
                                    }
                                } else {
                                    statusText = "<i class='fa fa-close text-danger mr-0-5'></i>REJECTED : " + submit_status;
                                }


                                if (msgid == null) {
                                    msgid = "";
                                }
                                if (destinationAddr == null) {
                                    destinationAddr = "";
                                }


                                reportText = reportText + "      <tr>";
                                reportText = reportText + "         <td style='text-align: center'>" + countryName + " - " + newtorkName + "</td>";
                                reportText = reportText + "         <td style='text-align: center'>" + destinationAddr + "</td>";
                                reportText = reportText + "         <td style='text-align: center'>" + msgid + "</td>";
                                reportText = reportText + "         <td style='text-align: center'>" + statusText + "</td>";
                                reportText = reportText + "      </tr>";
                            }


                            if (lastVendor != data[i].vendorName || lastVendor == "firstRow") {
                                reportText = reportText + "</tbody>";
                                reportText = reportText + "</table>";
                            }

                            lastVendor = data[i].vendorName;
                        }
                        $('#details_table').html(reportText);

                    }, "json").fail(function () {
                        location.reload();
                    });





                    $("#modal_details").modal("show");
                },
                fields: [
                    {title: "Date", name: "date", type: "text", width: 75, align: "center"},
                    {title: "Message text", name: "text", type: "text", width: 300, align: "center"},
                    {title: "Source address", name: "sourceaddr", type: "text", width: 75, align: "center"},
                    {title: "Country", name: "countryName", type: "text", width: 100, align: "center"},
                    {title: "Result", name: "result", type: "text", width: 100, align: "center"},
                    {title: "", name: "finished", type: "text", visible: false},
                    {title: "", name: "id", type: "text", visible: false},
                    {title: "", name: "customerId", type: "text", visible: false}
                ],
            });
        };
    window.jsGrid_init();

    refreshList();

    $(".select_mnc").on('select2:select', function () {
        event.stopPropagation();
    });


    //========== Button =====================

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





    $("#button_edit").click(function () {
        // $('#source_addr').val("");
        // $('#text').text("");
        // $('#country').val("0");
        // $('#networks').val("0");
        // $('#mnc_menu').val("0");
        // $('#template').val("0");


        $("#modal_payment").modal("show");
    })

});


function refreshList() {
    //admin_full_get_basic_test_list

    $.post('/admin_full_get_basic_test_list', {
        page: document.page_index
    }, function (data) {
        document.payments = [];
        for (i=0;i<data.length;i++) {
            row = {};
            row['id'] = data[i].id;
            row['date'] = getFormatedUTCDate(new Date(data[i].date));
            row['text'] = data[i].text;
            row['sourceaddr'] = data[i].sourceAddr;
            row['country'] = data[i].mcc;
            var countryName = $("#country option[value=" + data[i].mcc + "]").text();
            row['countryName'] = countryName;
            row['network'] = data[i].mnc;
            row['result'] = data[i].result;
            row['processed'] = data[i].processed;
            row['finished'] = data[i].finished;
            document.payments.push(row);
        }

        if (document.page_index == 0) {
            $('#btn_previous').addClass("disabled");
            if (document.payments.length < 50) {
                $('#btn_next').addClass("disabled");
            } else {
                $('#btn_next').removeClass("disabled");
            }
        }else {
            $('#btn_previous').removeClass("disabled");
            if (document.payments.length < 50) {
                $('#btn_next').addClass("disabled");
            } else {
                $('#btn_next').removeClass("disabled");
            }
        }

        window.jsGrid_init();
    }, "json").fail(function () {
        location.reload();
    })

}

$('#save').click(function () {

    var sourceaddr =  $('#source_addr').val();
    var templatetext =  $('#text').val();
    var country =  $('#country').val();
    var network =  $('#networks').val();

    if ($('#mnc_menu').val() == null) {
        toastr.error("You must select at least 1 vendor account.");
        return;
    } else {
        var vendors = $('#mnc_menu').val().toString();
    }
    if (sourceaddr == "") {
        toastr.error("Source address must be entered.");
        return;
    }
    if (templatetext == "") {
        toastr.error("Message text must be entered.");
        return;
    }
    if (country == "0") {
        toastr.error("Country must be selected");
        return;
    }


    $.post('/admin_full_save_basic_test', {
        sourceaddr: sourceaddr,
        templatetext: sourceaddr,
        country: country,
        network: network,
        vendors: vendors
    }, function (data) {
        if (data.status == "Error") {
            toastr.error(data.parameter);
        }  else {
            $("#modal_payment").modal('toggle');
            toastr.info("Test started.");
            refreshList();
        }
    }, "json").fail(function () {
        location.reload();
    })


})

function refillNetworks(network) {
    var countryId = $('#country').val();

    if (countryId == 0) {
        return;
    }

    $.get('/admin_full_get_mnc_by_mcc', {
        mcc: $('#country').val()
    }, function (data) {
        var select = $('#networks');
        select.find('option').remove();
        select.append('<option value="0">FLAT</option>')
        for (var n in data.mncXmlList) {
            select.append('<option value="' + data.mncXmlList[n].mnc + '">' + data.mncXmlList[n].network + ' (' + data.mncXmlList[n].mnc + ')</option>')
        }
    }, "json").fail(function () {
        location.reload();
    })
}

function templateOnChange() {
    var sourceaddr =  $('#template option:selected').attr('sourceaddr');
    var templatetext =  $('#template option:selected').attr('template');

    if (sourceaddr != "NotSelected") {
        $('#source_addr').val(sourceaddr);
        $('#text').text(templatetext);
    } else {
        $('#source_addr').val("");
        $('#text').text("");
    }
}


function saveTemplate() {
    var tempalteText = $('#text').val();
    var sourceAddr = $('#source_addr').val();

    if (text == "" || sourceAddr == "") {
        alert("empty");
    }

    $.post('/admin_full_save_test_template', {
        text: tempalteText,
        sourceAddr: sourceAddr
    }, function (data) {
        if (data.status == "Error") {
            toastr.error(data.parameter);
        }  else {
            toastr.info("New template added.");
        }

    }, "json").fail(function () {
        location.reload();
    })


    //admin_full_save_test_template
}

