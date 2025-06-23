<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up - Order Management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Bootstrap Icons (optional) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

    <style>
        body {
            background: linear-gradient(to right, #43cea2, #185a9d);
            min-height: 100vh;
        }
        .signup-card {
            max-width: 450px;
            margin: auto;
            margin-top: 5%;
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 0 25px rgba(0,0,0,0.1);
            padding: 2rem;
        }
        .signup-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }
        .signup-header h2 {
            color: #333;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #43cea2;
        }
        .btn-success {
            background-color: #43cea2;
            border-color: #43cea2;
        }
        .btn-success:hover {
            background-color: #3dbd90;
        }
    </style>
</head>
<body>

<div class="signup-card">
    <div class="signup-header">
        <h2><i class="bi bi-person-plus-fill"></i> Create Account</h2>
    </div>
    <form action="${pageContext.request.contextPath}/signup" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <div class="input-group">
                <span class="input-group-text"><i class="bi bi-person"></i></span>
                <input type="email" placeholder="Enter email as username" class="form-control" id="username" name="username" required>
            </div>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <div class="input-group">
                <span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
        </div>

        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Confirm Password</label>
            <div class="input-group">
                <span class="input-group-text"><i class="bi bi-lock"></i></span>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required />                
            </div>
            <p id="matchMessage" class="text-danger d-none">Passwords do not match!</p>
        </div>

        <div class="d-grid mb-3">
            <button type="submit" class="btn btn-success" id="btn" disabled="disabled">Sign Up</button>
        </div>

        <div class="text-center">
            <a href="${pageContext.request.contextPath}/signin" class="text-decoration-none text-secondary">Already have an account? Sign in</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS (optional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/admin-sign-up.js"></script>
</body>
</html>
