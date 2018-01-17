<?xml version="1.0" encoding="utf-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title>${title} - Dialpeer</title>

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
    <link rel="stylesheet" href="/css/_vendor_dialpeer_edit.css">


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

    <input type="hidden" name="id_vendor_dialpeer" value="${id_vendor_dialpeer}" id="val_vendor_dialpeer">

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>
    <li class="nav-item dropdown">
        <a href="#" aria-expanded="false" class="nav-link">
            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_save">Save
            </button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="/admin_full_vendor_dialpeer_list" class="nav-link">
            <button type="button" class="btn btn-outline-warning">Cancel</button>
        </a>
    </li>
    <c:if test="${id_vendor_dialpeer ne 0}">
        <li class="nav-item dropdown">
            <a href="#" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_delete">Delete vendor dialpeer
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

                <div class="box box-block bg-white scroll-x content_dialpeer">

                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" value="${vendorDialpeer.name}" class="form-control" id="name"
                               placeholder="Name" name="name_vendor_dialpeer">
                    </div>

                    <div class="scroll-x" name="vendorDialpeerChildList">
                        <div id="jsGrid-systemId"></div>
                    </div>

                    <div class="clearfix"></div>

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

                        <%--Modal delete--%>

                        <div id="modal_delete" class="modal <%--animated flipInX--%> fade small-modal" tabindex="-1"
                             role="dialog" aria-labelledby="myModalDelete" aria-hidden="true">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">×</span>
                                        </button>
                                        <h4 class="modal-title" id="myModalDelete" style="text-align: center">Are you sure you
                                            want to delete?</h4>
                                    </div>
                                    <%--<div class="modal-body">
                                        Modal content
                                    </div>--%>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary" id="post_delete">OK</button>
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
                <%--<div style="height: 300px"></div>--%>
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
    //==================Установка начальных значений Vendor SystemId==========

    var selectedItems = [];
    <c:forEach var="vendorDialpeerChild" items="${vendorDialpeer.vendorDialpeerChildList}">
    newSystemIdRow = {};
    newSystemIdRow['id'] = ${vendorDialpeerChild.id};
    newSystemIdRow['id_smppVendorIps'] = ${vendorDialpeerChild.smppVendorIps.id};
    newSystemIdRow['weight'] = ${vendorDialpeerChild.weight};
    selectedItems.push(newSystemIdRow);
    </c:forEach>

    //==================smppVendorIpsList==========
    var smppVendorIpsList = [];
    newSmppVendorIps ={};
    newSmppVendorIps['id'] = 0;
    newSmppVendorIps['name'] = 'No system id';
    smppVendorIpsList.push(newSmppVendorIps);
    <c:forEach var="smppVendorIps" items="${smppVendorIpsList}">
    newSmppVendorIps ={};
    newSmppVendorIps['id'] = ${smppVendorIps.id};
    newSmppVendorIps['name'] = '${smppVendorIps.smppVendorAccount.customer.name} - ${smppVendorIps.systemId}';
    smppVendorIpsList.push(newSmppVendorIps);
    </c:forEach>

    //=================Назначение первичных значений переменных=============

    document.id_vendor_dialpeer = ${id_vendor_dialpeer};

</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_vendor_dialpeer.js"></script>

</body>
</html>
