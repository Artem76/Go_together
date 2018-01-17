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
    <title>${title} - SMPP Client Accounts</title>

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
    <li class="nav-item dropdown">
        <a href="/admin_full_smpp_client_account_edit?id_client_account=0&id_customer=0&error=&err_name=&err_ips=&err_si=&return_in_customer=no"
           class="nav-link">
            <button class="btn btn-outline-primary">Add new Account</button>
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
                        <h4>Origination accounts</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Network operations center</a></li>
                            <li class="breadcrumb-item"><a>SMPP accounts</a></li>
                            <li class="breadcrumb-item"><a>Origination accounts</a></li>
                        </ol>
                    </div>
                </div>
                <div class="box box-block bg-white scroll-x content_account">
                    <div class="form-group" style="float: right">
                        <input type="text" class="form-control" id="regex"
                               placeholder="Search...">
                    </div>
                    <div class="clearfix"></div>

                    <div class="scroll-x">
                        <div id="jsGrid-accounts"></div>
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

    //==================Установка начальных значений Smpp client Account List==========

    smppClientAccountList = [];
    <c:forEach var="smppClientAccount" items="${smppClientAccountList}">
    newAccount = {};
    newAccount['idCustomer'] = ${smppClientAccount.customer.id};
    newAccount['idAccount'] = ${smppClientAccount.id};
    newAccount['customer'] = '${smppClientAccount.customer.name}';
    newAccount['account'] = '${smppClientAccount.name}';
    newAccount['systemId'] = '<c:forEach var="smppClientSystemId" items="${smppClientAccount.smppClientSystemIdList}" varStatus="vs">${smppClientSystemId.systemId}<c:if test="${not vs.last}">, </c:if></c:forEach>';
    smppClientAccountList.push(newAccount);
    </c:forEach>

    document.filter = "";

</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_all_smpp_client_account_list.js"></script>

</body>
</html>
