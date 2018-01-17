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
    <title>${title} - Incoming Payments</title>

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
    <link rel="stylesheet" href="/css/_test_numbers_pool.css">

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

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <button type="button" class="btn btn-outline-primary" id="button_edit">New
                number
            </button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <button class="btn btn-success w-min-sm mb-0-25 waves-effect waves-light" id="refresh_list">Refresh</button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <select id="countryNetwork_menu" class="dd_menu countryNetwork_menu js-example-basic-single select_static">
                <option value="0">All network</option>
                <c:forEach var="countryNetwork" items="${countryNetworkList}">
                    <option value="${countryNetwork.mcc_mnc}">${countryNetwork.text}</option>
                </c:forEach>
            </select>
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
                        <h4>Test numbers pool</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Network Operation Center</a></li>
                            <li class="breadcrumb-item"><a>Test numbers pool</a></li>
                        </ol>
                    </div>
                </div>
                <div class="box box-block bg-white scroll-y" id="number_list">
                    <div id="jsGrid-number-list"></div>
                </div>
            </div>
        </div>
    </div>

    <%--Modal payment--%>

    <div id="modal_number" class="modal fade small-modal" tabindex="-1" role="dialog"
         aria-labelledby="myModalnumber"
         aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-body scroll-x">

                    <div class="form-group">
                        <input type="checkbox" id="invalid" class="td-checkbox"/>
                        <label for="invalid">Invalid</label>
                    </div>
                    <div class="form-group">
                        <label for="number">Number</label>
                        <input type="text" id="number" name="number" class="form-control"/>
                    </div>
                    <button type="button" class="btn btn-outline-primary w-min-sm mb-0-25 waves-effect waves-light" id="btn_fill">Fill</button>
                    <div class="form-group">
                        <label for="mcc">MCC</label>
                        <input type="text" id="mcc" name="mcc" class="form-control"/>
                    </div>
                    <div class="form-group">
                    <label for="mnc">MNC</label>
                    <input type="text" id="mnc" name="mnc" class="form-control"/>
                </div>
                    <input type="hidden" id="id">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="save">Save</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

</div>

<%--Modal delete--%>

<div id="modal_delete" class="modal fade small-modal" tabindex="-1"
     role="dialog" aria-labelledby="myModalDelete" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalSave" style="text-align: center">Are you sure you
                    want to delete?</h4>
            </div>
            <%--<div class="modal-body">
                Modal content
            </div>--%>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="delete_go">OK</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
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
<script type="text/javascript" src="/js/_test_numbers_pool.js"></script>

</body>
</html>
