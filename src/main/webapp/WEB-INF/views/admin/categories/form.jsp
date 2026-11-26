<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Categories"/>
<%@ include file="../../fragments/header.jspf" %>
<section class="card auth">
    <h1>${empty editId ? 'Add' : 'Edit'} category</h1>
    <c:choose>
        <c:when test="${empty editId}">
            <c:url var="formUrl" value="/admin/categories/new"/>
        </c:when>
        <c:otherwise>
            <c:url var="formUrl" value="/admin/categories/${editId}/edit"/>
        </c:otherwise>
    </c:choose>
    <form:form method="post" action="${formUrl}" modelAttribute="categoryForm">
        <form:errors path="*" cssClass="field-error" element="div" htmlEscape="true"/>
        <label for="name">Category name</label>
        <form:input path="name" id="name" required="required" maxlength="100" htmlEscape="true"/>
        <label for="description">Description</label>
        <form:textarea path="description" id="description" rows="5" maxlength="500" htmlEscape="true"/>
        <div class="actions form-actions">
            <button type="submit">Save</button>
            <a class="button secondary" href="<c:url value='/admin/categories'/>">Cancel</a>
        </div>
    </form:form>
</section>
<%@ include file="../../fragments/footer.jspf" %>
