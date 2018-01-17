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
    <title>${title} - Outgoing Invoice</title>

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

        <input type="hidden" name="id_invoice" value="${id_invoice}" id="id_invoice">
        <!-- Header -->
        <c:import url="/sections/header_begin.html"/>
        <li class="nav-item dropdown">
            <a href="#" aria-expanded="false" class="nav-link">
                <button type="button" class="btn btn-outline-primary" onclick="">Recalculate</button>
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
                    <h4>Outgoing invoice #${invoice_number}</h4>
                    <ol class="breadcrumb no-bg mb-1">
                        <li class="breadcrumb-item"><a>Finance</a></li>
                        <li class="breadcrumb-item"><a>Outgoing invoices</a></li>
                    </ol>

                    <div class="box box-block bg-white content_header" id="content_header" style="border-top: 0">
                        <div class="col-sm-6">
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Date</label>
                                <div class="col-sm-7">
                                    <a id="date" name="date" data-type="text">${invoice_date}</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Customer</label>
                                <div class="col-sm-7">
                                    <a id="customer" name="customer" data-type="text">${invoice_customer_name}</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Billing period</label>
                                <div class="col-sm-7">
                                    <a id="billingPeriodName" name="billingPeriodName" data-type="text">${invoice_billing_period_name}</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Period start</label>
                                <div class="col-sm-7">
                                    <a id="startDate" name="startDate" data-type="text">${invoice_billing_period_start}</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Period end</label>
                                <div class="col-sm-7">
                                    <a id="endDate" name="endDate" data-type="text">${invoice_billing_period_end}</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Pay due</label>
                                <div class="col-sm-7">
                                    <a id="payDate" name="payDate" data-type="text">${invoice_billing_period_pay}</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Invoice sum</label>
                                <div class="col-sm-7">
                                    <a id="sum" name="sum" data-type="text">${invoice_sum}</a>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-5 form-control-label">Sent to</label>
                                <div class="col-sm-7">
                                    <a id="sent_to" name="sent_to" data-type="text">${invoice_sent_to}</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div id="jsGrid-attachment-list"></div>
                            <div id="jsGrid-emailcontent-list" class="mt-1"></div>
                            <div class="form-group mt-1">
                                <input type="checkbox" id="confirmed" name="confirmed" class="td-checkbox"/>
                                <label for="confirmed">Confirmed</label>
                            </div>
                        </div>

                        <div class="clearfix"></div>
                    </div>

                    <div class="box box-block bg-white scroll-y traffic-list" id="traffic-list">
                        <div id="jsGrid-traffic-list"></div>
                    </div>
                </div>
            </div>
        </div>

    <%--Modal confirmed--%>

    <div id="modal_confirmed" class="modal fade small-modal" tabindex="-1"
         role="dialog" aria-labelledby="myModalConfirmed" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalConfirmed" style="text-align: center">Are you sure you
                        want to confirm?</h4>
                </div>
                <%--<div class="modal-body">
                    Modal content
                </div>--%>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="ok_confirmed">OK</button>
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
<script type="text/javascript" src="/vendor/jsgrid/dist/jsgrid.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>

<script type="text/javascript" src="/js/_main.js"></script>
<script src="/js/select2.min.js"></script>
<script>

    detailList = [];
    <c:forEach var="detail" items="${detailsList}">
    newRow = {};
    newRow['mcc'] = '${detail.mcc}';
    newRow['mnc'] = '${detail.mnc}';
    newRow['country'] = '${detail.country}';
    newRow['network'] = '${detail.network}';
    newRow['count'] = '${detail.count}';
    newRow['sum'] = '${detail.sum}';
    newRow['uid'] = '${detail.uid}';
    detailList.push(newRow);
    </c:forEach>

    attachmentList = [];
    <c:forEach var="attachment" items="${attachmentList}">
    newRow1 = {};
    newRow1['name'] = "<a href='/admin_full_get_email_attachment?id_email_attachment=${attachment.id}'>${attachment.name}</a>";
    newRow1['id'] = '${attachment.id}';
    attachmentList.push(newRow1);
    </c:forEach>

    emailContentList = [];
    <c:forEach var="emailContent" items="${emailContentList}">
    newRow2 = {};
    newRow2['subject'] = '${emailContent.subject}';
    newRow2['date'] = '${emailContent.date.toGMTString()}';
    newRow2['id'] = '${emailContent.id}';
    emailContentList.push(newRow2);
    </c:forEach>

    $('#confirmed').prop("checked", ${invoice_confirmed});

    if ($('#confirmed').is(':checked')){
        $('#confirmed').prop("disabled", true);
    }


</script>
<script type="text/javascript" src="/js/_outgoing_invoice_edit.js"></script>

</body>
</html>
