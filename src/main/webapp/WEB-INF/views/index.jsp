<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Database Config</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <!-- Header -->
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>Database</h4>
                </div>
                
                <!-- Messaggi -->
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>

                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/configure" method="post">
                    <div class="mb-3">
                        <label for="host" class="form-label">Host</label>
                        <input type="text" class="form-control" id="host" name="host" 
                               value="localhost" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="port" class="form-label">Porta</label>
                        <input type="number" class="form-control" id="port" name="port" 
                               value="3306" required min="1" max="65535">
                    </div>
                    
                    <div class="mb-3">
                        <label for="dbName" class="form-label">Database</label>
                        <input type="text" class="form-control" id="dbName" name="dbName" 
                               value="rubrica" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username" 
                               value="rubrica" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" 
                               value="rubrica">
                    </div>
                    
                        <button type="submit" class="btn btn-primary">Configura</button>
                    </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
