<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat&amp;display=swap">
    <link rel="stylesheet" href="static/css/login-register/styles.css">
</head>

<body class="d-flex align-items-center">
    <div class="container">
        <div class="row">
            <div class="col-md-3 logo">
                <div></div>
            </div>
            <div class="col-md-9 align-self-center px-4 main-col">
                <form method="POST" action="acc-check">
                    <div>
                        <div class="row my-2">
                            <div class="col">
                                <p class="fs-4 fw-bold header">Member login</p>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col">
                                <div class="d-flex align-items-center py-2 px-3 input-div"><svg class="me-2" xmlns="http://www.w3.org/2000/svg" enable-background="new 0 0 24 24" height="1em" viewBox="0 0 24 24" width="1em" fill="currentColor">
                                    <g>
                                        <rect fill="none" height="24" width="24"></rect>
                                    </g>
                                    <g>
                                        <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 4c1.93 0 3.5 1.57 3.5 3.5S13.93 13 12 13s-3.5-1.57-3.5-3.5S10.07 6 12 6zm0 14c-2.03 0-4.43-.82-6.14-2.88C7.55 15.8 9.68 15 12 15s4.45.8 6.14 2.12C16.43 19.18 14.03 20 12 20z"></path>
                                    </g>
                                </svg><input class="form-control input" type="text" placeholder="Username" name="username"></div>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col">
                                <div class="d-flex align-items-center py-2 px-3 input-div"><svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 24 24" width="1em" fill="currentColor" class="me-2">
                                        <path d="M0 0h24v24H0z" fill="none"></path>
                                        <path d="M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zm-6 9c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2zm3.1-9H8.9V6c0-1.71 1.39-3.1 3.1-3.1 1.71 0 3.1 1.39 3.1 3.1v2z"></path>
                                    </svg><input class="form-control input" type="text" placeholder="Password" name="password"></div>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col">
                                <div class="form-check"><input class="form-check-input" type="checkbox" id="formCheck-1" name="remember"><label class="form-check-label" for="formCheck-1">Remember me?</label></div>
                            </div>
                        </div>
                        <div class="row my-2">
                            <div class="col"><button class="btn btn-primary" type="submit">Log in</button></div>
                        </div>
                        <div class="row my-2">
                            <div class="col">
                                <p class="text-center new-acc mb-0">I don't have <a href="register">account</a>.</p>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>