/**
 * Created by АРТЕМ on 25.07.2017.
 */
var ips_namber = 1000000;

var addVendorIps = function () {
    ips_namber++;

    var frag = document.createDocumentFragment();
    var tr = document.createElement("tr");

    // создаем поле Id
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", ips_namber + "_id");
    input.setAttribute("value", 0);
    tr.appendChild(input);

    // создаем поле Allowed
    var td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "checkbox");
    input.setAttribute("name", ips_namber + "_allowed");
    input.setAttribute("value", "true");
    input.setAttribute("placeholder", "Allowed");
    input.setAttribute("class", "td-checkbox chk");
    input.checked = true;
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле Ip
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", ips_namber + "_ip");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "Ip");
    input.setAttribute("class", "ip");
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле Port
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", ips_namber + "_port");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "Port");
    input.setAttribute("class", "port");
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле System Id
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", ips_namber + "_systemId");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "System Id");
    input.setAttribute("class", "system_id");
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле Password
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", ips_namber + "_password");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "Password");
    input.setAttribute("class", "password");
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле System-type
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", ips_namber + "_systemType");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "System-type");
    input.setAttribute("class", "system_type");
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле Submit Throughput
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", ips_namber + "_submitThroughput");
    input.setAttribute("value", "0");
    input.setAttribute("placeholder", "Submit Throughput");
    input.setAttribute("class", "submit_throughput");
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле cid
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", ips_namber + "_cid");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "CID");
    input.setAttribute("class", "cid");
    td.appendChild(input);
    tr.appendChild(td);

    frag.appendChild(tr);

    document.getElementById("ips_list").prepend(frag);
}

var addClientIps = function () {
    ips_namber++;

    var frag = document.createDocumentFragment();
    var tr = document.createElement("tr");

    // создаем поле Id
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", "ip_" + ips_namber + "_id");
    input.setAttribute("value", 0);
    tr.appendChild(input);

    // создаем поле Allowed
    var td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "checkbox");
    input.setAttribute("name", "ip_" + ips_namber + "_allowed");
    input.setAttribute("value", "true");
    input.setAttribute("placeholder", "Allowed");
    input.setAttribute("class", "td-checkbox chk");
    input.checked = true;
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле Ip
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "ip_" + ips_namber + "_ip");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "Ip");
    input.setAttribute("class", "ip");
    td.appendChild(input);
    tr.appendChild(td);

    frag.appendChild(tr);

    document.getElementById("ips_list").prepend(frag);
}

var addClientSystemId = function () {
    ips_namber++;

    var frag = document.createDocumentFragment();
    var tr = document.createElement("tr");

    // создаем поле Id
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", "si_" + ips_namber + "_id");
    input.setAttribute("value", 0);
    tr.appendChild(input);

    // создаем поле System Id
    var td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "si_" + ips_namber + "_systemId");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "System Id");
    input.setAttribute("class", "system_id");
    input.checked = true;
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле Password
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "si_" + ips_namber + "_password");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "Password");
    input.setAttribute("class", "password");
    td.appendChild(input);
    tr.appendChild(td);

    // создаем поле UID
    td = document.createElement("td");
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "si_" + ips_namber + "_uid");
    input.setAttribute("value", "");
    input.setAttribute("placeholder", "UID");
    input.setAttribute("class", "uid");
    td.appendChild(input);
    tr.appendChild(td);

    frag.appendChild(tr);

    document.getElementById("system_id_list").prepend(frag);
}
