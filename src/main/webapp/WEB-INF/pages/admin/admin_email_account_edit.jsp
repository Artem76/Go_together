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
    <title>${title} - Email Account</title>

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
    <link rel="stylesheet" href="/vendor/summernote/summernote.css">

    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- My CSS -->
    <link rel="stylesheet" href="/css/_main.css">
    <link rel="stylesheet" href="/css/_email_account_edit.css">

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
        <a href="/admin_full_email_account_list" class="nav-link">
            <button type="button" class="btn btn-outline-warning">Cancel</button>
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

                <div class="box box-block bg-white content_email_account_edit" id="content_email_account_edit">

                    <div id="form_content">
                        <input type="hidden" name="id_email_account" value="${id_email_account}">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="text" value="${email_account.email}" class="form-control" id="email"
                                   placeholder="Email..." name="email">
                        </div>
                        <div class="form-group">
                            <label for="invisible">Invisible</label>
                            <select class="form-control" name="invisible" id="invisible">
                                <option value="true">True</option>
                                <option value="false">False</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="userName">User name</label>
                            <input type="text" value="${email_account.userName}" class="form-control"
                                   id="userName" placeholder="User name..." name="userName">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="text" value="${email_account.password}" class="form-control"
                                   id="password" placeholder="Password..." name="password">
                        </div>
                        <div class="form-group">
                            <label for="smtpServer">SMTP server</label>
                            <input type="text" value="${email_account.smtpServer}" class="form-control"
                                   id="smtpServer" placeholder="SMTP server..." name="smtpServer">
                        </div>
                        <div class="form-group">
                            <label for="smtpPort">SMTP port</label>
                            <input type="number" value="${email_account.smtpPort}" class="form-control"
                                   id="smtpPort" placeholder="SMTP port..." name="smtpPort">
                        </div>
                        <div class="form-group">
                            <label for="smtpSSL">SMTP SSL</label>
                            <select class="form-control" name="smtpSSL" id="smtpSSL">
                                <option value="true">True</option>
                                <option value="false">False</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="imapServer">IMAP server</label>
                            <input type="text" value="${email_account.imapServer}" class="form-control"
                                   id="imapServer" placeholder="IMAP server..." name="imapServer">
                        </div>
                        <div class="form-group">
                            <label for="imapPort">IMAP port</label>
                            <input type="number" value="${email_account.imapPort}" class="form-control"
                                   id="imapPort" placeholder="IMAP port..." name="imapPort">
                        </div>
                        <div class="form-group">
                            <label for="imapSSL">IMAP SSL</label>
                            <select class="form-control" name="imapSSL" id="imapSSL">
                                <option value="true">True</option>
                                <option value="false">False</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="emailRole">Email role</label>
                            <select class="form-control" name="emailRole" id="emailRole">
                                <option value="">No role</option>
                                <c:forEach var="emailRole" items="${email_role_list}">
                                    <option value="${emailRole}">${emailRole.toString()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="outgoingFont">Outgoing font</label>
                            <select class="form-control" name="outgoingFont" id="outgoingFont">
                                <c:forEach var="font" items="${font_family}">
                                    <option value="${font}"
                                            style="font-family: ${font.toString()}">${font.toString()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="outgoingFontSize">Outgoing font size</label>
                            <input type="number" value="${email_account.outgoingFontSize}" class="form-control"
                                   id="outgoingFontSize" placeholder="Font size..." name="outgoingFontSize">
                        </div>
                        <div class="form-group for_noc">
                            <label for="autoAnswer">Auto answer</label>
                            <select class="form-control" name="autoAnswer" id="autoAnswer">
                                <option value="true">On</option>
                                <option value="false">Off</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group for_noc">
                        <label for="textAutoAnswer">Text auto answer</label>
                        <div class="form-control p-0" name="textAutoAnswer">
                            <div id="textAutoAnswer">
                                ${email_account.textAutoAnswer}
                            </div>
                        </div>
                    </div>
                    <div class="form-group for_noc">
                        <label for="textAutoEmailOutgoingTT">Text auto email OutgoingTT (insertion required - [MessagesBlock])</label>
                        <div class="form-control p-0" name="textAutoEmailOutgoingTT">
                            <div id="textAutoEmailOutgoingTT">
                                ${email_account.textAutoEmailOutgoingTT}
                            </div>
                        </div>
                    </div>
                    <div class="form-group for_invoice">
                        <label for="textEmailInvoice">Text email invoice (insertion required - [NameCompany], [PeriodStart], [PeriodEnd])</label>
                        <div class="form-control p-0" name="textEmailInvoice">
                            <div id="textEmailInvoice">
                                ${email_account.textEmailInvoice}
                            </div>
                        </div>
                    </div>
                    <div class="form-group for_invoice">
                        <label for="textHtmlForPdfInvoice">Text HTML for PDF file in invoice (insertion required - [Address], [Manager], [Date], [PeriodStart], [PeriodEnd], [InvoiceName], [PayDue], [NumberOfSMS], [Sum])</label>
                        <div class="form-control p-0" name="textHtmlForPdfInvoice">
                            <div id="textHtmlForPdfInvoice">
                                ${email_account.textHtmlForPdfInvoice}
                            </div>
                        </div>
                    </div>
                    <div class="form-group for_ratemode">
                        <label for="textEmailClientRn">Text email client RN (insertion required - [SystemId])</label>
                        <div class="form-control p-0" name="textEmailClientRn">
                            <div id="textEmailClientRn">
                                ${email_account.textEmailClientRn}
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="signature">Signature</label>
                        <div class="form-control p-0" name="signature">
                            <div id="signature">
                                ${email_account.signature}
                            </div>
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
<script type="text/javascript" src="/vendor/summernote/summernote.min.js"></script>

<!-- Neptune JS -->
<script type="text/javascript" src="/js/app.js"></script>
<script type="text/javascript" src="/js/demo.js"></script>
<script type="text/javascript" src="/js/tables-datatable.js"></script>


<script>

    //==================Установка начальных значений Email account==========

    $('#invisible').val("${email_account.invisible}");

    $('#smtpSSL').val("${email_account.smtpSSL}");

    $('#imapSSL').val("${email_account.imapSSL}");

    $('#emailRole').val("${email_account.emailRole}");

    $('#autoAnswer').val("${email_account.autoAnswer}");

    $('#outgoingFont').val("${email_account.outgoingFont}");

</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_email_account_edit.js"></script>


</body>
</html>
