<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<% request.setAttribute("activePage", "manageMembers"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Members — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables.css">
    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title"><i data-lucide="users"></i> Manage Members</span>
        </header>

        <main class="page-body">

            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success"><span class="alert-icon"><i data-lucide="check-circle-2"></i></span> ${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-error"><span class="alert-icon"><i data-lucide="alert-triangle"></i></span> ${sessionScope.errorMessage}</div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <div class="page-header">
                <h2>Member Accounts</h2>
                <p>View, suspend, or remove member accounts from the system.</p>
            </div>

            <div class="table-toolbar">
                <div class="table-toolbar-left">
                    <span class="table-count">${not empty memberList ? memberList.size() : 0} members registered</span>
                </div>
            </div>

            <div class="table-wrap">
                <c:choose>
                    <c:when test="${empty memberList}">
                        <div class="empty-state">
                            <div class="empty-state-icon"><i data-lucide="users"></i></div>
                            <h4>No members registered yet</h4>
                            <p>Members will appear here after they register on the platform.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Member</th>
                                    <th>Phone</th>
                                    <th>Address</th>
                                    <th>Joined</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="member" items="${memberList}">
                                    <tr>
                                        <td class="td-id">#${member.userId}</td>
                                        <td>
                                            <div class="td-name">${member.fullName}</div>
                                            <div class="td-meta">${member.userEmail}</div>
                                        </td>
                                        <td>${not empty member.phoneNumber ? member.phoneNumber : '—'}</td>
                                        <td>
                                            <div style="max-width:160px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
                                                ${not empty member.userAddress ? member.userAddress : '—'}
                                            </div>
                                        </td>
                                        <td class="td-nowrap">
                                            <fmt:formatDate value="${member.createdAt}" pattern="dd MMM yyyy"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${member.accountStatus == 'ACTIVE'}">
                                                    <span class="badge badge-success">Active</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-danger">Suspended</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="td-actions">
                                                <!-- Toggle suspend/activate -->
                                                <form action="${pageContext.request.contextPath}/manageMembers"
                                                      method="post" style="display:inline;">
                                                    <input type="hidden" name="userId" value="${member.userId}">
                                                    <c:choose>
                                                        <c:when test="${member.accountStatus == 'ACTIVE'}">
                                                            <input type="hidden" name="action" value="suspend">
                                                            <button type="submit" class="btn btn-warning btn-sm">⏸ Suspend</button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="hidden" name="action" value="activate">
                                                            <button type="submit" class="btn btn-accent btn-sm">▶ Activate</button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </form>

                                                <!-- Delete -->
                                                <form action="${pageContext.request.contextPath}/manageMembers"
                                                      method="post" style="display:inline;"
                                                      onsubmit="return confirm('Permanently delete this member account?');">
                                                    <input type="hidden" name="userId" value="${member.userId}">
                                                    <input type="hidden" name="action" value="delete">
                                                    <button type="submit" class="btn btn-danger btn-sm"><i data-lucide="trash-2"></i> Delete</button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>

        </main>
    </div>
</div>


<script>
    document.addEventListener("DOMContentLoaded", function() {
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    });
</script>
</body>
</html>
