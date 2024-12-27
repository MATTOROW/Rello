<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Rello</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat&amp;display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main/styles.css">
    <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
    <nav class="navbar navbar-expand-md py-3" data-bs-theme="dark">
        <div class="container"><a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}"><img class="logo" src="${pageContext.request.contextPath}/static/img/logo.svg" width="50px"><span class="brand-name">Rello</span></a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-5"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-5">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/projects">Projects</a></li>
                </ul>
                <c:if test="${account == null}"><a class="btn btn-primary ms-md-2 login-register-btn" role="button" href="${pageContext.request.contextPath}/login">Login</a><a class="btn btn-primary ms-md-2 login-register-btn" role="button" href="${pageContext.request.contextPath}/register">Register</a></c:if>
                <c:if test="${account != null}">
                    <div class="dropdown account-panel"><a class="dropdown-toggle my-dropdown" aria-expanded="false" data-bs-toggle="dropdown"><img class="acc-icon" src="<c:if test="${account.getIconPath() != null}">
                           ${account.getIconPath()}
                        </c:if><c:if test="${account.getIconPath() == null}">${pageContext.request.contextPath}/static/img/account_logo.svg</c:if>" width="40px" /><span class="username">${account.getUsername()}</span></a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/account">View account</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/account/settings">Edit account</a>
                            <a class="dropdown-item logout-btn btn btn-primary" href="${pageContext.request.contextPath}/logout">Log out</a></div>
                    </div>
                </c:if>
            </div>
        </div>
    </nav>
    <div class="container main-container">
        <h2>Welcome to Rello project!</h2>
        <p>This website is designed for easy project management with your team. This service allows you to visualize current tasks, which will make working on a project easier. Try it, you just need to sign up!</p>
    </div>
</body>

</html>