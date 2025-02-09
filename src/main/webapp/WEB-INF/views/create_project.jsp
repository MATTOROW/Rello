<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Project</title>
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
            <div class="dropdown account-panel"><a class="dropdown-toggle my-dropdown" aria-expanded="false" data-bs-toggle="dropdown"><img class="acc-icon rounded-circle" src="<c:if test="${account.getIconPath() != null}">${pageContext.request.contextPath}/images/${account.getIconPath()}</c:if><c:if test="${account.getIconPath() == null}">${pageContext.request.contextPath}/static/img/account_logo.svg</c:if>" width="40px" /><span class="username">${account.getUsername()}</span></a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/account">View account</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/account/settings">Edit account</a>
                    <a class="dropdown-item logout-btn btn btn-primary" href="${pageContext.request.contextPath}/logout">Log out</a></div>
            </div>
        </div>
    </div>
</nav>

<div class="container main-container">
    <h1>Create project</h1>
    <form method="post">

        <div class="mb-3">
            <label for="projectName" class="form-label">Project title</label>
            <input type="text" class="form-control" id="projectName" name="name" maxlength="255" required>
        </div>

        <div class="mb-3">
            <label for="projectDescription" class="form-label">Project description</label>
            <textarea class="form-control" id="projectDescription" name="description" rows="3" required></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Create Project</button>
        <a href="${pageContext.request.contextPath}/projects" class="btn btn-secondary edit-btn">Back to projects</a>
    </form>
</div>
</body>
</html>
