/**
 * Created by АРТЕМ on 21.08.2017.
 */


(function () {

    var db = {

        loadData: function (filter) {
            //(this.clients);
         return this.clients;
         },

        insertItem: function (insertingClient) {

            //(insertingClient.network);
            var customerId = document.getElementById("client_menu").value;
            var countryMcc = document.getElementById("country_menu").value;
            var routingLevel = document.getElementById("smsRoutingLevel_menu").value;


            $.post('/admin_full_save_sms_routing_item', {
                id: -1,
                level: routingLevel,
                account: customerId,
                country: countryMcc,
                network: insertingClient.network,
                vendor: insertingClient.vendor,
                sourceFilter: insertingClient.sourceFilter,
                messageFilter: insertingClient.messageFilter,
                vendorFilter: insertingClient.vendorFilter,
                ruleOrder: insertingClient.ruleOrder,
                weight: insertingClient.weight,
                filtered: insertingClient.filtered,
                replaceSource: insertingClient.replaceSource,
                replaceRegisteredDelivery: insertingClient.replaceRegisteredDelivery,
                registeredDelivery: insertingClient.registeredDelivery,
                filteredContent: insertingClient.filteredContent
            }, function (data) {
                insertingClient.id = data['parameter'];

                if (insertingClient.vendorType == "VendorDialpeer") {
                    insertingClient.vendor = "dp_" + insertingClient.vendor.toString();
                } else {
                    insertingClient.vendor = insertingClient.vendor.toString();
                }
                if (insertingClient.vendorFilterType == "VendorDialpeer") {
                    insertingClient.vendorFilter = "dp_" + insertingClient.vendorFilter.toString();
                } else {
                    insertingClient.vendorFilter = insertingClient.vendorFilter.toString();
                }

                window.jsGrid_init();
                this.clients.push(insertingClient);
            }, "json").fail(function () {
                location.reload();
            })


        },

        updateItem: function (insertingClient) {

            //(insertingClient.network);
            var customerId = document.getElementById("client_menu").value;
            var countryMcc = document.getElementById("country_menu").value;
            var routingLevel = document.getElementById("smsRoutingLevel_menu").value;


            $.post('/admin_full_save_sms_routing_item', {
                id: insertingClient.id,
                level: routingLevel,
                account: customerId,
                country: countryMcc,
                network: insertingClient.network,
                vendor: insertingClient.vendor,
                sourceFilter: insertingClient.sourceFilter,
                messageFilter: insertingClient.messageFilter,
                vendorFilter: insertingClient.vendorFilter,
                ruleOrder: insertingClient.ruleOrder,
                weight: insertingClient.weight,
                filtered: insertingClient.filtered,
                replaceSource: insertingClient.replaceSource,
                replaceRegisteredDelivery: insertingClient.replaceRegisteredDelivery,
                registeredDelivery: insertingClient.registeredDelivery,
                filteredContent: insertingClient.filteredContent
            }, function (data) {
                insertingClient.id = data['parameter'];
                if (insertingClient.vendorType == "VendorDialpeer") {
                    insertingClient.vendor = "dp_" + insertingClient.vendor.toString();
                } else {
                    insertingClient.vendor = insertingClient.vendor.toString();
                }
                if (insertingClient.vendorFilterType == "VendorDialpeer") {
                    insertingClient.vendorFilter = "dp_" + insertingClient.vendorFilter.toString();
                } else {
                    insertingClient.vendorFilter = insertingClient.vendorFilter.toString();
                }
                window.jsGrid_init();
                this.clients.push(insertingClient);
            }, "json").fail(function () {
                //location.reload();
            })


        },

        deleteItem: function (deletingClient) {
            $.post('/admin_full_delete_sms_routing_item', {
                id: deletingClient.id
            }, function (data) {
            }, "json").fail(function () {
            })
        }

    };



    window.db = db;

    db.jsgrid_height = 0;

    db.countrys = [];

    db.vendors = [];

    db.vendors_filter = [];

    db.networks = [];

    db.clients = [];

    $.getJSON('/admin_full_get_mcc', function (data) {
        var o1 = {};
        o1['Name'] = '';
        o1['Mcc'] = '';
        db.countrys.push(o1);

        $.each(data, function (name, mcc) {
            var o2 = {};
            o2['Name'] = name + ' (' + mcc + ')';
            o2['Mcc'] = mcc;
            db.countrys.push(o2);
        })
        window.jsGrid_init();
    })

    $.getJSON('/admin_full_customer_smpp_vendor_account_list', function (data) {
        var empty_vendor = {};
        empty_vendor['Name'] = "Not selected ...";
        empty_vendor['id'] = "0";
        db.vendors_filter.push(empty_vendor);
        $.each(data.customerSmppVendorAccountList, function (id, object) {
            var o2 = {};
            if(object['account_id'].indexOf("dp_") !== -1 ) {
                o2['Name'] = object['customer_name'];
                o2['id'] = object['account_id'];
            } else {
                o2['Name'] = object['customer_name'] + ' - ' + object['account_name'];
                o2['id'] = object['account_id'];
            }
            db.vendors.push(o2);
            db.vendors_filter.push(o2);
        }
        )
        window.jsGrid_init();
    })

}());

