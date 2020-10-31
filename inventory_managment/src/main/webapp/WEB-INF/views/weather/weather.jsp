<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page language="java" pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <!-- Meta, title, CSS, favicons, etc. -->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Inventory Management | </title>

  <!-- Bootstrap -->
  <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
  <!-- Font Awesome -->
  <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
  <!-- NProgress -->
  <link href="<c:url value="/resources/vendors/nprogress/nprogress.css"/>" rel="stylesheet">
  <!-- Animate.css -->
  <link href="<c:url value="/resources/vendors/animate.css/animate.min.css"/>" rel="stylesheet">

  <!-- Custom Theme Style -->
  <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
</head>

<body class="login">
<div>

  <div class="login_wrapper">
    <div class="animate form login_form">
      <section class="login_content">
        <form action="/weatherAPI" method="post">
          <div class="form-group">
            <label for="cityName">City name</label>
            <input type="text" class="form-control" id="cityName"  placeholder="City name">
          </div>
          <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <label for="nhietdo">Nhiet do</label>
        <input type="text" class="form-control" id="nhietdo" >
      </section>
    </div>
  </div>
</div>
</body>
</html>
