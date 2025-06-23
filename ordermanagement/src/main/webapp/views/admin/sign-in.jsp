<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign In - Order Management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Optional Icons (Bootstrap Icons) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

    <style>
        body {
            background: linear-gradient(to right, #43cea2, #185a9d);
            min-height: 100vh;
        }
        .login-card {
            max-width: 400px;
            margin: auto;
            margin-top: 10%;
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 0 25px rgba(0,0,0,0.1);
            padding: 2rem;
        }
        .login-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }
        .login-header h2 {
            color: #4b4b4b;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #667eea;
        }
        .btn-primary {
            background-color: #667eea;
            border-color: #667eea;
        }
        .btn-primary:hover {
            background-color: #5a67d8;
        }
    </style>
</head>
<body>

<div class="login-card">
    <div class="login-header">
        <h2><i class="bi bi-lock-fill"></i> Sign In</h2>
        <p class="text-muted">Please enter your credentials</p>
    </div>
    <form action="${pageContext.request.contextPath}/signin" method="post">
    	<sec:csrfInput/>
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <div class="input-group">
                <span class="input-group-text"><i class="bi bi-person"></i></span>
                <input type="text" class="form-control" id="username" name="username" required autofocus />
            </div>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <div class="input-group">
                <span class="input-group-text"><i class="bi bi-key-fill"></i></span>
                <input type="password" class="form-control" id="password" name="password" required autofocus />
            </div>
        </div>

        <div class="d-grid mb-3">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>

        <div class="text-center">
            <a href="${pageContext.request.contextPath}/signup" class="text-decoration-none text-secondary">Don't have an account? Sign up</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS Bundle (optional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