$(document).ready(function () {

    //=============== Resize ==============================

    var resizeBlock = function () {
        var heightWindow = $(window).height();
        //$('#routing_list').css('min-height', (heightWindow - 97) + 'px');
        db.jsgrid_height = (heightWindow - 190) + 'px';
    }

    if (document.getElementById('routing_list')) {
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
        },

        insertTemplate: function() {
            var insertControl = this._insertControl = this._createSelect();

            setTimeout(function() {
                insertControl.select2({
                    minWidth: 140
                });
            });
            return insertControl;
        },

        editTemplate: function(value) {
            var editControl = this._editControl = this._createSelect(value);

            setTimeout(function() {
                editControl.select2({
                    minWidth: 140
                });
            });
            return editControl;
        },

        insertValue: function() {
            return this._insertControl.find("option:selected").map(function() {
                return this.selected ? $(this).val() : null;
            });
        },

        editValue: function() {
            return this._editControl.find("option:selected").map(function() {
                return this.selected ? $(this).val() : null;
            });
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
            $("#jsGrid-routing").jsGrid({
                height: db.jsgrid_height,
                width: "100%",
                autoload: true,
                //filtering: true,
                editing: true,
                inserting: true,
                //sorting: true,
                //paging: true,
                autoload: true,
                pageSize: 15,
                pageButtonCount: 5,
                deleteConfirm: "Do you really want to delete the client?",
                controller: db,
                loadIndication: true,
                editTemplate: function(value) {
                    var editControl = this._editControl = this._createSelect(value);
                    setTimeout(function() {
                        editControl.select.select2();
                    });

                    return editControl;
                },
//                onItemInserting: function(args) {
//                    args.cancel = true;
//                },
                fields: [
                    {type: "control", width: 70},
                    {title: "id", name: "id", type: "text", width: 250, align: "center", visible: false},
                    {title: "Order", name: "ruleOrder", type: "number", width: 70, align: "center"},
                    {title: "Weight", name: "weight", type: "number", width: 100, align: "center"},
                    {
                        title: "Network",
                        name: "network",
                        type: "select",
                        width: 250,
                        items: db.networks,
                        valueField: "Mnc",
                        textField: "Name",
                        align: "center",
                    },
                    {title: "Vendor", name: "vendor", type: "select", width: 250, align: "center", items: db.vendors, valueField: "id", textField: "Name",},
                    {title: "Vendor (Whitelist)", name: "vendorFilter", type: "select", width: 250, align: "center", items: db.vendors_filter, valueField: "id", textField: "Name",
                    },
                    {title: "SA Filter", name: "sourceFilter", type: "text", width: 150, align: "center"},
                    {title: "Text Filter", name: "messageFilter", type: "text", width: 150, align: "center"},
                    {title: "Filtered", name: "filtered", type: "checkbox", width: 70, sorting: false, align: "center"},
                    {title: "Replace SA", name: "replaceSource", type: "checkbox", sorting: false, align: "center"},
                    {
                        title: "Replace RD",
                        name: "replaceRegisteredDelivery",
                        type: "checkbox",
                        sorting: false,
                        filtering:false,
                        align: "center"
                    },
                    {title: "RD", name: "registeredDelivery", type: "checkbox", width: 70,  sorting: false, filtering:false, align: "center"},
                    {title: "Content filter", name: "filteredContent", type: "checkbox", width: 100, sorting: false, filtering:false, align: "center"}


                ]
            });
            $(".js-example-basic-single").select2();
        };


    window.jsGrid_init();


});

