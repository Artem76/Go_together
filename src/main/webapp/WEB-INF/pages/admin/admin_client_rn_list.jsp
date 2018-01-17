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
    <title>${title} - Client RN</title>

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
    <link href="/css/select2.css" rel="stylesheet"/>

    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_client_rn_list.css">

    <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="fixed-sidebar fixed-header skin-default content-appear" style="background-color: #bbc2ce;">
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
                        <option value="0">All customer</option>
                        <c:forEach var="account" items="${accounts}">
                            <option value="${account.id}">${account.getCustomer().name} - ${account.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="btn_box" id="mcc_parent">
                    <select id="mcc_menu" class="dd_menu mcc_menu form-control select_mcc" data-plugin="select2"
                            style="width: 100%" data-options="{ dropdownParent:$('#mcc_parent') }">
                        <option value="">All MCC</option>
                        <c:forEach var="refbook" items="${refbooks}">
                            <option value="${refbook.mcc}">${refbook.country} (${refbook.mcc})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="btn_box">
                    <select id="mnc_menu" class="dd_menu mnc_menu form-control select_mnc" data-plugin="select2"
                            style="width: 100%" data-options="{ dropdownParent:$('#mcc_parent') }">
                        <option value="">All MNC</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>
    <li class="nav-item dropdown">
        <a href="/admin_full_client_rn_edit?id=0" class="nav-link">
            <button class="btn btn-outline-primary">New Rate Notification</button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <button class="btn btn-success w-min-sm mb-0-25 waves-effect waves-light" id="refresh_list">Refresh</button>
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
                        <h4>Client rates update</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Ratemode</a></li>
                            <li class="breadcrumb-item"><a>Client RN</a></li>
                        </ol>
                    </div>
                    <div class="col-md-2"></div>
                    <div class="col-md-2"></div>
                    <div class="col-md-2" style="padding: 5px;">
                       <%-- <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled" id="btn_save" style="position: absolute; right: 110px;">
                            <i class="ti-save"></i>
                        </button>--%>
                        <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled" id="btn_previous" style="position: absolute; right: 65px;">
                            <i class="ti-arrow-left"></i>
                        </button>
                        <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled" id="btn_next" style="position: absolute; right: 20px;">
                            <i class="ti-arrow-right"></i>
                        </button>

                    </div>
                </div>
                <div class="box box-block bg-white scroll-y" id="tt_list">
                    <div id="jsGrid-client-rn-list"></div>
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
<script src="/js/select2.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>


<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_client_rn_list.js"></script>

</body>
</html>
