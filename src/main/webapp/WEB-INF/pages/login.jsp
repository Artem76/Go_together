<?xml version="1.0" encoding="utf-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Title -->
    <title>Volumes</title>

    <!-- Vendor CSS -->
    <link rel="stylesheet" href="/vendor/bootstrap4/css/bootstrap.min.css">
    <link rel="stylesheet" href="/vendor/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="/vendor/font-awesome/css/font-awesome.min.css">

    <!-- Neptune CSS -->
    <link rel="stylesheet" href="/css/core.css">

    <!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="img-cover" style="background-image: url(/images/photos-1/2.jpg);">

<div class="container-fluid">
    <div class="sign-form">
        <div class="row">
            <div class="col-md-4 offset-md-4 px-3">
                <div class="box b-a-0">
                    <div class="p-2 text-xs-center">
                        <h5>Welcome</h5>
                    </div>
                    <c:url value="/j_spring_security_check" var="loginUrl"/>
                    <form class="form-material mb-1" action="${loginUrl}" method="post">
                        <div class="form-group">
                            <input type="text" class="form-control" id="exampleInputText" placeholder="Login" name="j_login">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" id="exampleInputPassword" placeholder="Password" name="j_password">
                        </div>
                        <div class="px-2 form-group mb-0">
                            <button type="submit" class="btn btn-purple btn-block text-uppercase">Sign in</button>
                        </div>
                    </form>

                    <div class="p-2 text-xs-center text-muted">
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

<!-- Блокировка сохранения паролей -->

<script>
    window.onload = function () {
        setTimeout(function () {
            //document.getElementById("exampleInputPassword").type = "password";
            //заменить type="password" на type="text"
        }, 10);
    }
</script>

</body>
</html>
