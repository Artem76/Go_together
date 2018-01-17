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
    <title>${title}</title>

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
    <c:import url="/sections/${role}_sidebar.html"/>

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>

    <c:import url="/sections/header_middle1.html"/>
    <c:import url="/sections/header_middle2.html"/>
    ${name}
    <c:import url="/sections/header_end.html"/>

    <div class="site-content">
        <!-- Content -->
        <div class="content-area py-1">
            <div class="container-fluid">
                <div class="box box-block bg-white content_email_content">

                    <div class="scroll-x">
                        <div id="jsGrid-email-contents"></div>
                    </div>

                    <div class="clearfix"></div>
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

    document.emailRole = "${emailRole}";

</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_email_content_list.js"></script>

</body>
</html>
