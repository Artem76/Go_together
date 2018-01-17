<?xml version="1.0" encoding="utf-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>${title} - Dashboard</title>

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
    <c:import url="/sections/${role}_sidebar.html" />

    <!-- Header -->
    <c:import url="/sections/header_begin.html" />
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <button type="button"
                    class="btn btn-success w-min-sm mb-0-25 waves-effect waves-light" onclick="fillIndexData()">
                Refresh
            </button>
        </a>
    </li>
    <c:import url="/sections/header_middle1.html" />
    <c:import url="/sections/header_middle2.html"/>
    ${name}
    <c:import url="/sections/header_end.html" />

    <div class="site-content">
        <!-- Content -->
        <div class="content-area py-1">
            <div class="container-fluid">
                <div class="row row-md">
                    <div class="col-lg-3 col-md-6 col-xs-12">
                        <div class="box box-block bg-white tile tile-1 mb-2">
                            <div class="t-icon right"><span class="bg-primary"></span><i class="ti-email"></i>
                            </div>
                            <div class="t-content">
                                <h6 class="text-uppercase mb-1">Messages</h6>
                                <h1 class="mb-1" id="messagesCount">0</h1>
                                <span class="tag tag-primary mr-0-5" id="lastMessageCount">+125</span>
                                <span class="text-muted font-90">from previos refresh</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-xs-12">
                        <div class="box box-block bg-white tile tile-1 mb-2">
                            <div class="t-icon right"><span class="bg-success"></span><i
                                    class="ti-arrow-down"></i></div>
                            <div class="t-content">
                                <h6 class="text-uppercase mb-1">Incoming Sum</h6>
                                <h1 class="mb-1" id="incomingSum">0</h1>
                                <span class="tag tag-success mr-0-5" id="lastIncomingSum"></span>
                                <span class="text-muted font-90">from previos refresh</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-xs-12">
                        <div class="box box-block bg-white tile tile-1 mb-2">
                            <div class="t-icon right"><span class="bg-danger"></span><i class="ti-arrow-up"></i>
                            </div>
                            <div class="t-content">
                                <h6 class="text-uppercase mb-1">Outgoing Sum</h6>
                                <h1 class="mb-1" id="outgoingSum">0</h1>
                                <span class="tag tag-danger mr-0-5" id="lastOutgoingSum"></span>
                                <span class="text-muted font-90">from previos refresh</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-xs-12">
                        <div class="box box-block bg-white tile tile-1 mb-2">
                            <div class="t-icon right"><span class="bg-warning"></span><i class="ti-bar-chart"></i>
                            </div>
                            <div class="t-content">
                                <h6 class="text-uppercase mb-1">Profit</h6>
                                <h1 class="mb-1" id="profit">0</h1>
                                <span class="tag tag-warning mr-0-5" id="lastProfit"></span>
                                <span class="text-muted font-90">from previos refresh</span>
                            </div>
                        </div>
                    </div>
                </div>
                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Message Activity</a>
                    </li>
                </ul>
                <div class="box box-block bg-white b-t-0 mb-2">
                    <div class="chart-container demo-chart">
                        <div id="main-chart" class="chart-placeholder"></div>
                    </div>
                </div>
                <div class="box box-block bg-white">
                    <div class="row row-md mb-2">
                        <div class="col-md-6">
                            <h5 class="h-underline h-underline-50 h-underline-success">Client consumption</h5>
                            <div id="jsGrid-client-consumption"></div>
                        </div>
                        <div class="col-md-6">
                            <h5 class="h-underline h-underline-50 h-underline-warning">Vendor consumption</h5>
                            <div id="jsGrid-vendor-consumption"></div>
                        </div>

                    </div>
                </div>
                <div class="box box-block bg-white">
                    <h5 class="h-underline h-underline-50 h-underline-info">Profit</h5>
                    <div id="jsGrid-profit"></div>
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
<script type="text/javascript" src="/vendor/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="/vendor/flot/jquery.flot.resize.min.js"></script>
<script type="text/javascript" src="/vendor/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
<script type="text/javascript" src="/vendor/CurvedLines/curvedLines.js"></script>
<script type="text/javascript" src="/vendor/TinyColor/tinycolor.js"></script>
<script type="text/javascript" src="/vendor/sparkline/jquery.sparkline.min.js"></script>
<script type="text/javascript" src="/vendor/raphael/raphael.min.js"></script>
<script type="text/javascript" src="/vendor/morris/morris.min.js"></script>
<script type="text/javascript" src="/vendor/peity/jquery.peity.js"></script>
<script type="text/javascript" src="/vendor/jsgrid/dist/jsgrid.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>
<script type="text/javascript" src="/js/index.js"></script>
<script type="text/javascript" src="/js/_main.js"></script>


</body>
</html>
