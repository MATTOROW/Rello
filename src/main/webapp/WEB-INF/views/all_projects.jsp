<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projects</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
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
                            <button type="submit" class="btn btn-primary">View Details</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <a href="${pageContext.request.contextPath}/project/create" class="btn btn-success mt-3">Create Project</a>
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