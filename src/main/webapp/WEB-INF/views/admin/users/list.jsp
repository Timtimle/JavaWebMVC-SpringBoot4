<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Users"/>
<%@ include file="../../fragments/header.jspf" %>
<section class="card">
    <div class="heading">
        <div>
            <h1>Users</h1>
            <p class="muted">Admin management</p>
        </div>
        <a class="button" href="<c:url value='/admin/users/new'/>">Add new</a>
    </div>
    <form method="get" action="<c:url value='/admin/users'/>" class="search">
        <input name="q" value="<c:out value='${q}'/>" placeholder="Search..." aria-label="Search">
        <button type="submit">Search</button>
        <a class="button secondary" href="<c:url value='/admin/users'/>">Clear</a>
    </form>
    <div class="table-wrap">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Full name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${result.content}">
                    <tr>
                        <td>${item.id}</td>
                        <td><c:out value="${item.username}"/></td>
                        <td><c:out value="${item.fullName}"/></td>
                        <td><c:out value="${item.email}"/></td>
                        <td><c:out value="${item.phone}"/></td>
                        <td><c:out value="${item.role}"/></td>
                        <td>
                            <div class="actions">
                                <a class="button secondary" href="<c:url value='/admin/users/${item.id}/edit'/>">Edit</a>
                                <form method="post" action="<c:url value='/admin/users/${item.id}/delete'/>" onsubmit="return confirm('Delete this record?');">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <button class="danger" type="submit">Delete</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty result.content}">
                    <tr>
                        <td colspan="7" class="muted">No records found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
    <div class="pagination">
        <span><c:out value="${result.totalElements}"/> records · Page ${result.totalPages == 0 ? 0 : result.number + 1} / ${result.totalPages}</span>
        <div class="actions">
            <c:if test="${result.number > 0}">
                <c:url var="prevUrl" value="/admin/users">
                    <c:param name="q" value="${q}"/>
                    <c:param name="page" value="${result.number - 1}"/>
                </c:url>
                <a class="button secondary" href="<c:out value='${prevUrl}'/>">Previous</a>
            </c:if>
            <c:if test="${result.totalPages > 0}">
                <c:set var="startPage" value="${result.number > 2 ? result.number - 2 : 0}"/>
                <c:set var="endPage" value="${startPage + 4 < result.totalPages ? startPage + 4 : result.totalPages - 1}"/>
                <c:forEach var="pageIndex" begin="${startPage}" end="${endPage}">
                    <c:choose>
                        <c:when test="${pageIndex == result.number}">
                            <span class="page-current" aria-current="page">${pageIndex + 1}</span>
                        </c:when>
                        <c:otherwise>
                            <c:url var="pageUrl" value="/admin/users">
                                <c:param name="q" value="${q}"/>
                                <c:param name="page" value="${pageIndex}"/>
                            </c:url>
                            <a class="button secondary" href="<c:out value='${pageUrl}'/>" aria-label="Page ${pageIndex + 1}">${pageIndex + 1}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
            <c:if test="${result.number + 1 < result.totalPages}">
                <c:url var="nextUrl" value="/admin/users">
                    <c:param name="q" value="${q}"/>
                    <c:param name="page" value="${result.number + 1}"/>
                </c:url>
                <a class="button secondary" href="<c:out value='${nextUrl}'/>">Next</a>
            </c:if>
        </div>
    </div>
</section>
<%@ include file="../../fragments/footer.jspf" %>
