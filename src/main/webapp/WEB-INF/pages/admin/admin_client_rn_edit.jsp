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
    <title>${title} - Client RN</title>

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
    <link href="/css/select2.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/vendor/toastr/toastr.min.css">
    <link rel="stylesheet" href="/vendor/summernote/summernote.css">


    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_excel.css">
    <link rel="stylesheet" href="/css/_client_rn_edit.css">

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
    <c:if test="${empty rn.emailContentList}">
        <li class="nav-item dropdown">
            <a href="#" class="nav-link">
                <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_save">Save
                </button>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a href="#" class="nav-link">
                <button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modal_save_send">
                    Save and send
                </button>
            </a>
        </li>
        <li class="nav-item dropdown">
            <a href="/admin_full_client_rn_list" class="nav-link">
                <button type="button" class="btn btn-outline-warning">Cancel</button>
            </a>
        </li>
    </c:if>

    <c:if test="${not empty rn.emailContentList}">
        <li class="nav-item dropdown">
            <a href="#" class="nav-link">
                <button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modal_send">
                    Send
                </button>
            </a>
        </li>
    </c:if>
    <c:if test="${empty rn.emailContentList}">
        <li class="nav-item dropdown">
            <label class="custom-file">
                <!-- Excel form -->
                <form enctype="multipart/form-data" method="POST" id="fileUploadForm">
                    <input type="file" id="file" class="custom-file-input" accept=".xlsx,.xls,.csv" name="file">
                    <span class="custom-file-control b-a-radius-0 mt-1"></span>
                </form>
            </label>
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
                <div class="row">
                    <div class="col-md-6">
                        <h4>Client rate notification</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Ratemode</a></li>
                            <li class="breadcrumb-item"><a>Client RN</a></li>
                        </ol>
                    </div>
                </div>
                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#client_rn" role="tab">Rates</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#email" role="tab">Email text</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#email_list" role="tab">Related emails</a>
                    </li>
                </ul>
                <div class="box box-block bg-white scroll-x" id="content_rn" style="border-top: 0">
                    <div class="tab-content">
                        <div class="tab-pane active" id="client_rn" role="tabpanel">
                            <div class="form_rn">
                                <input type="hidden" name="id_rn" value="${id_rn}" id="id_rn">
                                <div class="form-group">
                                    <label for="account_id">Customer / Account</label><br>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div name="account_id">
                                                <c:if test="${empty rn.emailContentList}">
                                                    <select id="account_id" class="js-example-basic-single form-control"
                                                            name="account_id"
                                                            style="width: 100%">
                                                        <c:if test="${id_rn eq 0}">
                                                            <option value="0">Select customer ...</option>
                                                        </c:if>
                                                        <c:forEach var="account" items="${accounts}">
                                                            <option value="${account.id}">${account.getCustomer().name}
                                                                - ${account.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </c:if>
                                                <c:if test="${not empty rn.emailContentList}">
                                                    <div class="form-control"> ${rn.smppClientAccount.getCustomer().name}
                                                        - ${rn.smppClientAccount.name}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <select id="country_id" class="js-example-basic-single form-control"
                                                    name="country_id"
                                                    style="width: 100%">
                                                    <option value="0">Select country ...</option>
                                                <c:forEach var="countryElement" items="${countries}">
                                                    <option value="${countryElement.mcc}">${countryElement.country}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-2">
                                            <button type="button" class="btn btn-primary w-min-sm mb-0-25 waves-effect waves-light" onclick="fillOpenedCodes('opened')" style="width: 100%">Fill with opened codes</button>
                                        </div>
                                        <div class="col-md-2">
                                            <button type="button" class="btn btn-primary w-min-sm mb-0-25 waves-effect waves-light" onclick="fillOpenedCodes('all')" style="width: 100%">Fill with all codes</button>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div class="scroll-x" name="rates">
                                <div id="jsGrid-rate-list"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="tab-pane" id="email" role="tabpanel">
                            <div class="form-group">
                                <label for="<c:if test='${empty rn.emailContentList}'>textEmail</c:if>">Text
                                    email</label>
                                <div class="form-control p-0" name="textEmail">
                                    <div id="<c:if test='${empty rn.emailContentList}'>textEmail</c:if>">
                                        ${rn.textEmail}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="email_list" role="tabpanel">
                            <div class="form-group">
                                <div class="scroll-x">
                                    <div id="jsGrid-email-list"></div>
                                </div>
                            </div>
                        </div>
                    </div>
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

    <%--Modal save and send--%>

    <div id="modal_save_send" class="modal fade small-modal" tabindex="-1"
         role="dialog" aria-labelledby="myModalSaveSend" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalSaveSend" style="text-align: center">Are you sure you
                        want to save Client RN and send email?</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="save_send">OK</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <%--Modal send--%>

    <div id="modal_send" class="modal fade small-modal" tabindex="-1"
         role="dialog" aria-labelledby="myModalSend" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalsend" style="text-align: center">Are you sure you
                        want to send email?</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="send_email">OK</button>
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

    <%--Modal error send--%>

    <div id="modal_error_send" class="modal animated tada small-modal" tabindex="-1" role="dialog"
         aria-labelledby="myModalErrorSend" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content" style="border: 3px solid red;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalErrorSend" style="text-align: center; color: red">
                        Error send!</h4>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <%--Modal excel data--%>
    <div id="modal_table" class="modal fade small-modal" tabindex="-1" role="dialog"
         aria-labelledby="myModalSave"
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
                    <h4 class="modal-title" id="myModalExcelError" style="text-align: center; color: red">File
                        error!</h4>
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
<script src="/js/select2.min.js"></script>
<script type="text/javascript" src="/vendor/toastr/toastr.min.js"></script>
<script type="text/javascript" src="/vendor/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>
<script type="text/javascript" src="/vendor/autoNumeric/autoNumeric-min.js"></script>
<script type="text/javascript" src="/vendor/summernote/summernote.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>

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

    window.email_list = [];
    <c:forEach var="emailContent" items="${rn.emailContentList}">
    newRow = {};
    newRow['email'] = '<a href="/admin_full_email_content_view?id_email_content=${emailContent.id}">Outgoing email to ${emailContent.to} (${fn:replace(fn:replace(emailContent.subject, "^.*(", ""), ")", "")})</a>';
    newRow['status'] = '<c:if test="${empty emailContent.dateSend}"><div style="color:red">Not sent</div></c:if><c:if test="${not empty emailContent.dateSend}"><div style="color:green">Sent</div></c:if>';
    window.email_list.push(newRow);
    </c:forEach>

    <c:if test="${id_rn ne 0}">
    $('#account_id').val("${rn.smppClientAccount.id}")
    </c:if>

    document.id_rn = ${id_rn};

    document.insAndEdit = <c:if test="${rn.emailContentList.size() ne 0}">false</c:if><c:if test="${rn.emailContentList.size() eq 0}">true</c:if>;
</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_client_rn_edit.js"></script>

<!-- Excel -->
<script type="text/javascript" src="/js/_excel.js"></script>

<!-- Modal -->
<script type="text/javascript" src="/js/ui-modal.js"></script>
<script type="text/javascript" src="/js/forms-masks.js"></script>

</body>
</html>
