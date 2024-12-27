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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat&amp;display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/account/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-md py-3" data-bs-theme="dark">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}"><img class="logo" src="${pageContext.request.contextPath}/static/img/logo.svg" width="50px"><span class="brand-name">Rello</span></a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-5"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-5">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/projects">Projects</a></li>
            </ul>
                <div class="dropdown account-panel"><a class="dropdown-toggle my-dropdown" aria-expanded="false" data-bs-toggle="dropdown"><img class="acc-icon" src="<c:if test="${account.getIconPath() != null}">
                           ${account.getIconPath()}
                        </c:if><c:if test="${account.getIconPath() == null}">${pageContext.request.contextPath}/static/img/account_logo.svg</c:if>" width="40px" /><span class="username">${account.getUsername()}</span></a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/account">View account</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/account/settings">Edit account</a>
                        <a class="dropdown-item logout-btn btn btn-primary" href="${pageContext.request.contextPath}/logout">Log out</a></div>
                </div>
        </div>
    </div>
</nav>

<div class="container main-container">
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
                    <a href="account/settings" class="btn btn-primary mt-3 edit-btn">Edit Account</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
