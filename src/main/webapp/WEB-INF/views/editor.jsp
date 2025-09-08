<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editor Persona</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h2 class="mb-4">
                    <c:choose>
                        <c:when test="${person.id != null}">Modifica Persona</c:when>
                        <c:otherwise>Nuova Persona</c:otherwise>
                    </c:choose>
                </h2>

                <!-- Alert per messaggi di errore -->
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle"></i> ${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/salva" method="post">
                    <c:if test="${person.id != null}">
                        <label for="id" class="form-label">ID</label>
                        <input type="text" class="form-control" id="id" name="id" 
                               value="${person.id}" readonly>
                    </c:if>
                    
                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome</label>
                        <input type="text" class="form-control" id="nome" name="nome" 
                               value="${person.nome}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="cognome" class="form-label">Cognome</label>
                        <input type="text" class="form-control" id="cognome" name="cognome" 
                               value="${person.cognome}" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="telefono" class="form-label">Telefono</label>
                        <input type="tel" class="form-control" id="telefono" name="telefono" 
                               value="${person.telefono}" required placeholder="es. +39 123 456 7890">
                    </div>

                    <div class="mb-3">
                        <label for="indirizzo" class="form-label">Indirizzo</label>
                        <input type="text" class="form-control" id="indirizzo" name="indirizzo" 
                               value="${person.indirizzo}">
                    </div>
                    
                    <div class="mb-3">
                        <label for="eta" class="form-label">Età</label>
                        <input type="number" class="form-control" id="eta" name="eta" 
                               value="${person.eta}" required min="0" max="120">
                    </div>
                    
                    <div class="d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/lista" class="btn btn-secondary">Annulla</a>
                        <button type="submit" class="btn btn-primary">
                            <c:choose>
                                <c:when test="${person.id != null}">Aggiorna</c:when>
                                <c:otherwise>Salva</c:otherwise>
                            </c:choose>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
