<%--
  Created by IntelliJ IDEA.
  User: pnikita
  Date: 22.12.2024
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${username}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body text-center">
                    <!-- Иконка аккаунта -->
                    <c:if test="${iconUrl != null}"><img src="${pageContext.request.contextPath}/images/${iconUrl}" alt="Account Icon" class="rounded-circle mb-3" style="width: 100px; height: 100px; object-fit: cover;"></c:if>
                    <c:if test="${iconUrl == null}"><img src="${pageContext.request.contextPath}/static/img/account_logo.svg" alt="Account Icon" class="rounded-circle mb-3" style="width: 100px; height: 100px; object-fit: cover;"></c:if>

                    <!-- Имя пользователя -->
                    <h5 class="card-title mb-2">
                        ${username}
                    </h5>

                    <!-- Описание -->
                    <p class="card-text text-muted">
                        ${description}
                    </p>

                    <!-- Email -->
                    <p class="card-text">
                        <i class="bi bi-envelope"></i> ${email}
                    </p>
                    <a href="account/settings" class="btn btn-primary mt-3">Edit Account</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.js"></script>
</body>
</html>
