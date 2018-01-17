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
    <title>${title} - SMPP Client Account</title>

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
    <link rel="stylesheet" href="/css/_smpp_client_account_edit.css">

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

    <c:if test="${id_customer ne 0}">
        <input type="hidden" name="id_customer" value="${id_customer}">
    </c:if>
    <input type="hidden" name="id_client_account" value="${id_client_account}">

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>
    <li class="nav-item dropdown">
        <a href="#" aria-expanded="false" class="nav-link">
            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_save">Save
            </button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a
                <c:if test="${id_customer ne 0 and return_in_customer ne 'no'}"> href="/admin_full_customer_edit?id_customer=${id_customer}&error=&err_name="</c:if>
                <c:if test="${id_customer eq 0 or return_in_customer eq 'no'}"> href="/admin_full_all_smpp_client_account_list"</c:if>
                class="nav-link">
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
                <div class="box box-block bg-white content_client">
                    <c:if test="${id_customer eq 0}">
                        <div class="form-group">
                            <select class="form-control" name="id_customer"
                                    id="search-select" <%--style="width: 200px"--%>>
                                <option value="0">No customer</option>
                                <c:forEach var="customer" items="${customers}">
                                    <option value="${customer.id}">${customer.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" value="${smppClientAccount.name}" class="form-control" id="name"
                               placeholder="Name" name="name_client_account">
                    </div>
                        <label for="system_type">System Type</label>
                        <div class="row">
                            <div class="col-md-10">
                                <div class="form-group">
                                    <input type="text" value="${smppClientAccount.systemType}" class="form-control"
                                           id="system_type" placeholder="System Type" name="system_type_client_account">
                                </div>
                            </div>
                            <div class="col-md-2" style="padding: 6px">
                                <input type="checkbox"
                                       id="dontSync"
                                       name="dontSync" class="td-checkbox"
                                <c:if test="${smppClientAccount.dontSync}">
                                       CHECKED
                                </c:if>
                                >
                                <label for="dontSync">Softswitch synchronization disabled</label>
                            </div>
                        </div>
                    <div class="scroll-x mb-1" name="ips_list">
                        <div id="jsGrid-smpp-client-ips"></div>
                    </div>
                    <div class="scroll-x" name="systemId_list">
                        <div id="jsGrid-smpp-client-systemId"></div>
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

    //==================Установка начальных значений smppClientIps==========

    var selectedItemsIps = [];
    <c:forEach var="smppClientIps" items="${smppClientAccount.smppClientIpsList}">
    newIpsRow = {};
    newIpsRow['id'] = ${smppClientIps.id};
    newIpsRow['ip'] = '${smppClientIps.ip}';
    newIpsRow['allowed'] = ${smppClientIps.allowed};
    selectedItemsIps.push(newIpsRow);
    </c:forEach>

    //==================Установка начальных значений smppClientSystemId==========

    var selectedItemsSystemId = [];
    <c:forEach var="smppClientSystemId" items="${smppClientAccount.smppClientSystemIdList}">
    newIpsRow = {};
    newIpsRow['id'] = ${smppClientSystemId.id};
    newIpsRow['systemId'] = '${smppClientSystemId.systemId}';
    newIpsRow['password'] = '${smppClientSystemId.password}';
    newIpsRow['uid'] = '${smppClientSystemId.uid}';
    selectedItemsSystemId.push(newIpsRow);
    </c:forEach>

    //=================Назначение первичных значений переменных=============

    document.flag_customer = ${id_customer ne 0};
</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_smpp_client_account_edit.js"></script>


</body>
</html>
