<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                <!-- Header configurazione database -->
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>Configurazione Database</h4>
                </div>

                <!-- Messaggio di errore -->
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>

                <!-- Card per il modulo di configurazione database -->
                <div class="card">
                    <div class="card-body">
                        <form:form action="${pageContext.request.contextPath}/configure" 
                                  method="post" modelAttribute="config">
                            <div class="mb-3">
                                <label for="host" class="form-label">Host</label>
                                <form:input type="text" class="form-control" id="host" path="host" required/>
                            </div>
                            
                            <div class="mb-3">
                                <label for="port" class="form-label">Porta</label>
                                <form:input type="number" class="form-control" id="port" path="port" min="1" max="65535"/>
                            </div>
                            
                            <div class="mb-3">
                                <label for="dbName" class="form-label">Database</label>
                                <form:input type="text" class="form-control" id="dbName" path="dbName" required/>
                            </div>
                            
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <form:input type="text" class="form-control" id="username" path="username" required/>
                            </div>
                            
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <form:input type="password" class="form-control" id="password" path="password" required/>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Configura</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
