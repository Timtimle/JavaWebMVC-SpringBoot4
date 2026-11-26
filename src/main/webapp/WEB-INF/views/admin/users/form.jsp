<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Users"/>
<%@ include file="../../fragments/header.jspf" %>
<section class="card auth">
    <h1>${empty editId ? 'Add' : 'Edit'} user</h1>
    <c:choose>
        <c:when test="${empty editId}">
            <c:url var="formUrl" value="/admin/users/new"/>
        </c:when>
        <c:otherwise>
            <c:url var="formUrl" value="/admin/users/${editId}/edit"/>
        </c:otherwise>
    </c:choose>
    <form:form method="post" action="${formUrl}" modelAttribute="userForm">
        <form:errors path="*" cssClass="field-error" element="div" htmlEscape="true"/>
        <label for="username">Username</label>
        <form:input path="username" id="username" required="required" maxlength="40" htmlEscape="true"/>
        <label for="fullName">Full name</label>
        <form:input path="fullName" id="fullName" required="required" maxlength="100" htmlEscape="true"/>
        <label for="email">Email</label>
        <form:input path="email" id="email" type="email" required="required" maxlength="150" htmlEscape="true"/>
        <label for="phone">Phone</label>
        <form:input path="phone" id="phone" maxlength="20" htmlEscape="true"/>
        <label for="password">Password</label>
        <c:choose>
            <c:when test="${not empty editId}">
                <form:password path="password" id="password" maxlength="72" autocomplete="new-password"/>
                <small>Leave blank to keep the current password.</small>
            </c:when>
            <c:otherwise>
                <form:password path="password" id="password" required="required" minlength="6" maxlength="72" autocomplete="new-password"/>
            </c:otherwise>
        </c:choose>
        <label for="role">Role</label>
        <form:select path="role" id="role" items="${roles}" htmlEscape="true"/>
        <div class="actions form-actions">
            <button type="submit">Save</button>
            <a class="button secondary" href="<c:url value='/admin/users'/>">Cancel</a>
        </div>
    </form:form>
</section>
<%@ include file="../../fragments/footer.jspf" %>
