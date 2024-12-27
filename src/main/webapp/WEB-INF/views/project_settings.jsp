<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Project Settings</title>
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
  <h1>Project Settings</h1>

  <!-- Success message -->
  <c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
        ${message}
    </div>
  </c:if>

  <!-- Форма для редактирования названия и описания проекта -->
  <form method="post">
    <input type="hidden" name="projectId" value="${project.projectId()}">
    <div class="mb-4">
      <label for="projectName" class="form-label">Project Name</label>
      <input type="text" class="form-control" id="projectName" name="name" value="${project.name()}" required>
    </div>
    <div class="mb-4">
      <label for="projectDescription" class="form-label">Project Description</label>
      <textarea class="form-control" id="projectDescription" name="description" required>${project.description()}</textarea>
    </div>
    <button type="submit" class="btn btn-primary edit-btn">Save Changes</button>
  </form>

  <!-- Текущий владелец проекта -->
  <div class="mt-5">
    <h4>Project Owner</h4>
    <p>${owner.username()}</p>
  </div>

  <!-- Список участников проекта -->
  <div class="mt-4">
    <h4>Project Members</h4>
    <ul class="list-group">
      <c:forEach var="participant" items="${participants}">
        <c:if test="${!participant.value.equals('OWNER')}">
          <li class="list-group-item d-flex justify-content-between align-items-center">
            <span>${participant.key.username()}</span>
            <span class="badge bg-secondary">${participant.value}</span>
            <c:if test="${this_account_role == 'OWNER'}">
              <button class="btn btn-warning btn-sm ms-3 change-role-btn"
                      data-username="${participant.key.username()}"
                      data-current-role="${participant.value}">
                Change Role
              </button>

              <!-- Dropdown для изменения роли -->
              <div class="role-dropdown" id="roleDropdown-${participant.key.username()}" style="display: none;">
                <select class="form-select role-select">
                  <option value="MEMBER">MEMBER</option>
                  <option value="ADMIN">ADMIN</option>
                </select>
                <button class="btn btn-primary btn-sm ms-2 save-role-btn">Save</button>
              </div>
            </c:if>
            <c:if test="${this_account_role == 'OWNER' || this_account_role == 'ADMIN'}">
              <button class="btn btn-danger btn-sm ms-3 remove-member-btn"
                      data-username="${participant.key.username()}">
                Remove
              </button>
            </c:if>
          </li>
        </c:if>
      </c:forEach>
    </ul>
  </div>
  <c:if test="${this_account_role == 'OWNER' || this_account_role == 'ADMIN'}">
    <div class="mt-4">
      <h4>Add Member</h4>
      <div class="mb-3">
        <label for="userSearch" class="form-label">Search User:</label>
        <input type="text" id="userSearch" class="form-control" placeholder="Enter username..." onkeyup="searchUsers()">
        <div id="userDropdown" class="dropdown-menu show" style="display: none;"></div>
      </div>
      <div class="mb-3">
        <label for="roleSelect" class="form-label">Select Role:</label>
        <select id="roleSelect" class="form-select">
          <c:forEach var="role" items="${roles}">
            <c:if test="${!role.equals('OWNER')}">
              <option value="${role}">${role}</option>
            </c:if>
          </c:forEach>
        </select>
      </div>
      <button class="btn btn-primary edit-btn" onclick="addMember()">Add</button>
    </div>
  </c:if>

  <c:if test="${this_account_role == 'OWNER'}">
    <form method="post">
      <input type="hidden" id="projectId" name="projectId" value="${project.projectId()}">
      <button type="submit" name="deleteProject" class="btn btn-danger mt-4">Delete Project</button>
    </form>
  </c:if>

  <a href="${pageContext.request.contextPath}/project" class="btn btn-secondary mt-3">Back to Project</a>
</div>

<script>
  document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.remove-member-btn');
    buttons.forEach(button => {
      button.addEventListener('click', function () {
        const username = this.dataset.username;
        const projectId = document.getElementById('projectId').value.trim();

        if (confirm(`Are you sure you want to remove \${username} from the project?`)) {
          fetch('${pageContext.request.contextPath}/project/participants/remove', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({projectId: projectId, username: username })
          })
                  .then(response => {
                    if (response.ok) {
                      alert(`\${username} has been removed.`);
                      location.reload();
                    } else {
                      response.text().then(msg => alert(`Error: \${msg}`));
                    }
                  })
                  .catch(error => alert(`Request failed: \${error}`));
        }
      });
    });
  });

  // Изменение роли участника
  const changeRoleButtons = document.querySelectorAll('.change-role-btn');
  changeRoleButtons.forEach(button => {
    button.addEventListener('click', function () {
      const username = this.dataset.username;
      const currentRole = this.dataset.currentRole;
      const dropdown = document.getElementById(`roleDropdown-\${username}`);
      dropdown.style.display = 'block'; // Показываем dropdown с выбором роли
    });
  });

  // Сохранение новой роли
  const saveRoleButtons = document.querySelectorAll('.save-role-btn');
  saveRoleButtons.forEach(button => {
    button.addEventListener('click', function () {
      const projectId = document.getElementById('projectId').value.trim();
      const username = this.closest('div.role-dropdown').id.split('-')[1];
      const roleSelect = this.closest('div.role-dropdown').querySelector('.role-select');
      const newRole = roleSelect.value;

      fetch('${pageContext.request.contextPath}/project/participants/update-role', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ projectId: projectId, username: username, role: newRole })
      })
              .then(response => {
                if (response.ok) {
                  alert(`Role updated to \${newRole}`);
                  location.reload(); // Перезагрузить страницу, чтобы отразить изменения
                } else {
                  alert('Error updating role.');
                }
              })
              .catch(error => console.error('Error updating role:', error));
    });
  });

  function searchUsers() {
    const input = document.getElementById('userSearch').value.trim();
    const dropdown = document.getElementById('userDropdown');

    if (input.length === 0) {
      dropdown.style.display = 'none';
      return;
    }

    fetch(`${pageContext.request.contextPath}/accounts?username=\${encodeURIComponent(input)}`)
            .then(response => response.json())
            .then(users => {
              dropdown.innerHTML = '';
              if (users.length > 0) {
                dropdown.style.display = 'block';
                users.forEach(user => {
                  const option = document.createElement('button');
                  option.className = 'dropdown-item';
                  option.textContent = user.username;
                  option.onclick = () => {
                    document.getElementById('userSearch').value = user.username;
                    dropdown.style.display = 'none';
                  };
                  dropdown.appendChild(option);
                });
              } else {
                dropdown.style.display = 'none';
              }
            })
            .catch(error => console.error('Error fetching users:', error));
  }

  function addMember() {
    const projectId = document.getElementById('projectId').value.trim();
    const username = document.getElementById('userSearch').value.trim();
    const role = document.getElementById('roleSelect').value;

    if (!username || !role) {
      alert('Please enter a username and select a role.');
      return;
    }

    fetch('${pageContext.request.contextPath}/project/participants/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({projectId, username, role }),
    })
            .then(response => {
              if (response.ok) {
                alert('Member added successfully!');
                location.reload(); // Reload the page to reflect changes
              } else {
                alert('Error adding member.');
              }
            })
            .catch(error => console.error('Error adding member:', error));
  }
</script>
</body>
</html>