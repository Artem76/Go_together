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
    <title>${title} - Reference Book</title>

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
    <link rel="stylesheet" href="/vendor/DataTables/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="/vendor/DataTables/Responsive/css/responsive.bootstrap4.min.css">
    <link rel="stylesheet" href="/vendor/DataTables/Buttons/css/buttons.dataTables.min.css">
    <link rel="stylesheet" href="/vendor/DataTables/Buttons/css/buttons.bootstrap4.min.css">

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

    <form id="mainform" method="post" action="/admin_full_refbook_edit_save">
        <input type="hidden" name="id_refbook" value="${id_refbook}">
        <input type="hidden" name="state" value="country">
        <!-- Header -->
        <c:import url="/sections/header_begin.html"/>
        <li class="nav-item dropdown">
            <a href="#" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-primary" onclick="submitForms()">Save</button>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a href="/admin_full_refbook_list" data-toggle="" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-warning">Cancel</button>
            </a>
        </li>
        <c:if test="${id_refbook ne 0}">
            <li class="nav-item dropdown">
                <a href="#" data-toggle="" aria-expanded="false" class="nav-link">
                    <button type="button" class="btn btn-outline-danger" onclick="deleteRefboook('${id_refbook}')">Delete
                    </button>
                </a>
            </li>
        </c:if>

        <c:import url="/sections/header_middle1.html"/>
        <c:import url="/sections/header_middle2.html"/>
        ${name}
        <c:import url="/sections/header_end.html"/>

        <div class="site-content">
            <!-- Content -->
            <div class="content-area py-1">
                <div class="container-fluid">
                    <h4>${refbook_title}</h4>
                    <ol class="breadcrumb no-bg mb-1">
                        <li class="breadcrumb-item"><a>Network operations center</a></li>
                        <li class="breadcrumb-item"><a>Reference book</a></li>
                    </ol>
                    <div class="box box-block bg-white scroll-x" id="content">
                        <div class="form-group">
                            <label for="country">Country name</label>
                            <input type="text" value="${refbook.country}" class="form-control" id="country"
                                   placeholder="Country name" name="country_refbook">
                        </div>
                        <div class="form-group">
                            <label for="mcc">MCC</label>
                            <input type="text" value="${refbook.mcc}" class="form-control" id="mcc"
                                   placeholder="MCC" name="mcc_refbook">
                        </div>
                        <div class="form-group">
                            <label for="dial_code">Dialcode</label>
                            <input type="text" value="${refbook.dialCode}" class="form-control" id="dial_code"
                                   placeholder="Dialcode" name="dialCode_refbook">
                        </div>
                        <div class="form-group">
                            <label for="min_length">Min length</label>
                            <input type="text" value="${refbook.minLength}" class="form-control" id="min_length"
                                   placeholder="Min length" name="minLength_refbook">
                        </div>
                        <div class="form-group">
                            <label for="max_length">Max length</label>
                            <input type="text" value="${refbook.maxLength}" class="form-control" id="max_length"
                                   placeholder="Max length" name="maxLength_refbook">
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
    </form>

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
<script type="text/javascript" src="/vendor/DataTables/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/Responsive/js/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/Responsive/js/responsive.bootstrap4.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/Buttons/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/Buttons/js/buttons.bootstrap4.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/JSZip/jszip.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/pdfmake/build/pdfmake.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/pdfmake/build/vfs_fonts.js"></script>
<script type="text/javascript" src="/vendor/DataTables/Buttons/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/Buttons/js/buttons.print.min.js"></script>
<script type="text/javascript" src="/vendor/DataTables/Buttons/js/buttons.colVis.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>
<script type="text/javascript" src="/js/tables-datatable.js"></script>
<script type="text/javascript" src="/js/_main.js"></script>

</body>
</html>
