<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Task Details</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4">${task.getName()}</h1>
  <p>${task.getDescription()}</p>

  <h4>Status: ${task.getStatus()}</h4>
  <p>Created on: ${task.getStartDate()}</p>
  <p>Due Date: ${task.getEndDate()}</p>

  <h3>Subtasks</h3>
  <input type="text" id="searchInput" class="form-control mb-4" placeholder="Search for subtasks..." onkeyup="searchSubtasks()">
  <div class="row">
    <c:forEach var="subtask" items="${task.subtasks}">
      <div class="col-md-4 mb-4 subtask-card">
        <div class="card h-100 shadow-sm">
          <div class="card-body">
            <h5 class="card-title">${subtask.name()}</h5>
            <p class="card-text">${subtask.description()}</p>

            <!-- Чекбокс для отметки завершенности подзадачи -->
            <div class="form-check">
              <input class="form-check-input" type="checkbox"
                     id="subtask_${subtask.subtaskId()}"
                ${subtask.completed() ? 'checked' : ''}
                     onclick="updateSubtaskStatus('${subtask.subtaskId()}', this)">
              <label class="form-check-label" for="subtask_${subtask.subtaskId()}">
                Completed
              </label>
            </div>

            <!-- Форма для отправки ID подзадачи на сервлет -->
            <form method="post">
              <input type="hidden" name="subtaskId" value="${subtask.subtaskId()}">
              <button type="submit" class="btn btn-primary">View Subtask Details</button>
            </form>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <a href="${pageContext.request.contextPath}/subtask/create" class="btn btn-success">Create Subtask</a>

  <a href="${pageContext.request.contextPath}/project" class="btn btn-secondary">Back to Project</a>
</div>

<script>
  // Функция для поиска проектов
  function searchSubtasks() {
    const searchQuery = document.getElementById('searchInput').value.toLowerCase();
    const subtasks = document.querySelectorAll('.subtask-card');

    if (searchQuery === "") {
      // Если поле поиска пустое, показываем все проекты
      subtasks.forEach(project => {
        project.style.display = 'block';
      });
    } else {
      subtasks.forEach(subtask => {
        const name = subtask.querySelector('.card-title').textContent.toLowerCase();
        const description = subtask.querySelector('.card-text').textContent.toLowerCase();

        if (name.includes(searchQuery) || description.includes(searchQuery)) {
          subtask.style.display = 'block'; // Показываем карточку
        } else {
          subtask.style.display = 'none'; // Скрываем карточку
        }
      });
    }
  }

  function updateSubtaskStatus(subtaskId, checkbox) {
    const isCompleted = checkbox.checked;

    fetch('${pageContext.request.contextPath}/subtask/update-status', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ subtaskId: subtaskId, completed: isCompleted.toString(), taskId: '${task.getTaskId()}' })
    })
            .then(response => {
              if (!response.ok) {
                alert('Error updating subtask status');
                checkbox.checked = !isCompleted;  // Revert checkbox state on failure
              }
            })
            .catch(error => {
              console.error('Error updating subtask status:', error);
              checkbox.checked = !isCompleted;  // Revert checkbox state on failure
            });
  }
</script>
</body>
</html>
