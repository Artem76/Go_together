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
    <title>${title} - Incoming TT</title>

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


    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_incoming_tt_edit.css">

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
        <a href="#" class="nav-link">
            <button type="button" class="btn btn-outline-primary" onclick="saveTT()">Save</button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="/admin_full_incoming_tt_list" class="nav-link">
            <button type="button" class="btn btn-outline-warning">Cancel</button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="#" class="nav-link">
            <button type="button" class="btn btn-outline-success" onclick="createOutgoingTT()">Create outgoing TT
            </button>
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
                        <h4>Incoming trouble ticket update</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Trouble tickets</a></li>
                            <li class="breadcrumb-item"><a>Incoming</a></li>
                        </ol>
                    </div>
                </div>

                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#tt" role="tab">TT</a>
                    </li>
                    <c:if test="${not empty emailContentList}">
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#email" role="tab">Email</a>
                        </li>
                    </c:if>
                    <c:if test="${not empty outgoingTTList}">
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#outgoingTT" role="tab">OutgoingTT</a>
                        </li>
                    </c:if>
                </ul>

                <div class="box box-block bg-white scroll-x content_tt" style="border-top: 0">
                    <div class="tab-content">

                        <div class="tab-pane active" id="tt" role="tabpanel">
                            <div class="form_incominTT">
                                <input type="hidden" name="id_tt" value="${id_tt}" id="id_tt">
                                <div class="form-group">
                                    <label for="status">Status</label><br>
                                    <select class="form-control" name="status" id="status">
                                        <c:if test="${not empty tt.status}">
                                            <option value="${tt.status}">
                                                <c:out value="${tt.status.toString()}"/>
                                            </option>
                                        </c:if>
                                        <c:forEach var="status" items="${statuses}">
                                            <c:if test="${tt.status ne status}">
                                                <option value="${status}">
                                                    <c:out value="${status.toString()}"/>
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="customer_account">Customer</label><br>
                                    <select class="form-control js-example-basic-single dd_menu" id="customer_account"
                                            name="customer_account" style="width: 100%">
                                        <<c:if test="${empty customer}">
                                        <option value="0">
                                            <c:out value="Not selected"/>
                                        </option>
                                    </c:if>
                                        <c:if test="${not empty customer}">
                                            <option value="${customer.id}">
                                                <c:out value="${customer.getCustomer().getName()} - ${customer.name}"/>
                                            </option>
                                        </c:if>
                                        <c:forEach var="currcustomer" items="${customers}">
                                            <c:if test="${customer.id ne currcustomer.id}">
                                                <option value="${currcustomer.id}">
                                                    <c:out value="${currcustomer.getCustomer().getName()} - ${currcustomer.name}"/>
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                </div>
                                <div class="form-group">
                                    <label for="subject">Subject</label>
                                    <input type="" class="form-control" id="subject" placeholder="Subject"
                                           value="${tt.subject}" name="subject">
                                </div>
                                <div class="form-group">
                                    <label for="solution">Solution</label>
                                    <input type="" class="form-control" id="solution" placeholder="Solution"
                                           value="${tt.solution}" name="solution">
                                </div>
                            </div>
                            <div class="scroll-x" name="messageLogForTTList">
                                <div id="jsGrid-incoming-tt-complain-list"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>

                        <div class="tab-pane" id="email" role="tabpanel">

                            <div class="box bg-white messenger">
                                <div class="row no-gutter">
                                    <div class="m-chat">
                                        <div class="m-header">
                                            <div class="media">
                                                <div class="media-body">
                                                    <h6 class="media-heading mb-0">${tt.subject}</h6>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="m-form">
                                            <div class="row">
                                                <div class="col-md-8 offset-md-2">
                                                    <%--<form class="mf-compose">--%>
                                                    <div class="flex-container">
                                                            <textarea
                                                                    class="form-control new_email_body" <%--type="text"--%>
                                                                    placeholder="Type a message..."></textarea>
                                                        <button class="btn btn-success btn-rounded new_email_btn"
                                                                type="button" data-toggle="modal"
                                                                data-target="#modal_send">
                                                            Send
                                                        </button>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>

                                        <div class="emails scroll-y" id="emails">
                                            <c:forEach var="emailContent" items="${emailContentList}">
                                                <c:if test="${emailContent.emailType eq 'Incoming'}">
                                                    <c:set var="position" value="left"/>
                                                </c:if>
                                                <c:if test="${emailContent.emailType ne 'Incoming'}">
                                                    <c:set var="position" value="right"/>
                                                </c:if>
                                                <div class="mc-item ${position} clearfix">
                                                    <div class="mc-content">
                                                        <c:if test="${emailContent.textTypeHtml eq 'true'}">
                                                            <div class="email_content">
                                                                    ${emailContent.body}
                                                            </div>
                                                            <a href="/admin_full_email_content_view?id_email_content=${emailContent.id}">
                                                                <div class="font-90 text-xs-right text-muted">Open full
                                                                    e-mail
                                                                    (from: ${emailContent.from} ${emailContent.date.toGMTString()})
                                                                </div>
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${emailContent.textTypeHtml ne 'true'}">
                                                            <div class="email_content">
                                                                <pre>
                                                                        ${emailContent.body}
                                                                </pre>
                                                            </div>
                                                            <a href="/admin_full_email_content_view?id_email_content=${emailContent.id}">
                                                                <div class="font-90 text-xs-right text-muted">Open full
                                                                    e-mail
                                                                    (from: ${emailContent.from} ${emailContent.date.toGMTString()})
                                                                </div>
                                                            </a>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="tab-pane" id="outgoingTT" role="tabpanel">
                            <div class="scroll-x" name="outgoingTT_list">
                                <div id="jsGrid-otgoing-tt-list"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>

                        </div>

                    <%--Modal new email--%>

                    <div id="modal_send" class="modal <%--animated flipInX--%> fade small-modal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalSave" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                    <h4 class="modal-title" id="myModalSave" style="text-align: center">Are you sure you
                                        want to send email?</h4>
                                </div>
                                <%--<div class="modal-body">
                                    Modal content
                                </div>--%>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" id="email_send">OK</button>
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
<script type="text/javascript" src="/vendor/jsgrid/dist/jsgrid.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>

<script>
    //======== Назначение параметров ================

    outgoingTTList = [];
    <c:forEach var="outgoingTT" items="${outgoingTTList}">
    newOutgoingTT = {};
    newOutgoingTT['id'] = ${outgoingTT.id};
    newOutgoingTT['subject'] = '${outgoingTT.subject}';
    newOutgoingTT['customer_account'] = '${outgoingTT.customer_account}';
    newOutgoingTT['date_opened'] = '${outgoingTT.date_opened}';
    newOutgoingTT['date_closed'] = '${outgoingTT.date_closed}';
    newOutgoingTT['status'] = '${outgoingTT.status}';
    outgoingTTList.push(newOutgoingTT);
    </c:forEach>

    issueList = [];
    <c:forEach var="issue" items="${issueList}">
    newRow = {};
    newRow['value'] = '${issue}';
    newRow['name'] = '${issue.toString()}';
    issueList.push(newRow);
    </c:forEach>

    document.id_tt = ${tt.id};
</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_incoming_tt_edit.js"></script>
<script src="/js/select2.min.js"></script>
<script type="text/javascript" src="/vendor/toastr/toastr.min.js"></script>

</body>
</html>
