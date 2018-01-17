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
    <title>${title} - Basic tests</title>

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
    <link rel="stylesheet" href="/vendor/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">
    <link rel="stylesheet" href="/vendor/clockpicker/dist/bootstrap-clockpicker.min.css">
    <link rel="stylesheet" href="/vendor/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="/vendor/bootstrap-daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid.css">
    <link rel="stylesheet" href="/vendor/jsgrid/dist/jsgrid-theme.min.css">
    <link rel="stylesheet" href="/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.css">
    <link rel="stylesheet" href="/vendor/multi-select/css/multi-select.css">
    <link rel="stylesheet" href="/vendor/toastr/toastr.min.css">
    <link rel="stylesheet" href="/css/core.css">

    <link href="/css/select2.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/css/_mdr.css">

    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_sms_outgoing_payment_list.css">

    <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="fixed-sidebar fixed-header skin-default content-appear" style="background-color: #bbc2ce;">
<div class="wrapper">

    <!-- Preloader -->
    <%--<div class="preloader"></div>--%>

    <!-- Sidebar -->
    <c:import url="/sections/${role}_sidebar.html"/>

    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <%--<button class="btn btn-outline-primary">New payment</button>--%>
            <button type="button" class="btn btn-outline-primary" id="button_edit">New
                test
            </button>
        </a>
    </li>
    <%--<li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <div class='input-group date datetime_form' id='datetimepicker'>
                <input type='text' class="form-control"/>
                <span class="input-group-addon">
                        <span class="fa fa-calendar"></span>
                    </span>
            </div>
        </a>
    </li>--%>
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
                        <h4>Basic tests</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Network operations center</a></li>
                            <li class="breadcrumb-item"><a>Basic tests</a></li>
                        </ol>
                    </div>
                    <div class="col-md-2"></div>
                    <div class="col-md-2"></div>
                    <div class="col-md-2" style="padding: 5px;">
                        <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled"
                                id="btn_previous" style="position: absolute; right: 65px;">
                            <i class="ti-arrow-left"></i>
                        </button>
                        <button type="button" class="btn btn-black btn-circle waves-effect waves-light disabled"
                                id="btn_next" style="position: absolute; right: 20px;">
                            <i class="ti-arrow-right"></i>
                        </button>

                    </div>
                </div>
                <div class="box box-block bg-white scroll-y" id="tt_list">
                    <div id="jsGrid-payment-list"></div>
                </div>
            </div>
        </div>
    </div>

    <%--Modal payment--%>

    <div id="modal_details" class="modal fade big-modal" tabindex="-1" role="dialog"
         aria-labelledby="myModalPayment"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" style="width: 1000px">
            <div class="modal-content" style="width: 1000px">
                <div class="modal-body scroll-x" style="width: 1000px">
                    <div id="details_table">

                    </div>
                </div>
                <div class="modal-footer" style="width: 1000px">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>

    </div>



    <div id="modal_payment" class="modal fade small-modal" tabindex="-1" role="dialog"
         aria-labelledby="myModalPayment"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" style="width: 1000px">
            <div class="modal-content" style="width: 1000px">
                <div class="modal-body scroll-x" style="width: 1000px">
                    <div class="row">
                        <div class="col-md-6" style="height: 305px">
                            <div class="form-group">
                                <select id="template" class="js-example-basic-single dd_menu form-control"
                                        name="template"
                                        style="width: 100%" onchange="templateOnChange()">
                                    <option sourceaddr ="NotSelected" value="0">Select template</option>
                                    <c:forEach var="template" items="${templates}">
                                        <option sourceaddr ="${template.sourceAddr}" template="${template.text}" value="${template.id}">${template.sourceAddr} - ${template.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="text" id="source_addr" placeholder="Source address" name="source_addr" class="form-control"/>
                            </div>
                            <div class="form-group">
                                <textarea id="text" class="form-control new_email_body" placeholder="Type a message..." style="margin-top: 0px; margin-bottom: 0px; height: 172px;"></textarea>
                            </div>
                            <button type="button" class="btn btn-primary" id="save_template" style="width: 100%" onclick="saveTemplate()">Save as template</button>

                        </div>
                        <div class="col-md-6" style="height: 305px">
                            <div class="form-group" id="client_parent">
                                <select id="country" class="js-example-basic-single form-control"
                                        name="country"
                                        style="width: 100%" onchange="refillNetworks()">
                                    <option value="0">Select country</option>
                                    <c:forEach var="country" items="${countries}">
                                        <option value="${country.mcc}">${country.country}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <select id="networks" class="js-example-basic-single dd_menu form-control"
                                        name="networks"
                                        style="width: 100%">
                                    <option value="0">Select network</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <div class="btn_box">
                                    <select id="mnc_menu" class="dd_menu mnc_menu form-control select_mnc" data-plugin="select2"
                                            multiple="multiple" style="width: 100%" data-placeholder="Enter account ...">
                                        <c:forEach var="account" items="${accounts}">
                                            <option value="${account.account_id}">${account.customer_name}-${account.account_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="width: 1000px">
                    <button type="button" class="btn btn-primary" id="save">Save</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
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
<script type="text/javascript" src="/vendor/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>
<script type="text/javascript" src="/vendor/clockpicker/dist/jquery-clockpicker.min.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="/vendor/moment/moment.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/vendor/jsgrid/dist/jsgrid.min.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="/vendor/toastr/toastr.min.js"></script>
<script src="/js/select2.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>


<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_basic_test_list.js"></script>

</body>
</html>
