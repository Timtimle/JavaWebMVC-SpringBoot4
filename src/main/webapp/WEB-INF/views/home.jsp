<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Home"/>
<%@ include file="fragments/header.jspf" %>
<section class="card">
    <h1>Welcome, <c:out value="${account.fullName}"/></h1>
    <p class="muted">Account dashboard</p>
    <dl>
        <dt>Username</dt>
        <dd><c:out value="${account.username}"/></dd>
        <dt>Email</dt>
        <dd><c:out value="${account.email}"/></dd>
        <dt>Phone</dt>
        <dd><c:out value="${account.phone}"/></dd>
        <dt>Role</dt>
        <dd><c:out value="${account.role}"/></dd>
    </dl>
    <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
        <div class="actions">
            <a class="button" href="<c:url value='/admin/categories'/>">Manage categories</a>
            <a class="button" href="<c:url value='/admin/users'/>">Manage users</a>
        </div>
    </c:if>
</section>
<%@ include file="fragments/footer.jspf" %>
