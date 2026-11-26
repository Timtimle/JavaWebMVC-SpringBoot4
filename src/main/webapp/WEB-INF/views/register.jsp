<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Register"/>
<%@ include file="fragments/header.jspf" %>
<section class="card auth">
    <h1>Register</h1>
    <p class="muted">Create a new user account.</p>
    <c:url var="formUrl" value="/register"/>
    <form:form action="${formUrl}" method="post" modelAttribute="userForm">
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
        <form:password path="password" id="password" required="required" minlength="6" maxlength="72" autocomplete="new-password"/>
        <button class="wide" type="submit">Register</button>
    </form:form>
    <p>
        Already registered?
        <a href="<c:url value='/login'/>">Login</a>
    </p>
</section>
<%@ include file="fragments/footer.jspf" %>
