<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projects</title>
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
    <h1 class="mb-4">Projects</h1>
    <input type="text" id="searchInput" class="form-control mb-4" placeholder="Search for projects..." onkeyup="searchProjects()">
    <div class="row">
        <c:forEach var="project" items="${projects}">
            <div class="col-md-4 mb-4 project-card" data-name="${project.name()}" data-description="${project.description()}">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${project.name()}</h5>
                        <p class="card-text">${project.description()}</p>
                        <!-- Форма для отправки ID проекта на сервлет -->
                        <form method="post">
                            <input type="hidden" name="projectId" value="${project.projectId()}">
                            <button type="submit" class="btn btn-primary edit-btn">View Details</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <a href="${pageContext.request.contextPath}/project/create" class="btn btn-success mt-3 edit-btn">Create Project</a>
</div>

<script>
    // Функция для поиска проектов
    function searchProjects() {
        const searchQuery = document.getElementById('searchInput').value.toLowerCase();
        const projects = document.querySelectorAll('.project-card');

        if (searchQuery === "") {
            // Если поле поиска пустое, показываем все проекты
            projects.forEach(project => {
                project.style.display = 'block';
            });
        } else {
            projects.forEach(project => {
                const name = project.querySelector('.card-title').textContent.toLowerCase();
                const description = project.querySelector('.card-text').textContent.toLowerCase();

                if (name.includes(searchQuery) || description.includes(searchQuery)) {
                    project.style.display = 'block'; // Показываем карточку
                } else {
                    project.style.display = 'none'; // Скрываем карточку
                }
            });
        }
    }
</script>
</body>
</html>