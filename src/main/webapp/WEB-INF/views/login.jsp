<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Login"/>
<%@ include file="fragments/header.jspf" %>
<section class="card auth">
    <h1>Login</h1>
    <p class="muted">Sign in to your account.</p>
    <c:if test="${param.error != null}">
        <div class="alert error">
            Invalid username or password.
        </div>
    </c:if>
    <c:if test="${param.logout != null}">
        <div class="alert success">
            You have logged out.
        </div>
    </c:if>
    <c:if test="${param.registered != null}">
        <div class="alert success">
            Account created. Please log in.
        </div>
    </c:if>
    <form method="post" action="<c:url value='/login'/>">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <label for="username">Username</label>
        <input id="username" name="username" required autocomplete="username" maxlength="40">
        <label for="password">Password</label>
        <input id="password" name="password" type="password" required autocomplete="current-password">
        <button type="submit" class="wide">Login</button>
    </form>
    <p>
        New account?
        <a href="<c:url value='/register'/>">Register</a>
    </p>
</section>
<%@ include file="fragments/footer.jspf" %>
