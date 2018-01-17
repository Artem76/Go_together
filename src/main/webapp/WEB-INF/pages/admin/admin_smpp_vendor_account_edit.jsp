<?xml version="1.0" encoding="utf-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Meta tags -->
    <meta charset="utf-8">
    <%--<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Title -->
    <title>${title} - SMPP Vendor Account</title>

    <!-- Vendor CSS -->
    <link rel="stylesheet" href="/vendor/bootstrap4/css/bootstrap.min.css">
    <link rel="stylesheet" href="/vendor/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="/vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/vendor/animate.css/animate.min.css">
    <link rel="stylesheet" href="/vendor/jscrollpane/jquery.jscrollpane.css">
    <link rel="stylesheet" href="/vendor/waves/waves.min.css">
    <link rel="stylesheet" href="/vendor/switchery/dist/switchery.min.css">
    <link rel="stylesheet" href="/vendor/morris/morris.css">
    <link rel="stylesheet" href="/vendor/jvectormap/jquery-jvectormap-2.0.3.css">
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid.css">
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid-theme.min.css">


    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_smpp_vendor_account_edit.css">

    <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="fixed-sidebar fixed-header skin-default content-appear">
<div class="wrapper">

    <!-- Preloader -->
    <%--<div class="preloader"></div>--%>

    <!-- Sidebar -->
    <c:import url="/sections/${role}_sidebar.html"/>

    <c:if test="${id_customer ne 0}">
        <input type="hidden" name="id_customer" value="${id_customer}" id="id_customer">
    </c:if>
    <input type="hidden" name="id_vendor_account" value="${id_vendor_account}">

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>

    <li class="nav-item dropdown">
        <a href="#" aria-expanded="false" class="nav-link">
            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_save">Save
            </button>
        </a>
    </li>
    <li class="nav-item dropdown">

        <a
                <c:if test="${id_customer ne 0 and return_in_customer ne 'no'}"> href="/admin_full_customer_edit?id_customer=${id_customer}&error=&err_name="</c:if>
                <c:if test="${id_customer eq 0 or return_in_customer eq 'no'}"> href="/admin_full_all_smpp_vendor_account_list"</c:if>
                class="nav-link">

            <button type="button" class="btn btn-outline-warning">Cancel</button>
        </a>
    </li>

    <c:import url="/sections/header_middle1.html"/>
    <c:import url="/sections/header_middle2.html"/>
    ${name_admin}
    <c:import url="/sections/header_end.html"/>

    <div class="site-content">
        <!-- Content -->
        <div class="content-area py-1">
            <div class="container-fluid">

                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#Profile" role="tab">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#Additional_Settings" role="tab">Additional
                            Settings</a>
                    </li>
                </ul>

                <div class="box box-block bg-white content_vendor" style="border-top: 0">
                    <div class="tab-content">

                        <div class="tab-pane active" id="Profile" role="tabpanel">
                            <c:if test="${id_customer eq 0}">
                                <div class="form-group">
                                    <select class="form-control" name="id_customer"
                                            id="search-select" <%--style="width: 200px"--%>>
                                        <option value="0">No customer</option>
                                        <c:forEach var="customer" items="${customers}">
                                            <option value="${customer.id}">${customer.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:if>
                            <label for="name">Name</label>
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <input type="text" value="${smppVendorAccount.name}" class="form-control" id="name"
                                               placeholder="Name" name="name_vendor_account">
                                    </div>
                                </div>
                                <div class="col-md-2" style="padding: 6px">
                                    <div class="form-group">
                                        <input type="checkbox" value="true"
                                               id="dont_create_dp"
                                               name="dont_create_dp" class="td-checkbox"
                                        <c:if test="${smppVendorAccount.dont_create_dp}">
                                               checked
                                        </c:if>
                                        >
                                        <label for="dont_create_dp">Dialpeer disabled</label>
                                    </div>
                                </div>
                                <div class="col-md-2" style="padding: 6px">
                                    <div class="form-group">

                                        <input type="checkbox" value="true"
                                               id="dontSync"
                                               name="dontSync" class="td-checkbox"
                                        <c:if test="${smppVendorAccount.dontSync}">
                                               CHECKED
                                        </c:if>
                                        >
                                        <label for="dontSync">Softswitch synchronization disabled</label>
                                    </div>
                                </div>
                            </div>

                            <div class="scroll-x" name="ips_list">
                                <div id="jsGrid-smpp-vendor-ips"></div>

                            </div>
                            <div class="clearfix"></div>
                        </div>

                        <div class="tab-pane" id="Additional_Settings" role="tabpanel">

                            <div class="form-group">
                                <label for="tag">Tag</label>
                                <input type="text" value="${smppVendorAccount.tag}" class="form-control" id="tag"
                                       placeholder="Tag" name="tag_vendor_account">
                            </div>

                            <div class="form-group">
                                <label for="ripf">Replace if present flag</label>
                                <select class="form-control" name="ripf" id="ripf">
                                    <option value="false">Do not replace</option>
                                    <option value="true">Replace</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="con_fail_delay">Reconnect delay (sec)</label>
                                <input type="text" value="${smppVendorAccount.con_fail_delay}" class="form-control"
                                       id="con_fail_delay"
                                       placeholder="sec ..." name="con_fail_delay">
                            </div>

                            <div class="form-group">
                                <label for="dlr_expiry">DLR expiry period (sec)</label>
                                <input type="text" value="${smppVendorAccount.dlr_expiry}" class="form-control"
                                       id="dlr_expiry"
                                       placeholder="sec ..." name="dlr_expiry">
                            </div>

                            <div class="form-group">
                                <label for="coding">Default data coding</label>
                                <select class="form-control" name="coding" id="coding">
                                    <c:forEach var="dataCoding" items="${dataCodingList}">
                                        <option value="${dataCoding}">${dataCoding.toString()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="elink_interval">Enquire link interval (sec)</label>
                                <input type="text" value="${smppVendorAccount.elink_interval}" class="form-control"
                                       id="elink_interval"
                                       placeholder="sec ..." name="elink_interval">
                            </div>

                            <div class="form-group">
                                <label for="bind_to">Bind request response timeout (sec)</label>
                                <input type="text" value="${smppVendorAccount.bind_to}" class="form-control"
                                       id="bind_to"
                                       placeholder="sec ..." name="bind_to">
                            </div>

                            <div class="form-group">
                                <label for="con_fail_retry">Reconnect on connection failure</label>
                                <select class="form-control" name="con_fail_retry" id="con_fail_retry">
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="bind_npi">Bind NPI</label>
                                <select class="form-control" name="bind_npi" id="bind_npi">
                                    <c:forEach var="npi" items="${npiList}">
                                        <option value="${npi}">${npi.toString()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="bind_ton">Bind TON</label>
                                <select class="form-control" name="bind_ton" id="bind_ton">
                                    <c:forEach var="ton" items="${tonList}">
                                        <option value="${ton}">${ton.toString()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="dst_npi">Destination NPI</label>
                                <select class="form-control" name="dst_npi" id="dst_npi">
                                    <c:forEach var="npi" items="${npiList}">
                                        <option value="${npi}">${npi.toString()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="dst_ton">Destination TON</label>
                                <select class="form-control" name="dst_ton" id="dst_ton">
                                    <c:forEach var="ton" items="${tonList}">
                                        <option value="${ton}">${ton.toString()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="src_npi">Source NPI</label>
                                <select class="form-control" name="src_npi" id="src_npi">
                                    <c:forEach var="npi" items="${npiList}">
                                        <option value="${npi}">${npi.toString()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="src_ton">Destination TON</label>
                                <select class="form-control" name="src_ton" id="src_ton">
                                    <c:forEach var="ton" items="${tonList}">
                                        <option value="${ton}">${ton.toString()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="res_to">PDU response timeout (sec)</label>
                                <input type="text" value="${smppVendorAccount.res_to}" class="form-control"
                                       id="res_to"
                                       placeholder="sec ..." name="res_to">
                            </div>

                            <div class="form-group">
                                <label for="def_msg_id">Pre-defined message SMSC index</label>
                                <input type="text" value="${smppVendorAccount.def_msg_id}" class="form-control"
                                       id="def_msg_id"
                                       placeholder="sec ..." name="def_msg_id">
                            </div>

                            <div class="form-group">
                                <label for="priority">Message priority</label>
                                <select class="form-control" name="priority" id="priority">
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="con_loss_retry">Reconnect on connection loss</label>
                                <select class="form-control" name="con_loss_retry" id="con_loss_retry">
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="con_loss_delay">Reconnect delay on connection loss</label>
                                <input type="text" value="${smppVendorAccount.con_loss_delay}" class="form-control"
                                       id="con_loss_delay"
                                       placeholder="sec ..." name="con_loss_delay">
                            </div>

                            <div class="form-group">
                                <label for="requeue_delay">Requeue delay</label>
                                <input type="text" value="${smppVendorAccount.requeue_delay}" class="form-control"
                                       id="requeue_delay"
                                       placeholder="sec ..." name="requeue_delay">
                            </div>

                            <div class="form-group">
                                <label for="trx_to">trx_to</label>
                                <input type="text" value="${smppVendorAccount.trx_to}" class="form-control"
                                       id="trx_to"
                                       placeholder="sec ..." name="trx_to">
                            </div>

                            <div class="form-group">
                                <label for="ssl">SSL</label>
                                <select class="form-control" name="ssl" id="ssl">
                                    <option value="true">Yes</option>
                                    <option value="false">No</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="bind">Bind type</label>
                                <select class="form-control" name="bind" id="bind">
                                    <c:forEach var="bindType" items="${bindTypeList}">
                                        <option value="${bindType}">${bindType}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                    </div>

                    <%--Modal save--%>

                    <div id="modal_save" class="modal <%--animated flipInX--%> fade small-modal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalSave" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                    <h4 class="modal-title" id="myModalSave" style="text-align: center">Are you sure you
                                        want to save?</h4>
                                </div>
                                <%--<div class="modal-body">
                                    Modal content
                                </div>--%>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" id="post_go">OK</button>
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%--Modal error post--%>

                    <div id="modal_error" class="modal animated tada small-modal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalError" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content" style="border: 3px solid red;">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                    <h4 class="modal-title" id="myModalError" style="text-align: center; color: red">
                                        Data error!</h4>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- Vendor JS -->
<script type="text/javascript" src="/vendor/jquery/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="/vendor/tether/js/tether.min.js"></script>
<script type="text/javascript" src="/vendor/bootstrap4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/vendor/detectmobilebrowser/detectmobilebrowser.js"></script>
<script type="text/javascript" src="/vendor/jscrollpane/jquery.mousewheel.js"></script>
<script type="text/javascript" src="/vendor/jscrollpane/mwheelIntent.js"></script>
<script type="text/javascript" src="/vendor/jscrollpane/jquery.jscrollpane.min.js"></script>
<script type="text/javascript" src="/vendor/jquery-fullscreen-plugin/jquery.fullscreen-min.js"></script>
<script type="text/javascript" src="/vendor/waves/waves.min.js"></script>
<script type="text/javascript" src="/vendor/switchery/dist/switchery.min.js"></script>
<script type="text/javascript" src="/vendor/jsgrid/dist/jsgrid.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>


<script>

    //==================Установка начальных значений селектов==========

    $('#ripf').val("${smppVendorAccount.ripf}");

    $('#coding').val("${smppVendorAccount.coding}");

    $('#con_fail_retry').val("${smppVendorAccount.con_fail_retry}");

    $('#bind_npi').val("${smppVendorAccount.bind_npi}");

    $('#bind_ton').val("${smppVendorAccount.bind_ton}");

    $('#dst_npi').val("${smppVendorAccount.dst_npi}");

    $('#dst_ton').val("${smppVendorAccount.dst_ton}");

    $('#src_npi').val("${smppVendorAccount.src_npi}");

    $('#src_ton').val("${smppVendorAccount.src_ton}");

    $('#priority').val("${smppVendorAccount.priority}");

    $('#con_loss_retry').val("${smppVendorAccount.con_loss_retry}");

    $('#ssl').val("${smppVendorAccount.ssl}");

    $('#bind').val("${smppVendorAccount.bind}");

    //==================Установка начальных значений smppVendorIps==========

    var selectedItems = [];
    <c:forEach var="smppVendorIps" items="${smppVendorAccount.smppVendorIpsList}">
    newIpsRow = {};
    newIpsRow['id'] = ${smppVendorIps.id};
    newIpsRow['ip'] = '${smppVendorIps.ip}';
    newIpsRow['port'] = ${smppVendorIps.port};
    newIpsRow['systemId'] = '${smppVendorIps.systemId}';
    newIpsRow['password'] = '${smppVendorIps.password}';
    newIpsRow['systemType'] = '${smppVendorIps.systemType}';
    newIpsRow['submitThroughput'] = ${smppVendorIps.submitThroughput};
    newIpsRow['cid'] = '${smppVendorIps.cid}';
    newIpsRow['allowed'] = ${smppVendorIps.allowed};
    newIpsRow['softswitchId'] = '${smppVendorIps.softswitchId}';
    selectedItems.push(newIpsRow);
    </c:forEach>

    //==================softswitchList==========
    var softswitchList = [];
    newSoftswitch ={};
    newSoftswitch['id'] = '';
    newSoftswitch['name'] = 'No softswitch';
    softswitchList.push(newSoftswitch);
    <c:forEach var="softswitch" items="${softswitchList}">
    newSoftswitch ={};
    newSoftswitch['id'] = '${softswitch.id}';
    newSoftswitch['name'] = '${softswitch.name}';
    softswitchList.push(newSoftswitch);
    </c:forEach>

    //=================Назначение первичных значений переменных=============

    document.flag_customer = ${id_customer ne 0};
    document.return_in_customer = '${return_in_customer}';

</script>



<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_smpp_vendor_account_edit.js"></script>

</body>
</html>