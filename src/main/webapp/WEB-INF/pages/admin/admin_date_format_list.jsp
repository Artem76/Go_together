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
    <title>${title} - Date Formats</title>

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
            <button class="btn btn-primary" data-toggle="modal" data-target=".default-modal">Help</button>
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
                        <h4>Date formats</h4>
                        <ol class="breadcrumb no-bg mb-1">
                            <li class="breadcrumb-item"><a>Settings</a></li>
                            <li class="breadcrumb-item"><a>Date formats</a></li>
                        </ol>
                    </div>
                </div>
                <div class="box box-block bg-white date_format_list">

                    <div class="scroll-x">
                        <div id="jsGrid-date-format-list"></div>
                    </div>

                    <div class="clearfix"></div>
                </div>
            </div>
        </div>

        <%--Modal help--%>
        <div class="modal fade default-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">Creating a Date Format</h4>
                    </div>
                    <div class="modal-body">
                        <h4>Pattern Syntax</h4>
                        <table>
                            <tr><td><b>G</b></td><td>	- Era designator (before christ, after christ)</td></tr>
                            <tr><td><b>y</b></td><td>	- Year (e.g. 12 or 2012). Use either yy or yyyy.</td></tr>
                            <tr><td><b>M</b></td><td>	- Month in year. Number of M's determine length of format (e.g. MM, MMM or MMMMM)</td></tr>
                            <tr><td><b>d</b></td><td>	- Day in month. Number of d's determine length of format (e.g. d or dd)</td></tr>
                            <tr><td><b>h</b></td><td>	- Hour of day, 1-12 (AM / PM) (normally hh)</td></tr>
                            <tr><td><b>H</b></td><td>	- Hour of day, 0-23 (normally HH)</td></tr>
                            <tr><td><b>m</b></td><td>	- Minute in hour, 0-59 (normally mm)</td></tr>
                            <tr><td><b>s</b></td><td>	- Second in minute, 0-59 (normally ss)</td></tr>
                            <tr><td><b>S</b></td><td>	- Millisecond in second, 0-999 (normally SSS)</td></tr>
                            <tr><td><b>E</b></td><td>	- Day in week (e.g Monday, Tuesday etc.)</td></tr>
                            <tr><td><b>u</b></td><td>	- Day number of week (1 = Monday, ..., 7 = Sunday)</td></tr>
                            <tr><td><b>D</b></td><td>	- Day in year (1-366)</td></tr>
                            <tr><td><b>F</b></td><td>	- Day of week in month (e.g. 1st Thursday of December)</td></tr>
                            <tr><td><b>w</b></td><td>	- Week in year (1-53)</td></tr>
                            <tr><td><b>W</b></td><td>	- Week in month (0-5)</td></tr>
                            <tr><td><b>a</b></td><td>	- AM / PM marker</td></tr>
                            <tr><td><b>k</b></td><td>	- Hour in day (1-24, unlike HH's 0-23)</td></tr>
                            <tr><td><b>K</b></td><td>	- Hour in day, AM / PM (0-11)</td></tr>
                            <tr><td><b>z</b></td><td>	- Time Zone</td></tr>
                            <tr><td><b>'</b></td><td>	- Escape for text delimiter</td></tr>
                            <tr><td><b>'</b></td><td>	- Single quote</td></tr>
                        </table>
                        <hr>
                        <h4>Pattern Examples</h4>
                        <table>
                            <tr><td><b>dd-MM-yy</b></td><td> - 31-01-12</td></tr>
                            <tr><td><b>dd-MM-yyyy</b></td><td> - 31-01-2012</td></tr>
                            <tr><td><b>MM-dd-yyyy</b></td><td> - 01-31-2012</td></tr>
                            <tr><td><b>yyyy-MM-dd</b></td><td> - 2012-01-31</td></tr>
                            <tr><td><b>yyyy-MM-dd HH:mm:ss</b></td><td> - 2012-01-31 23:59:59</td></tr>
                            <tr><td><b>yyyy-MM-dd HH:mm:ss.SSS</b></td><td> - 2012-01-31 23:59:59.999</td></tr>
                            <tr><td><b>yyyy-MM-dd HH:mm:ss.SSSZ</b></td><td> - 2012-01-31 23:59:59.999+0100</td></tr>
                            <tr><td><b>EEEEE MMMMM yyyy HH:mm:ss.SSSZ</b></td><td> - Saturday November 2012 10:45:42.720+0100</td></tr>
                            <tr><td><b>yyyy-MM-dd'T'HH:mm:ss.SSSZ</b></td><td> - 2001-07-04T12:08:56.235-0700</td></tr>
                            <tr><td><b>yyyy-MM-dd'T'HH:mm:ss.SSSXXX</b></td><td> - 2001-07-04T12:08:56.235-07:00</td></tr>
                            <tr><td><b>YYYY-'W'ww-u</b></td><td> - 2001-W27-3</td></tr>
                            <tr><td><b>yyyyy.MMMMM.dd GGG hh:mm aaa</b></td><td> - 02001.July.04 AD 12:08 PM</td></tr>
                            <tr><td><b>EEE, d MMM yyyy HH:mm:ss Z</b></td><td> - Wed, 4 Jul 2001 12:08:56 -0700</td></tr>
                            <tr><td><b>K:mm a, z</b> - 0:08 PM, PDT</td></tr>
                        </table>
                        </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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

    //==================Установка начальных значений Email Account List==========

    dateFormatList = [];
    <c:forEach var="dateFormat" items="${dateFormatList}">
    newFormat = {};
    newFormat['id'] = ${dateFormat.id};
    newFormat['format'] = '${dateFormat.format}';
    dateFormatList.push(newFormat);
    </c:forEach>

</script>

<script type="text/javascript" src="/js/_main.js"></script>
<script type="text/javascript" src="/js/_date_format_list.js"></script>


</body>
</html>
