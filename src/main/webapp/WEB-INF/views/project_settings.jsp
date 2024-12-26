<%--
  Created by IntelliJ IDEA.
  User: pnikita
  Date: 25.12.2024
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Project settings</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card">
        <div class="card-body">
          <h5 class="card-title text-center">Edit Account</h5>

          <!-- Success message -->
          <c:if test="${not empty message}">
            <div class="alert alert-success" role="alert">
                ${message}
            </div>
          </c:if>

          <form method="post">
            <div class="mb-3 text-center">
              <label for="icon" class="form-label">Account Icon</label>
              <div class="mb-2">
                <img src="${pageContext.request.contextPath}/${iconUrl}" alt="Current Icon" class="rounded-circle" style="width: 100px; height: 100px; object-fit: cover;">
              </div>
              <input type="file" class="form-control" id="icon" name="icon" accept="image/*">
            </div>

            <div class="mb-3">
              <label for="username" class="form-label">Username</label>
              <input type="text" class="form-control" id="username" name="username" placeholder="${username}" value="${username}">
            </div>

            <div class="mb-3">
              <label for="password" class="form-label">Password</label>
              <input type="text" class="form-control" id="password" name="password" placeholder="New password">
            </div>

            <div class="mb-3">
              <label for="email" class="form-label">Email</label>
              <input type="email" class="form-control" id="email" name="email" placeholder="${email}" value="${email}">
            </div>

            <div class="mb-3">
              <label for="description" class="form-label">Description</label>
              <textarea class="form-control" id="description" name="description" rows="3" placeholder="${description}">${description}</textarea>
            </div>

            <div class="text-center">
              <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
