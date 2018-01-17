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
    <title>${title} - Vendor RN</title>

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
    <link href="/css/select2.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/vendor/toastr/toastr.min.css">

    <%--<link rel="stylesheet" href="/vendor/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">--%>
    <%--<link rel="stylesheet" href="/vendor/clockpicker/dist/bootstrap-clockpicker.min.css">--%>
    <%--<link rel="stylesheet" href="/vendor/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">--%>
    <%--<link rel="stylesheet" href="/vendor/bootstrap-daterangepicker/daterangepicker.css">--%>
<%--<link rel="stylesheet" type="text/css" href="/css/bootstrap-datetimepicker.min.css"/>--%>



    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_excel.css">
    <link rel="stylesheet" href="/css/_vendor_rn_edit.css">
    <%--<link rel="stylesheet" type="text/css" href="/css/jquery.datetimepicker.css"/>--%>

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
        <a href="#" aria-expanded="false" class="nav-link">
            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_save">Save
            </button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="/admin_full_vendor_rn_list" data-toggle="" aria-expanded="false" class="nav-link">
            <button type="button" class="btn btn-outline-warning">Cancel</button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <label class="custom-file">
            <!-- Excel form -->
            <form enctype="multipart/form-data" method="POST" id="fileUploadForm">
                <input type="file" id="file" class="custom-file-input" accept=".xlsx,.xls,.csv" name="file">
                <span class="custom-file-control b-a-radius-0 mt-1"></span>
            </form>
        </label>
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
                        <h4>Vendor rate update</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Ratemode</a></li>
                            <li class="breadcrumb-item"><a>Vendor RN</a></li>
                        </ol>
                    </div>
                </div>
                <div class="box box-block bg-white scroll-x" id="content_rn">
                    <div class="form_rn">
                        <input type="hidden" name="id_rn" value="${id_rn}" id="id_rn">
                        <input type="hidden" name="id_email_content" value="${id_email_content}" id="id_email_content">
                        <div class="form-group">
                            <label for="email">Related email</label><br>
                            <div class="form-control" id="email">
                                <c:if test="${empty rn.emailContent}">No email</c:if>
                                <c:if test="${not empty rn.emailContent}"><a href='/admin_full_email_content_view?id_email_content=${rn.emailContent.id}'>Incoming email from ${rn.emailContent.from}</a></c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="accountIps_id">Customer - Account - System id</label><br>
                            <div name="accountIps_id">
                                <select id="accountIps_id" class="js-example-basic-single form-control" name="accountIps_id"
                                        style="width: 100%">
                                    <c:if test="${id_rn eq 0}">
                                        <option value="0">Select customer ...</option>
                                    </c:if>
                                    <c:forEach var="accountIp" items="${accountIps}">
                                        <option value="${accountIp.id}">${accountIp.smppVendorAccount.customer.name}
                                            - ${accountIp.smppVendorAccount.name} - ${accountIp.systemId}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>
                    </div>
                    <div class="scroll-x" name="rates">
                        <div id="jsGrid-rate-list"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
    <%--</form>--%>

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

    <%--Modal excel data--%>
    <div id="modal_table" class="modal fade small-modal" tabindex="-1" role="dialog" aria-labelledby="myModalSave"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-body scroll-x">
                    <table id="excel"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="get_data_excel">OK</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <%--Modal excel error--%>
    <div id="modal_excel_error" class="modal animated tada small-modal" tabindex="-1" role="dialog"
         aria-labelledby="myModalExcelError" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content" style="border: 3px solid red;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalExcelError" style="text-align: center; color: red">File error!</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- Vendor JS -->
<script type="text/javascript" src="/vendor/jquery/jquery-1.12.3.min.js"></script>
<%--<scri src="/js/jquery-3.2.1.min.js"></scri>--%>

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
<script src="/js/select2.min.js"></script>
<script type="text/javascript" src="/vendor/toastr/toastr.min.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>
<script type="text/javascript" src="/vendor/autoNumeric/autoNumeric-min.js"></script>



<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>
<script type="text/javascript" src="/js/tables-datatable.js"></script>

<script>
    //======== Назначение параметров ================

    window.rates = [];
    <c:forEach var="rate" items="${rates}">
    newRow = {};
    newRow['country'] = '${rate.country}';
    newRow['mcc'] = '${rate.mcc}';
    newRow['network'] = '${rate.network}';
    newRow['mnc'] = '${rate.mnc}';
    newRow['rate'] = '${rate.rate}';
    newRow['date'] = '${rate.date}';
    window.rates.push(newRow);
    </c:forEach>

    <c:if test="${id_rn ne 0}">
    $('#accountIps_id').val("${rn.smppVendorIps.id}")
    </c:if>

    document.id_rn = ${id_rn};
    document.id_email_content = ${id_email_content};
    document.id_email_attachment = ${id_email_attachment};

</script>

<!-- Excel -->
<script type="text/javascript" src="/js/_excel.js"></script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_vendor_rn_edit.js"></script>

<!-- Modal -->
<script type="text/javascript" src="/js/ui-modal.js"></script>
<script type="text/javascript" src="/js/forms-masks.js"></script>


</body>
</html>
