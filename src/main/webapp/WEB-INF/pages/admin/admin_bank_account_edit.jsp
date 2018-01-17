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
    <title>${title} - Bank Account</title>

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
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid.css">
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid-theme.min.css">
    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
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

    <form id="mainform" method="post" action="/admin_full_bank_account_edit_save">
        <input type="hidden" name="id_bank_account" value="${id_bank_account}">
        <!-- Header -->
        <c:import url="/sections/header_begin.html"/>
        <li class="nav-item dropdown">
            <a href="#" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-primary" onclick="submitForms()">Save</button>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a href="/admin_full_bank_account_list" data-toggle="" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-warning">Cancel</button>
            </a>
        </li>

        <c:import url="/sections/header_middle1.html"/>

        <%--<li class="nav-item dropdown">
            <a href="/admin_full_customer_smpp_vendor_account_list?id_customer=${id_customer}" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-purple">Termination Accounts</button>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a href="#" data-toggle="" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-purple">Origination Accounts</button>
            </a>
        </li>--%>
        <c:import url="/sections/header_middle2.html"/>
        ${name_admin}
        <c:import url="/sections/header_end.html"/>

        <div class="site-content">
            <!-- Content -->
            <div class="content-area py-1">
                <div class="container-fluid">
                    <h4>${bank_account_name}</h4>
                    <ol class="breadcrumb no-bg mb-1">
                        <li class="breadcrumb-item"><a>Finance</a></li>
                        <li class="breadcrumb-item"><a>Bank accounts</a></li>
                    </ol>
                    <div class="box box-block bg-white content_customer" style="border-top: 0">
                        <div class="form-group">
                            <label for="name">Name</label>
                            <input type="text" value="${bank_account.name}" class="form-control" id="name"
                                   aria-describedby="emailHelp" placeholder="Name" name="name">
                        </div>
                        <div class="form-group">
                            <label for="name">Holder</label>
                            <input type="text" value="${bank_account.holder}" class="form-control" id="holder"
                                   aria-describedby="emailHelp" placeholder="Holder" name="holder">
                        </div>
                        <div class="form-group">
                            <label for="name">Account number</label>
                            <input type="text" value="${bank_account.account_number}" class="form-control" id="account_number"
                                   aria-describedby="emailHelp" placeholder="Account number" name="account_number">
                        </div>
                        <div class="form-group">
                            <label for="name">Address</label>
                            <input type="text" value="${bank_account.address}" class="form-control" id="address"
                                   aria-describedby="emailHelp" placeholder="Address" name="address">
                        </div>
                        <div class="form-group">
                            <label for="name">IBAN</label>
                            <input type="text" value="${bank_account.iban}" class="form-control" id="iban"
                                   aria-describedby="emailHelp" placeholder="IBAN" name="iban">
                        </div>
                        <div class="form-group">
                            <label for="name">SWIFT</label>
                            <input type="text" value="${bank_account.swift}" class="form-control" id="swift"
                                   aria-describedby="emailHelp" placeholder="SWIFT" name="swift">
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
<script type="text/javascript" src="/vendor/jsgrid/dist/jsgrid.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>
<script type="text/javascript" src="/js/tables-datatable.js"></script>

<script>

    //==================Установка начальных значений smppVendorAccount==========

    vendorList = [];
    <c:forEach var="smppVendorAccount" items="${customer.smppVendorAccountList}">
    newVendor = {};
    newVendor['id'] = ${smppVendorAccount.id};
    newVendor['name'] = '${smppVendorAccount.name}';
    newVendor['systemId'] = '<c:forEach var="smppVendorIps" items="${smppVendorAccount.smppVendorIpsList}" varStatus="vs">${smppVendorIps.systemId}<c:if test="${not vs.last}">, </c:if></c:forEach>';
    vendorList.push(newVendor);
    </c:forEach>

    //==================Установка начальных значений smppClientAccount==========

    var clientList = [];
    <c:forEach var="smppClientAccount" items="${customer.smppClientAccountList}">
    newClient = {};
    newClient['id'] = ${smppClientAccount.id};
    newClient['name'] = '${smppClientAccount.name}';
    newClient['systemId'] = '<c:forEach var="smppClientSystemId" items="${smppClientAccount.smppClientSystemIdList}" varStatus="vs">${smppClientSystemId.systemId}<c:if test="${not vs.last}">, </c:if></c:forEach>';
    clientList.push(newClient);
    </c:forEach>

</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script src="/js/select2.min.js"></script>
<script>
    $(document).ready(function () {
        $(".js-example-basic-single").select2();
    });
</script>
<script type="text/javascript" src="/js/_customer_edit.js"></script>

</body>
</html>
