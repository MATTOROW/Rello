<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${project.getName()}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container my-4">
    <h1 class="text-center">${project.getName()}</h1>
    <p class="text-muted text-center">${project.getDescription()}</p>

    <hr>

    <!-- Кнопка для обновления задач -->
    <div class="text-right mb-3">
        <button id="refreshTasks" class="btn btn-info" onclick="refreshTasks()">Обновить задачи</button>
    </div>

    <!-- Секция для задач -->
    <div id="taskContainer">
        <c:set var="tasks" value="${project.getTasks()}"></c:set>
        <c:choose>
            <c:when test="${not empty tasks}">
                <div class="row">
                    <c:forEach var="task" items="${tasks}">
                        <div class="col-md-4 mb-4">
                            <div class="card h-100 task-card" data-task-id="${task.taskId()}">
                                <div class="card-body">
                                    <h5 class="card-title">${task.name()}</h5>
                                    <p class="card-text">
                                        <strong>Описание:</strong>
                                        <c:choose>
                                            <c:when test="${task.description() != null}">
                                                ${task.description()}
                                            </c:when>
                                            <c:otherwise>
                                                "Нет описания"
                                            </c:otherwise>
                                        </c:choose>
                                        <br>
                                        <strong>Статус:</strong> ${task.status()}<br>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="text-center text-muted">У вас еще нет ни одной задачи. <a href="#" class="btn btn-success">Создать задачу</a></p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
    function refreshTasks() {
        const projectId = '${project.getProjectId()}';
        const url = "${pageContext.request.contextPath}/tasks/" + projectId;
        const taskContainer = document.getElementById("taskContainer");

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Ошибка при обновлении задач");
                }
                return response.json();
            })
            .then(tasks => {
                taskContainer.innerHTML = '';

                if (tasks.length > 0) {
                    const row = document.createElement("div");
                    row.classList.add("row");

                    tasks.forEach(function (task) {
                        const col = document.createElement("div");
                        col.classList.add("col-md-4", "mb-4");

                        const card = document.createElement("div");
                        card.classList.add("card", "h-100", "task-card");
                        card.dataset.taskId = task.taskId;

                        const cardBody = document.createElement("div");
                        cardBody.classList.add("card-body");

                        const cardTitle = document.createElement("h5");
                        cardTitle.classList.add("card-title");
                        cardTitle.textContent = task.name;

                        const cardText = document.createElement("p");
                        cardText.classList.add("card-text");
                        cardText.innerHTML = "<strong>Описание:</strong> " + (task.description || "Описание отсутствует") + "<br>" +
                            "<strong>Статус:</strong> " + task.status;

                        cardBody.appendChild(cardTitle);
                        cardBody.appendChild(cardText);
                        card.appendChild(cardBody);
                        col.appendChild(card);
                        row.appendChild(col);
                    });

                    taskContainer.appendChild(row);
                } else {
                    taskContainer.innerHTML = "<p class='text-center text-muted'>У вас еще нет ни одной задачи. <a href='#' class='btn btn-success'>Создать задачу</a></p>";
                }
            })
            .catch(error => {
                console.error(error);
                alert("Ошибка при обновлении задач. Пожалуйста, попробуйте снова.");
            });
    }

    document.addEventListener("click", function (event) {
        if (event.target.closest(".task-card")) {
            const taskId = event.target.closest(".task-card").dataset.taskId;
            const projectId = '${project.getProjectId()}';
            const url = "${pageContext.request.contextPath}/tasks/" + projectId + "/task/" + taskId;

            fetch(url)
                .then(response => {
                    if (!response.ok) throw new Error("Ошибка при загрузке данных задачи");
                    return response.json();
                })
                .then(task => {
                    // Удаляем старое модальное окно, если оно существует
                    const existingModal = document.getElementById("dynamicTaskModal");
                    if (existingModal) existingModal.remove();

                    // Создаем новое модальное окно
                    const modal = document.createElement("div");
                    modal.id = "dynamicTaskModal";
                    modal.classList.add("modal", "fade");
                    modal.tabIndex = -1;
                    modal.setAttribute("aria-labelledby", "taskModalLabel");
                    modal.setAttribute("aria-hidden", "true");

                    // Режим просмотра (по умолчанию)
                    modal.innerHTML = `
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="taskModalLabel">Описание задачи</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p><strong>Название:</strong> \${task.name || "Не указано"}</p>
                                <p><strong>Описание:</strong> \${task.description || "Описание отсутствует"}</p>
                                <p><strong>Дата начала:</strong> \${task.startDate || "Не указана"}</p>
                                <p><strong>Дата окончания:</strong> \${task.endDate || "Не указана"}</p>
                                <hr>
                                <h6>Подзадачи</h6>
                                \${task.subtasks.map((subtask, index) => `
                                    <div class="mb-3">
                                        <p><strong>\${index + 1}. \${subtask.name || "Без названия"}</strong></p>
                                        <p>${subtask.description || "Описание отсутствует"}</p>
                                        <p><strong>Выполнено:</strong> \${subtask.completed ? "Да" : "Нет"}</p>
                                    </div>
                                `).join("")}
                            </div>
                            <div class="modal-footer">
                                <button id="editTask" type="button" class="btn btn-primary">Редактировать</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                            </div>
                        </div>
                    </div>
                `;

                    document.body.appendChild(modal);
                    const bootstrapModal = new bootstrap.Modal(modal);

                    // Обработка кнопки "Редактировать"
                    document.getElementById("editTask").onclick = () => {
                        // Переключаемся в режим редактирования
                        modal.querySelector(".modal-title").textContent = "Редактировать задачу";
                        modal.querySelector(".modal-body").innerHTML = `
                        <div class="mb-3">
                            <label for="taskName" class="form-label">Название задачи</label>
                            <input type="text" id="taskName" class="form-control" value="\${task.name}">
                        </div>
                        <div class="mb-3">
                            <label for="taskDescription" class="form-label">Описание задачи</label>
                            <textarea id="taskDescription" class="form-control" rows="3">\${task.description}</textarea>
                        </div>
                        <hr>
                        <div id="subtasksContainer">
                            <h6>Подзадачи</h6>
                            \${task.subtasks.map((subtask, index) => `
                                <div class="subtask-item mb-3">
                                    <div class="d-flex align-items-center mb-2">
                                        <input type="text" class="form-control me-2" placeholder="Название подзадачи" value="\${subtask.name}">
                                        <button class="btn btn-danger btn-sm" onclick="deleteSubtask(this)">Удалить</button>
                                    </div>
                                    <textarea class="form-control mb-2" placeholder="Описание подзадачи">\${subtask.description}</textarea>
                                    <div class="form-check">
                                        <input type="checkbox" class="form-check-input" \${subtask.completed ? "checked" : ""}>
                                        <label class="form-check-label">Выполнено</label>
                                    </div>
                                </div>
                            `).join("")}
                        </div>
                        <button id="addSubtask" class="btn btn-outline-primary btn-sm mt-3">Добавить подзадачу</button>
                    `;

                        modal.querySelector(".modal-footer").innerHTML = `
                        <button id="saveTask" type="button" class="btn btn-primary">Сохранить</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    `;

                        // Логика добавления новой подзадачи
                        document.getElementById("addSubtask").onclick = () => {
                            const subtasksContainer = document.getElementById("subtasksContainer");
                            const newSubtaskDiv = document.createElement("div");
                            newSubtaskDiv.classList.add("subtask-item", "mb-3");
                            newSubtaskDiv.innerHTML = `
                            <div class="d-flex align-items-center mb-2">
                                <input type="text" class="form-control me-2" placeholder="Название подзадачи">
                                <button class="btn btn-danger btn-sm" onclick="deleteSubtask(this)">Удалить</button>
                            </div>
                            <textarea class="form-control mb-2" placeholder="Описание подзадачи"></textarea>
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input">
                                <label class="form-check-label">Выполнено</label>
                            </div>
                        `;
                            subtasksContainer.appendChild(newSubtaskDiv);
                        };

                        // Логика сохранения изменений
                        document.getElementById("saveTask").onclick = () => {
                            const updatedTask = {
                                name: document.getElementById("taskName").value,
                                description: document.getElementById("taskDescription").value,
                                subtasks: Array.from(document.getElementById("subtasksContainer").getElementsByClassName("subtask-item")).map(item => ({
                                    name: item.querySelector("input[type='text']").value,
                                    description: item.querySelector("textarea").value,
                                    completed: item.querySelector("input[type='checkbox']").checked,
                                }))
                            };
                            console.log("Обновленные данные задачи:", updatedTask);
                            bootstrapModal.hide();
                            modal.remove();
                        };
                    };

                    // Показываем модальное окно
                    bootstrapModal.show();
                })
                .catch(error => {
                    console.error("Ошибка при загрузке задачи:", error);
                    alert("Не удалось загрузить данные задачи. Попробуйте снова.");
                });
        }
    });

    function deleteSubtask(button) {
        button.closest(".subtask-item").remove();
    }
</script>
</body>
</html>
