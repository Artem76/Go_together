<?xml version="1.0" encoding="utf-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>${title} - MDR</title>

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
    <link rel="stylesheet" href="/vendor/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">
    <link rel="stylesheet" href="/vendor/clockpicker/dist/bootstrap-clockpicker.min.css">
    <link rel="stylesheet" href="/vendor/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="/vendor/bootstrap-daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.css">
    <link rel="stylesheet" href="/vendor/select2/dist/css/select2.min.css">
    <link rel="stylesheet" href="/vendor/multi-select/css/multi-select.css">
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid.css">
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid-theme.min.css">


    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_mdr.css">
    <link href="/css/select2.css" rel="stylesheet"/>

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
    <!-- Template options -->
    <div class="template-options">
        <div class="to-toggle"><i class="ti-settings"></i></div>
        <div class="custom-scroll custom-scroll-dark">
            <div class="to-content">
                <h5>Select</h5>
                <div class="btn_box" id="client_parent">
                    <select id="client_menu" class="dd_menu client_menu js-example-basic-single select_static"
                            style="width: 100%">
                        <option value="0">All client account</option>
                        <c:forEach var="smppClientAccount" items="${smppClientAccountList}">
                            <option value="${smppClientAccount.id}">${smppClientAccount.customer.name}
                                - ${smppClientAccount.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="btn_box">
                    <select id="vendor_menu" class="dd_menu vendor_menu js-example-basic-single select_static"
                            style="width: 100%">
                        <option value="0">All vendor account</option>
                        <c:forEach var="smppVendorAccount" items="${smppVendorAccountList}">
                            <option value="${smppVendorAccount.id}">${smppVendorAccount.customer.name}
                                - ${smppVendorAccount.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="btn_box" id="mcc_parent">
                    <select id="mcc_menu" class="dd_menu mcc_menu form-control select_mcc" data-plugin="select2"
                            style="width: 100%" data-options="{ dropdownParent:$('#mcc_parent') }">
                        <option value="0">All MCC</option>
                        <c:forEach var="refbook" items="${refbooks}">
                            <option value="${refbook.mcc}">${refbook.country} (${refbook.mcc})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="btn_box">
                    <select id="mnc_menu" class="dd_menu mnc_menu form-control select_mnc" data-plugin="select2"
                            multiple="multiple" style="width: 100%" data-placeholder="Enter mnc...">
                        <option value="0">All MNC</option>
                    </select>
                </div>
                <input type="text" data-role="tagsinput" name="msgid_list_filter" placeholder="Enter msgid...">

                <input type="text" data-role="tagsinput" name="vendor_msgid_list_filter"
                       placeholder="Enter vendor msgid...">

                <input type="text" data-role="tagsinput" name="destination_addr_list_filter"
                       placeholder="Enter destination address...">

                <input type="text" data-role="tagsinput" name="source_addr_list_filter"
                       placeholder="Enter source address...">

                <h5><br>Order by</h5>
                <div class="btn_box">
                    <select id="sort_menu" class="dd_menu sort_menu form-control select_sort" data-plugin="select2"
                            multiple="multiple" style="width: 100%" data-placeholder="Enter order by...">
                        <option value="created_at">Submit date</option>
                        <option value="mcc">MCC</option>
                        <option value="mnc">MNC</option>
                        <option value="destination_addr">Destination address</option>
                        <option value="source_addr">Source address</option>
                        <option value="status_at">Report date</option>

                    </select>
                </div>
            </div>
        </div>
    </div>

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <button type="button"
                    class="btn btn-success w-min-sm mb-0-25 waves-effect waves-light generate" id="btn_generate">
                Generate
            </button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <input class="form-control date_form" type="text" name="daterange-with-time"
                   value="${date_start} - ${date_end}"/>
        </a>
    </li>
    <c:import url="/sections/header_middle1.html"/>
    <c:import url="/sections/header_middle2.html"/>
    ${name}
    <c:import url="/sections/header_end.html"/>

    <div class="site-content">
        <!-- Content -->
        <div class="content-area py-1">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                        <h4>Message Detail Records</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Network operations center</a></li>
                            <li class="breadcrumb-item"><a>MDR</a></li>
                        </ol>
                    </div>
                    <div class="col-md-2"></div>
                    <div class="col-md-2"></div>
                    <div class="col-md-2" style="padding: 5px;">
                        <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled" id="btn_save" style="position: absolute; right: 110px;">
                            <i class="ti-save"></i>
                        </button>
                        <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled" id="btn_previous" style="position: absolute; right: 65px;">
                            <i class="ti-arrow-left"></i>
                        </button>
                        <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled" id="btn_next" style="position: absolute; right: 20px;">
                            <i class="ti-arrow-right"></i>
                        </button>

                    </div>
                </div>

                <div class="box box-block bg-white content_email_account" id="mdr_list">
                    <div id="jsGrid-mdr"></div>
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
<script type="text/javascript" src="/vendor/jscrollpane/jquery.jscrollpane.min.js"></script>
<script type="text/javascript" src="/vendor/jquery-fullscreen-plugin/jquery.fullscreen-min.js"></script>
<script type="text/javascript" src="/vendor/waves/waves.min.js"></script>
<script type="text/javascript" src="/vendor/switchery/dist/switchery.min.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>
<script type="text/javascript" src="/vendor/clockpicker/dist/jquery-clockpicker.min.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="/vendor/moment/moment.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="/vendor/jsgrid/dist/jsgrid.min.js"></script>
<%--<scrip type="text/javascript" src="/vendor/select2/dist/js/select2.min.js"></scrip>
<scrip type="text/javascript" src="/vendor/multi-select/js/jquery.multi-select.js"></scrip>--%>


<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>

<script type="text/javascript" src="/js/_main.js"></script>
<script src="/js/select2.min.js"></script>
<script type="text/javascript" src="/js/_mdr.js"></script>
<script>

</script>

</body>
</html>
