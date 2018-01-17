<?xml version="1.0" encoding="utf-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Title -->
    <title>Volumes</title>

    <!-- VendorRssXml CSS -->
    <link rel="stylesheet" href="/vendor/bootstrap4/css/bootstrap.min.css">
    <link rel="stylesheet" href="/vendor/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="/vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/vendor/animate.css/animate.min.css">
    <link rel="stylesheet" href="/vendor/jscrollpane/jquery.jscrollpane.css">
    <link rel="stylesheet" href="/vendor/waves/waves.min.css">
    <link rel="stylesheet" href="/vendor/switchery/dist/switchery.min.css">
    <link rel="stylesheet" href="/vendor/morris/morris.css">
    <link rel="stylesheet" href="/vendor/jvectormap/jquery-jvectormap-2.0.3.css">

    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

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
    <c:import url="/sections/admin_sidebar.html" />

    <!-- Header -->
    <c:import url="/sections/header_begin.html" />
    <li class="nav-item dropdown hidden-sm-down">
        <a href="#"  aria-expanded="false" class="nav-link">
            <h4>Report User</h4>
        </a>
    </li>
    <c:import url="/sections/header_middle.html" />
    ${name}
    <c:import url="/sections/header_end.html" />

    <div class="site-content">
        <!-- Content -->

    </div>

</div>

<!-- VendorRssXml JS -->
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
<script type="text/javascript" src="/vendor/jvectormap/jquery-jvectormap-2.0.3.min.js"></script>
<script type="text/javascript" src="/vendor/jvectormap/jquery-jvectormap-world-mill.js"></script>
<script type="text/javascript" src="/vendor/peity/jquery.peity.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>
<script type="text/javascript" src="/js/index.js"></script>
</body>
</html>
