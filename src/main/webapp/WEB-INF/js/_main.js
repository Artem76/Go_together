
var resizeBlock = function () {
        var heightWindow = $(window).height();
        $('#content').css('min-height', (heightWindow - 97) + 'px');
}

if (document.getElementById('content')){
    $(window).resize(resizeBlock);
    resizeBlock();
}

var submitForms = function () {
    var mainForm = document.getElementById("mainform");
    if (confirm("Are you sure you want to save?")) {
        mainForm.submit();
    }
    mainForm.preventDefault();
};

var deleteRefboook = function (id, state, mcc_refbook, mnc_refbook) {
    if (confirm("Are you sure you want to delete?")) {
        location = '/admin_full_refbook_delete?id_refbook=' + id + '&state=' + state + "&mcc_refbook=" + mcc_refbook + '&mnc_refbook=' + mnc_refbook;
    }
}

var changVal = function (e) {
    var new_val = e.dataset.val;
    var param = e.dataset.param;
    var param_id = e.dataset.param_id;
    document.getElementById("had_menu_" + param).innerHTML = new_val;
    document.getElementById("val_" + param).value = param_id;
};

var changRole = function (e) {
    var new_val = e.dataset.val;
    var param = e.dataset.param;
    document.getElementById("had_menu_" + param).innerHTML = new_val.replace('_', ' ');
    document.getElementById("value_" + param).value = new_val;
};

var changType = function (e) {
    var new_val = e.dataset.val;
    var param = e.dataset.param;
    document.getElementById("had_menu_" + param).innerHTML = new_val.replace('_', ' ');
    document.getElementById("value_" + param).value = new_val;
};


/* =================================================================
 My Table
 ================================================================= */

try {
    var $table4 = jQuery(".my_table_1");
    var table4 = $table4.DataTable({
        "aLengthMenu": [[-1, 10, 25, 50], ["All", 10, 25, 50]]
    });


    var $table5 = jQuery(".my_table_2");

    var table5 = $table5.DataTable({
        "bPaginate": false
    });
}catch (err){}




/* ================================================================
 Oleg
 */

var SplitLongText = function (text) {
    returnString = "";
    currShortMessage = text;
    var shortMessageArray = currShortMessage.split(" ");
    var newArray = [];
    for (j = 0; j < shortMessageArray.length; j++) {
        if (shortMessageArray[j].length >= 54) {
            newArray.push(shortMessageArray[j].match(/.{1,3}/g).join(" "));
        } else {
            newArray.push(shortMessageArray[j]);
        }
    }
    for (j= 0; j < newArray.length; j++) {
        returnString = returnString + newArray[j] + " ";
    }
    return returnString;
}

var getFormatedUTCDate = function (date) {
    date_string = "";
    date_string = date_string + date.getUTCFullYear() + "-";

    if ((date.getUTCMonth() + 1) < 10) {
        date_string = date_string + "0" + (date.getUTCMonth() + 1)  + "-";
    } else {
        date_string = date_string  + (date.getUTCMonth() + 1) + "-";
    }

    if (date.getUTCDate() < 10) {
        date_string = date_string + "0" + date.getUTCDate() + " ";
    } else {
        date_string = date_string  + date.getUTCDate() + " ";
    }

    if (date.getUTCHours() < 10) {
        date_string = date_string + "0" + date.getUTCHours() + ":";
    } else {
        date_string = date_string  + date.getUTCHours() + ":";
    }

    if (date.getUTCMinutes() < 10) {
        date_string = date_string + "0" + date.getUTCMinutes() + ":";
    } else {
        date_string = date_string  + date.getUTCMinutes() + ":";
    }

    if (date.getUTCSeconds() < 10) {
        date_string = date_string + "0" + date.getUTCSeconds();
    } else {
        date_string = date_string  + date.getUTCSeconds();
    }


    return date_string;
}