function customerAccountOnChange(element) {
    loadRoutingByFilters();
}

function countryOnChange(element) {
    initializeNetworksSelect(element.value);
    loadRoutingByFilters();
}

function initializeNetworksSelect(mnc) {
    db.networks = [];
    $.getJSON('/admin_full_get_mnc_by_mcc', "mcc=" + mnc, function (data) {
        var o1 = {};
        o1['Name'] = 'FLAT';
        o1['Mnc'] = 'Flt';
        db.networks.push(o1);
        var networksArray = data['mncXmlList'];
        $.each(networksArray, function (index, object) {
            var o2 = {};
            o2['Name'] = object['network'] + '(' + object['mnc'] + ')';
            o2['Mnc'] = object['mnc'];
            db.networks.push(o2);
        })
        window.jsGrid_init();

    })
}

function routingLevelOnChange(element) {
    loadRoutingByFilters();
}

function loadRoutingByFilters() {
    var customerId = document.getElementById("client_menu").value;
    var countryMcc = document.getElementById("country_menu").value;
    var routingLevel = document.getElementById("smsRoutingLevel_menu").value;
    db.clients = [];
    window.jsGrid_init();
    if(customerId != 0 && countryMcc != 0) {
        //alert(customerId + ' ' + countryMcc + ' ' + routingLevel);
        $.post('/admin_full_get_sms_routing_list', {
            client_id: customerId,
            mcc: String(countryMcc),
            sms_routing_level: routingLevel
        }, function (data) {
            $.each(data.routingSmsRuleList, function (i, routingSmsRule) {

                if (routingSmsRule.vendorType == "VendorDialpeer") {
                    vendorId = "dp_" + routingSmsRule.vendor.toString();
                } else {
                    vendorId = routingSmsRule.vendor.toString();
                }

                if (routingSmsRule.vendorFilterType == "VendorDialpeer") {
                    vendorFilterId = "dp_" + routingSmsRule.vendorFilter.toString();
                } else {
                    vendorFilterId = routingSmsRule.vendorFilter.toString();
                }

                var r = {
                    "id": routingSmsRule.id,
                    "account": routingSmsRule.account,
                    "country": routingSmsRule.mcc,
                    "network": routingSmsRule.mnc,
                    "vendor": vendorId,
                    "sourceFilter": routingSmsRule.sourceFilter,
                    "messageFilter": routingSmsRule.messageFilter,
                    "vendorFilter": vendorFilterId,
                    "ruleOrder": routingSmsRule.ruleOrder,
                    "weight": routingSmsRule.weight,
                    "filtered": routingSmsRule.filtered,
                    "replaceSource": routingSmsRule.replaceSource,
                    "replaceRegisteredDelivery": routingSmsRule.replaceRegisteredDelivery,
                    "registeredDelivery": routingSmsRule.registeredDelivery,
                    "filteredContent": routingSmsRule.filteredContent,
                };
                db.clients.push(r);
                window.jsGrid_init();
            })
        }, "json").fail(function () {
            //location.reload();
        })
    }
}
