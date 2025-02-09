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
  <h1>Task Settings</h1>

  <!-- Success message -->
  <c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
        ${message}
    </div>
  </c:if>

  <!-- Форма для редактирования названия и описания проекта -->
  <form method="post">
    <input type="hidden" name="subtaskId" value="${subtask.subtaskId()}">
    <div class="mb-4">
      <label for="subtaskName" class="form-label">Project Name</label>
      <input type="text" class="form-control" id="subtaskName" name="name" value="${subtask.name()}" required>
    </div>
    <div class="mb-4">
      <label for="subtaskDescription" class="form-label">Project Description</label>
      <textarea class="form-control" id="subtaskDescription" name="description" required>${subtask.description()}</textarea>
    </div>
    <button type="submit" class="btn btn-primary">Save Changes</button>
  </form>
  <form method="post">
    <input type="hidden" id="subtaskId" name="subtaskId" value="${subtask.subtaskId()}">
    <button type="submit" name="deleteSubtask" class="btn btn-danger mt-4">Delete Subtask</button>
  </form>
  <a href="${pageContext.request.contextPath}/subtask" class="btn btn-secondary mt-3">Back to Subtask</a>
</div>
</body>
</html>
