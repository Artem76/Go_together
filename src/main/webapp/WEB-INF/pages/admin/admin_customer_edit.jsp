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
    <title>${title} - Customer</title>

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

    <%--<form id="mainform" method="post" action="/admin_full_customer_edit_save">--%>
    <!-- Header -->
    <c:import url="/sections/header_begin.html"/>
    <li class="nav-item dropdown">
        <a href="#" aria-expanded="false" class="nav-link">
            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal_save">Save
            </button>
        </a>
    </li>
    <li class="nav-item dropdown">
        <a href="/admin_full_customer_list" data-toggle="" aria-expanded="false" class="nav-link">
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
                <%--<c:if test="${not empty error}">
                    <h4 class="text-danger">Data error</h4>
                </c:if>
                <c:if test="${not empty err_name}">
                    <h4 class="text-danger">Name error</h4>
                </c:if>--%>

                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#Profile" role="tab">Profile</a>
                    </li>

                    <c:if test="${id_customer ne 0}">
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#SMPP_Terimantion_accounts" role="tab">SMPP
                                Termination Accounts</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#SMPP_Origination_accounts" role="tab">SMPP
                                Origination Accounts</a>
                        </li>
                    </c:if>


                </ul>
                <div class="box box-block bg-white content_customer" style="border-top: 0">


                    <div class="tab-content">
                        <div class="tab-pane active" id="Profile" role="tabpanel">
                            <c:if test="${id_customer ne 0}">
                            <div class="col-sm-6">
                                </c:if>

                                <input type="hidden" name="id_customer" value="${id_customer}">
                                <h5>System</h5>
                                <div class="form-group">
                                    <label for="name_customer">Name</label>
                                    <input type="text" value="${customer.name}" class="form-control"
                                           id="name_customer" placeholder="Name" name="name_customer">
                                </div>

                                <div class="form-group">
                                    <label for="had_menu_status">Status</label><br>
                                    <select class="js-example-basic-single dd_menu" name="status_customer"
                                            id="had_menu_status" style="width: 100%">
                                        <c:if test="${empty customer.status}">
                                            <option value="0">No status</option>
                                        </c:if>
                                        <c:if test="${not empty customer.status}">
                                            <option value="${customer.status.id}">${customer.status}</option>
                                        </c:if>
                                        <c:forEach var="status" items="${statuses}">
                                            <c:if test="${customer.status.id ne status.id}">
                                                <option value="${status.id}">${status}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                    <div class="clearfix"></div>
                                </div>

                                <div class="form-group">
                                    <label for="website">Website</label>
                                    <input type="text" value="${customer.website}" class="form-control" id="website"
                                           placeholder="Website" name="website_customer">
                                </div>

                                <div class="form-group">
                                    <label for="had_menu_manager">Manager</label><br>
                                    <select class="js-example-basic-single dd_menu" name="manager_customer"
                                            id="had_menu_manager" style="width: 100%">
                                        <c:if test="${empty customer.manager}">
                                            <option value="0">No manager</option>
                                        </c:if>
                                        <c:if test="${not empty customer.manager}">
                                            <option value="${customer.manager.id}">${customer.manager}</option>
                                        </c:if>
                                        <c:forEach var="manager" items="${managers}">
                                            <c:if test="${customer.manager.id ne manager.id}">
                                                <option value="${manager.id}">${manager}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                    <div class="clearfix"></div>
                                </div>
                                <div class="clearfix"></div>

                                <h5 class="mt-2">Legal</h5>

                                <div class="form-group">
                                    <label for="had_menu_signed_on">Signed on</label><br>
                                    <select class="js-example-basic-single dd_menu" name="signed_on"
                                            id="had_menu_signed_on" style="width: 100%">
                                        <c:if test="${empty customer.companyId}">
                                            <option value="0">Select company ...</option>
                                        </c:if>
                                        <c:if test="${not empty customer.companyId}">
                                            <option value="${customer.companyId}">${signed_on}</option>
                                        </c:if>
                                        <c:forEach var="company" items="${companies}">
                                            <c:if test="${company.id ne customer.companyId}">
                                                <option value="${company.id}">${company.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div class="form-group">
                                    <label for="fullname">Full name</label>
                                    <input type="text" value="${customer.fullName}" class="form-control"
                                           id="fullname"
                                           aria-describedby="emailHelp" placeholder="Full name"
                                           name="fullName_customer">
                                </div>

                                <div class="form-group">
                                    <label for="address">Address</label>
                                    <input type="text" value="${customer.address}" class="form-control" id="address"
                                           aria-describedby="emailHelp" placeholder="Address"
                                           name="address_customer">
                                </div>

                                <div class="form-group">
                                    <label for="signer-name">Signer name</label>
                                    <input type="text" value="${customer.signerName}" class="form-control"
                                           id="signer-name"
                                           aria-describedby="emailHelp" placeholder="Signer name"
                                           name="signerName_customer">
                                </div>

                                <div class="form-group">
                                    <label for="signer-title">Signer title</label>
                                    <input type="text" value="${customer.signerTitle}" class="form-control"
                                           id="signer-title"
                                           aria-describedby="emailHelp" placeholder="Signer title"
                                           name="signerTitle_customer">
                                </div>

                                <div class="form-group">
                                    <label for="phone">Phone</label>
                                    <input type="text" value="${customer.phone}" class="form-control" id="phone"
                                           aria-describedby="emailHelp" placeholder="Phone" name="phone_customer">
                                </div>

                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="text" value="${customer.email}" class="form-control" id="email"
                                           aria-describedby="emailHelp" placeholder="Email" name="email_customer">
                                </div>

                                <div class="form-group">
                                    <label for="registration-number">Registration number</label>
                                    <input type="text" value="${customer.registrationNumber}" class="form-control"
                                           id="registration-number"
                                           aria-describedby="emailHelp" placeholder="Registration number"
                                           name="registrationNumber_customer">
                                </div>

                                <div class="form-group">
                                    <label for="vat">VAT</label>
                                    <input type="text" value="${customer.vat}" class="form-control" id="vat"
                                           aria-describedby="emailHelp" placeholder="VAT" name="vat_customer">
                                </div>
                                <div class="clearfix"></div>

                                <h5 class="mt-2">Messaging</h5>
                                <div class="form-group">
                                    <label for="smsBillingTerms_customer">Billing terms</label><br>
                                    <select class="js-example-basic-single dd_menu" name="smsBillingTerms_customer"
                                            id="smsBillingTerms_customer" style="width: 100%">
                                        <c:if test="${empty customer.smsBillingTerms}">
                                            <option value="0">No billing terms</option>
                                        </c:if>
                                        <c:if test="${not empty customer.smsBillingTerms}">
                                            <option value="${customer.smsBillingTerms.id}">${customer.smsBillingTerms}</option>
                                        </c:if>
                                        <c:forEach var="smsBillingTerms" items="${smsBillingTermsList}">
                                            <c:if test="${customer.smsBillingTerms.id ne smsBillingTerms.id}">
                                                <option value="${smsBillingTerms.id}">${smsBillingTerms}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                    <div class="clearfix"></div>
                                </div>

                                <div class="form-group">
                                    <label for="smsContractType">Status</label><br>
                                    <select class="js-example-basic-single dd_menu" name="smsContractType"
                                            id="smsContractType" style="width: 100%">
                                        <c:if test="${not empty customer.contractType}">
                                            <option value="${customer.contractType}">${customer.contractType.toString()}</option>
                                        </c:if>
                                        <c:forEach var="type" items="${contractTypes}">
                                            <c:if test="${customer.contractType ne type}">
                                                <option value="${type}">${type.toString()}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                    <div class="clearfix"></div>
                                </div>

                                <div class="form-group">
                                    <label for="smsCreditLimit">Credit limit</label>
                                    <input type="text" value="${customer.creditLimit}" class="form-control"
                                           id="smsCreditLimit" placeholder="Credit limit"
                                           name="smsCreditLimit">
                                </div>

                                <div class="form-group">
                                    <label for="smsMinimalPayment">Minimal payment</label>
                                    <input type="text" value="${customer.minimalPayment}" class="form-control"
                                           id="smsMinimalPayment" placeholder="Minimal payment"
                                           name="smsMinimalPayment">
                                </div>

                                <div class="form-group">
                                    <label for="sms-support-email">Support email</label>
                                    <input type="text" value="${customer.smsSupportEmail}" class="form-control"
                                           id="sms-support-email" placeholder="Support email"
                                           name="smsSupportEmail_customer">
                                </div>

                                <div class="form-group">
                                    <label for="sms-ratemode-email">Ratemode email</label>
                                    <input type="text" value="${customer.smsRateModeEmail}" class="form-control"
                                           id="sms-ratemode-email" placeholder="Ratemode email"
                                           name="smsRateModeEmail_customer">
                                </div>

                                <div class="form-group">
                                    <label for="sms-invoice-email">Invoice email</label>
                                    <input type="text" value="${customer.smsInvoiceEmail}" class="form-control"
                                           id="sms-invoice-email" placeholder="Invoice email"
                                           name="smsInvoiceEmail_customer">
                                </div>

                                <div class="form-group">
                                    <label for="sms-dispute-email">Dispute email</label>
                                    <input type="text" value="${customer.smsDisputeEmail}" class="form-control"
                                           id="sms-dispute-email" placeholder="Dispute email"
                                           name="smsDisputeEmail_customer">
                                </div>

                                <div class="clearfix"></div>

                                <h5 class="mt-2">Voice</h5>
                                <div class="form-group">
                                    <label for="had_menu_voice_billing_terms">Billing terms</label><br>
                                    <select class="js-example-basic-single dd_menu"
                                            name="voiceBillingTerms_customer"
                                            id="had_menu_voice_billing_terms" style="width: 100%">
                                        <c:if test="${empty customer.voiceBillingTerms}">
                                            <option value="0">No billing terms</option>
                                        </c:if>
                                        <c:if test="${not empty customer.voiceBillingTerms}">
                                            <option value="${customer.voiceBillingTerms.id}">${customer.voiceBillingTerms}</option>
                                        </c:if>
                                        <c:forEach var="voiceBillingTerms" items="${voiceBillingTermsList}">
                                            <c:if test="${customer.voiceBillingTerms.id ne voiceBillingTerms.id}">
                                                <option value="${voiceBillingTerms.id}">${voiceBillingTerms}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                    <div class="clearfix"></div>
                                </div>

                                <div class="form-group">
                                    <label for="voice-support-email">Support email</label>
                                    <input type="text" value="${customer.voiceSupportEmail}" class="form-control"
                                           id="voice-support-email" placeholder="Support email"
                                           name="voiceSupportEmail_customer">
                                </div>

                                <div class="form-group">
                                    <label for="voice-ratemode-email">Ratemode email</label>
                                    <input type="text" value="${customer.voiceRateModeEmail}" class="form-control"
                                           id="voice-ratemode-email" placeholder="Ratemode email"
                                           name="voiceRateModeEmail_customer">
                                </div>

                                <div class="form-group">
                                    <label for="voice-invoice-email">Invoice email</label>
                                    <input type="text" value="${customer.voiceInvoiceEmail}" class="form-control"
                                           id="voice-invoice-email" placeholder="Invoice email"
                                           name="voiceInvoiceEmail_customer">
                                </div>

                                <div class="form-group">
                                    <label for="voice-dispute-email">Dispute email</label>
                                    <input type="text" value="${customer.voiceDisputeEmail}" class="form-control"
                                           id="voice-dispute-email" placeholder="Dispute email"
                                           name="voiceDisputeEmail_customer">
                                </div>

                                <h5 class="mt-2">Other</h5>
                                <div class="form-group">
                                    <label for="dateFormat">Date fromat</label>
                                    <input type="text" value="${customer.dateFormat}" class="form-control"
                                           id="dateFormat" placeholder="Date format"
                                           name="dateFormat">
                                    <div class="clearfix"></div>
                                </div>
                                <div class="form-group">
                                    <label for="dateFormat">Fake name</label>
                                    <input type="text" value="${customer.fakeName}" class="form-control"
                                           id="fakeName" placeholder="Fake name"
                                           name="fakeName">
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <c:if test="${id_customer ne 0}">

                            <div class="col-sm-6">
                                <label class="custom-file">
                                    <!-- Excel form -->
                                    <form enctype="multipart/form-data" method="POST" id="fileUploadForm">
                                        <input type="file" id="file" class="custom-file-input" accept="" name="file">
                                        <span class="custom-file-control b-a-radius-0"></span>
                                    </form>
                                </label>
                                <div id="jsGrid-attachment-list"></div>
                            </div>

                        </div>

                        </c:if>

                        <div class="tab-pane" id="SMPP_Terimantion_accounts" role="tabpanel">
                            <a href="/admin_full_smpp_vendor_account_edit?id_vendor_account=0&id_customer=${customer.id}&error=&err_name=&err_ips=&return_in_customer=yes"
                               class="nav-link mb-1">
                                <button type="button" class="btn btn-outline-primary">Add new Account</button>
                            </a>

                            <div class="scroll-x">
                                <div id="jsGrid-smpp-vendor-accounts"></div>
                            </div>

                        </div>

                        <div class="tab-pane" id="SMPP_Origination_accounts" role="tabpanel">

                            <a href="/admin_full_smpp_client_account_edit?id_client_account=0&id_customer=${customer.id}&error=&err_name=&err_ips=&err_si=&return_in_customer=yes"
                               class="nav-link mb-1">
                                <button type="button" class="btn btn-outline-primary">Add new Account</button>
                            </a>

                            <div class="scroll-x">
                                <div id="jsGrid-smpp-client-accounts"></div>
                            </div>
                        </div>

                        <div class="tab-pane" id="SIP_Terimantion_accounts" role="tabpanel">
                            <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee
                                squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes
                                anderson artisan four loko farm-to-table craft beer twee. Qui photo booth
                                letterpress, commodo enim craft beer mlkshk aliquip jean shorts ullamco ad vinyl
                                cillum PBR. Homo nostrud organic, assumenda labore aesthetic magna delectus mollit.
                                Keytar helvetica VHS salvia yr, vero magna velit sapiente labore stumptown. Vegan
                                fanny pack odio cillum wes anderson 8-bit, sustainable jean shorts beard ut DIY
                                ethical culpa terry richardson biodiesel. Art party scenester stumptown, tumblr
                                butcher vero sint qui sapiente accusamus tattooed echo park.</p>
                        </div>

                        <div class="tab-pane" id="SIP_Origination_accounts" role="tabpanel">
                            <p>Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee
                                squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes
                                anderson artisan four loko farm-to-table craft beer twee. Qui photo booth
                                letterpress, commodo enim craft beer mlkshk aliquip jean shorts ullamco ad vinyl
                                cillum PBR. Homo nostrud organic, assumenda labore aesthetic magna delectus mollit.
                                Keytar helvetica VHS salvia yr, vero magna velit sapiente labore stumptown. Vegan
                                fanny pack odio cillum wes anderson 8-bit, sustainable jean shorts beard ut DIY
                                ethical culpa terry richardson biodiesel. Art party scenester stumptown, tumblr
                                butcher vero sint qui sapiente accusamus tattooed echo park.</p>
                        </div>

                    </div>


                    <div class="clearfix"></div>
                </div>
                <%--<div style="height: 300px"></div>--%>


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

                    <%--Modal delete--%>

                    <div id="modal_delete" class="modal fade small-modal" tabindex="-1"
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
                                    <button type="button" class="btn btn-primary" id="delete_go">OK</button>
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
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

    //==================Установка начальных значений smppVendorAccount==========

    vendorList = [];
    <c:forEach var="smppVendorAccount" items="${customer.smppVendorAccountList}">
    newVendor = {};
    newVendor['id'] = ${smppVendorAccount.id};
    newVendor['name'] = '${smppVendorAccount.name}';
    newVendor['systemId'] = '<c:forEach var="smppVendorIps" items="${smppVendorAccount.smppVendorIpsList}"
                                            varStatus="vs">${smppVendorIps.systemId}<c:if
            test="${not vs.last}">, </c:if></c:forEach>';
    vendorList.push(newVendor);
    </c:forEach>

    //==================Установка начальных значений smppClientAccount==========

    var clientList = [];
    <c:forEach var="smppClientAccount" items="${customer.smppClientAccountList}">
    newClient = {};
    newClient['id'] = ${smppClientAccount.id};
    newClient['name'] = '${smppClientAccount.name}';
    newClient['systemId'] = '<c:forEach var="smppClientSystemId" items="${smppClientAccount.smppClientSystemIdList}"
                                            varStatus="vs">${smppClientSystemId.systemId}<c:if
            test="${not vs.last}">, </c:if></c:forEach>';
    clientList.push(newClient);
    </c:forEach>


    <c:if test="${id_customer ne 0}">

    //==================Установка начальных значений Attachment==========

    attachmentList = [];
    <c:forEach var="attachment" items="${customer.emailAttachmentList}">
    newRow1 = {};
    newRow1['name'] = "<a href='/admin_full_get_email_attachment?id_email_attachment=${attachment.id}'>${attachment.fileName}</a>";
    newRow1['btn_del'] = '<button type="button" class="btn btn-danger btn-sm mb-0-25 waves-effect waves-light btn_del" value="${attachment.id}"><i class="ti-trash"></i> Delete</button>';
    newRow1['id'] = '${attachment.id}';
    attachmentList.push(newRow1);
    </c:forEach>

    </c:if>

    document.id_customer = ${id_customer};
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
