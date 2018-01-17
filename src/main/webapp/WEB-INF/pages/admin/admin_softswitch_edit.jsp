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
    <title>${title} - Softswitch</title>

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

    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_softswitch_edit.css">


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

    <%--<form id="mainform" method="post" action="/admin_full_softswitch_edit_save">--%>
        <input type="hidden" name="id_softswitch" value="${id_softswitch}">
        <%--<input type="hidden" name="mdr_load_enabled" value="${id_softswitch}">
        <input type="hidden" name="mdr_load_enabled" value="${id_softswitch}">--%>
        <!-- Header -->
        <c:import url="/sections/header_begin.html"/>
        <li class="nav-item dropdown">
            <a href="#" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_save">Save
                </button>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a href="/admin_full_softswitch_list" class="nav-link">
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
                    <div class="row">
                        <div class="col-md-6">
                            <h4>Softswitch update</h4>
                            <ol class="breadcrumb no-bg mb-1">
                                <li class="breadcrumb-item"><a>Connectivity</a></li>
                                <li class="breadcrumb-item"><a>Softswitches</a></li>
                            </ol>
                        </div>
                    </div>
                    <div class="box box-block bg-white scroll-x" id="content_softswitch">
                        <h5>Softswitch Edit</h5>
                        <div class="form-group">
                            <label for="had_menu_type">Type</label><br>
                            <select class="ui search dropdown form-control" name="type" id="had_menu_type">
                                <c:if test="${empty softswitch.type}">
                                    <option value="0">No type</option>
                                </c:if>
                                <c:if test="${not empty softswitch.type}">
                                    <option value="${softswitch.type}">
                                        <c:out value="${softswitch.type}"/>
                                    </option>
                                </c:if>
                                <c:forEach var="type" items="${types}">
                                    <c:if test="${softswitch.type ne type}">
                                        <option value="${type}">
                                            <c:out value="${type}"/>
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>

                            <div class="clearfix"></div>
                        </div>
                        <div class="form-group">
                            <label for="name">Name</label>
                            <input type="text" value="${softswitch.name}" class="form-control" id="name" placeholder="Name" name="name">
                        </div>
                        <div class="form-group">
                            <label for="host">Host</label>
                            <input type="text" value="${softswitch.host}" class="form-control" id="host" placeholder="Host" name="host">
                        </div>
                        <div class="form-group">
                            <label for="mdr_load_enabled">MDR load enabled</label>
                            <input type="checkbox" value="true"
                                   id="mdr_load_enabled"
                                   name="mdr_load_enabled" class="td-checkbox"
                            <c:if test="${mdr_load_enabled}">
                                   CHECKED
                            </c:if>
                            >
                        </div>
                        <div class="form-group">
                            <label for="api_exchange_enabled">API exchange enabled</label>
                            <input type="checkbox" value="true"
                                   id="api_exchange_enabled"
                                   name="api_exchange_enabled" class="td-checkbox"
                            <c:if test="${api_exchange_enabled}">
                                   CHECKED
                            </c:if>
                            >
                        </div>
                        <h5>MySQL settings</h5>
                        <div class="form-group">
                            <label for="mysql_port">Port</label>
                            <input type="number" value="${softswitch.mysql_port}" class="form-control" id="mysql_port" placeholder="Port" name="mysql_port">
                        </div>
                        <div class="form-group">
                            <label for="mysql_database">Database</label>
                            <input type="text" value="${softswitch.mysql_database}" class="form-control" id="mysql_database" placeholder="Database" name="mysql_database">
                        </div>
                        <div class="form-group">
                            <label for="mysql_username">Username</label>
                            <input type="text" value="${softswitch.mysql_username}" class="form-control" id="mysql_username" placeholder="Username" name="mysql_username">
                        </div>

                        <div class="form-group">
                            <label for="mysql_password">Password</label>
                            <input type="text" value="${softswitch.mysql_password}" class="form-control" id="mysql_password" placeholder="Password" name="mysql_password">
                        </div>
                        <div class="form-group">
                            <label for="mysql_dr_table">Detail records table name</label>
                            <input type="text" value="${softswitch.mysql_dr_table}" class="form-control" id="mysql_dr_table" placeholder="Detail records table name" name="mysql_dr_table">
                        </div>
                        <div class="form-group">
                            <label for="mysql_totals_table">Totals table name</label>
                            <input type="text" value="${softswitch.mysql_totals_table}" class="form-control" id="mysql_totals_table" placeholder="Totals table name" name="mysql_totals_table">
                        </div>
                        <div class="form-group">
                            <label for="mysql_stats_table">Statistics table name</label>
                            <input type="text" value="${softswitch.mysql_stats_table}" class="form-control" id="mysql_stats_table" placeholder="Statistics table name" name="mysql_stats_table">
                        </div>
                        <div class="form-group">
                            <label for="mysql_reports_table">Reports table name</label>
                            <input type="text" value="${softswitch.mysql_reports_table}" class="form-control" id="mysql_reports_table" placeholder="Reports table name" name="mysql_reports_table">
                        </div>

                        <div class="form-group">
                            <h5>Jasmin SMS Gateway settings</h5>
                            <label for="api_port">API port</label>
                            <input type="number" value="${softswitch.api_port}" class="form-control" id="api_port" placeholder="API port" name="api_port">
                        </div>
                        <div class="form-group">
                            <label for="api_username">Username</label>
                            <input type="text" value="${softswitch.api_username}" class="form-control" id="api_username" placeholder="Username" name="api_username">
                        </div>
                        <div class="form-group">
                            <label for="api_password">Passoword</label>
                            <input type="text" value="${softswitch.api_password}" class="form-control" id="api_password" placeholder="Password" name="api_password">
                        </div>
                    </div>
                    <div class="clearfix"></div>
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
    <%--</form>--%>

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

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>
<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_softswitch_edit.js"></script>

</body>
</html>
