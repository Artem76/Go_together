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
    <title>${title} - User</title>

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
    <link href="/css/select2.css" rel="stylesheet" />

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

    <form id="mainform" method="post" action="/admin_full_user_edit_save">
        <input type="hidden" name="id_user" value="${id_user}">
        <!-- Header -->
        <c:import url="/sections/header_begin.html"/>
        <li class="nav-item dropdown">
            <a href="#" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-primary" onclick="submitForms()">Save</button>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a href="/admin_full_user_list" data-toggle="" aria-expanded="false" class="nav-link">
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
                    <c:if test="${not empty error}">
                        <h4 class="text-danger">Data error</h4>
                    </c:if>
                    <c:if test="${not empty err_log}">
                        <h4 class="text-danger">Login or password error</h4>
                    </c:if>
                    <c:if test="${not empty err_admin}">
                        <h4 class="text-danger">At least 1 administrator must stay after saving</h4>
                    </c:if>

                    <div class="box box-block bg-white scroll-x" id="content">
                        <h5>User Edit</h5>
                        <div class="form-group">
                            <label for="name">Full name</label>
                            <input type="text" value="${user.name}" class="form-control" id="name"
                                   aria-describedby="emailHelp" placeholder="Name" name="name_user">
                        </div>
                        <div class="form-group">
                            <label for="login">Login</label>
                            <input type="text" value="${user.login}" class="form-control" id="login"
                                   aria-describedby="emailHelp" placeholder="Login" name="login_user">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" value="" class="form-control" id="password"
                                   aria-describedby="emailHelp" placeholder="Password" name="password_user">
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="phone" value="${user.phone}" class="form-control" id="phone"
                                   aria-describedby="emailHelp" placeholder="Phone" name="phone_user">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" value="${user.email}" class="form-control" id="email"
                                   aria-describedby="emailHelp" placeholder="Email" name="email_user">
                        </div>
                        <div class="form-group">
                            <label for="had_menu_role">Role</label><br>
                            <select class="js-example-basic-single dd_menu" name="role_user" id="had_menu_role" style="width: 100%">
                                <c:if test="${empty user.role}">
                                    <option value="0">No role</option>
                                </c:if>
                                <c:if test="${not empty user.role}">
                                    <option value="${user.role}">
                                        <c:out value="${fn:replace(user.role, '_', ' ')}"/>
                                    </option>
                                </c:if>
                                <c:forEach var="role" items="${roles}">
                                    <c:if test="${user.role ne role}">
                                        <option value="${role}">
                                            <c:out value="${fn:replace(role, '_', ' ')}"/>
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <div class="clearfix"></div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <%--<div style="height: 300px"></div>--%>
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
<script src="/js/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $(".js-example-basic-single").select2();
    });
</script>

</body>
</html>
