<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rubrica</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">Rubrica Contatti</h1>

        <!-- Alert per messaggi di successo -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <!-- Alert per messaggi di errore -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle"></i> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/editor" class="btn btn-primary mb-3">Nuovo</a>

        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Indirizzo</th>
                    <th scope="col">Telefono</th>
                    <th scope="col">Età</th>
                    <th scope="col" class="text-center">Modifica</th>
                    <th scope="col" class="text-center">Elimina</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty listPerson}">
                    <c:forEach var="persona" items="${listPerson}">
                        <tr>
                            <td class="fw-bold">${persona.id}</td>
                            <td>${persona.nome}</td>
                            <td>${persona.cognome}</td>
                            <td class="text-muted">${persona.indirizzo}</td>
                            <td>${persona.telefono}</td>
                            <td class="text-center">${persona.eta}</td>
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/editor/${persona.id}" 
                                   class="btn btn-warning btn-sm">
                                   <i class="fas fa-edit"></i> Modifica
                                </a>
                            </td>
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/elimina/${persona.id}"  
                                   class="btn btn-danger btn-sm" 
                                   onclick="return confirm('Eliminare ${persona.nome} ${persona.cognome}?')">
                                   <i class="fas fa-trash"></i> Elimina
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty listPerson}">
                    <tr>
                        <td colspan="8" class="text-center text-muted">Nessun contatto presente</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>