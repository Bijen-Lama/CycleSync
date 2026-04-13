<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setAttribute("activePage", "manageBicycles"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Bikes — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables.css">
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title">🚲 Manage Bicycles</span>
            <div class="topbar-actions">
                <button class="btn btn-primary btn-sm" onclick="toggleAddForm()">+ Add New Bike</button>
            </div>
        </header>

        <main class="page-body">

            <!-- Flash Messages -->
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success"><span class="alert-icon">✅</span> ${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-error"><span class="alert-icon">⚠️</span> ${sessionScope.errorMessage}</div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <!-- Add Bike Form (hidden by default) -->
            <div class="form-panel" id="addBikeForm" style="display:none;">
                <div class="form-panel-title">🆕 Add New Bicycle</div>
                <form action="${pageContext.request.contextPath}/manageBicycles" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="form-grid-3">
                        <div class="form-group">
                            <label class="form-label" for="bicycleName">Bike Name <span class="required">*</span></label>
                            <input type="text" id="bicycleName" name="bicycleName"
                                   class="form-control" placeholder="e.g. Trek Marlin 5" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="bicycleType">Type <span class="required">*</span></label>
                            <select id="bicycleType" name="bicycleType" class="form-control" required>
                                <option value="">Select type</option>
                                <option value="MOUNTAIN">Mountain</option>
                                <option value="ROAD">Road</option>
                                <option value="ELECTRIC">Electric</option>
                                <option value="HYBRID">Hybrid</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="hourlyRate">Hourly Rate ($) <span class="required">*</span></label>
                            <input type="number" id="hourlyRate" name="hourlyRate"
                                   class="form-control" placeholder="e.g. 3.50" step="0.01" min="0" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label" for="locationCode">Dock Location <span class="required">*</span></label>
                            <input type="text" id="locationCode" name="locationCode"
                                   class="form-control" placeholder="e.g. DOCK-A1" required>
                        </div>
                        <div class="form-group form-col-span-2">
                            <label class="form-label" for="description">Description</label>
                            <input type="text" id="description" name="description"
                                   class="form-control" placeholder="Optional notes about this bike">
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Save Bicycle</button>
                        <button type="button" class="btn btn-ghost" onclick="toggleAddForm()">Cancel</button>
                    </div>
                </form>
            </div>

            <!-- Edit Bike Form (hidden by default) -->
            <div class="form-panel" id="editBikeForm" style="display:none;">
                <div class="form-panel-title">✏️ Edit Bicycle</div>
                <form action="${pageContext.request.contextPath}/manageBicycles" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="bicycleId" id="edit_bicycleId">
                    <div class="form-grid-3">
                        <div class="form-group">
                            <label class="form-label">Bike Name <span class="required">*</span></label>
                            <input type="text" name="bicycleName" id="edit_bicycleName" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Type <span class="required">*</span></label>
                            <select name="bicycleType" id="edit_bicycleType" class="form-control" required>
                                <option value="MOUNTAIN">Mountain</option>
                                <option value="ROAD">Road</option>
                                <option value="ELECTRIC">Electric</option>
                                <option value="HYBRID">Hybrid</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Status <span class="required">*</span></label>
                            <select name="bicycleStatus" id="edit_bicycleStatus" class="form-control" required>
                                <option value="AVAILABLE">Available</option>
                                <option value="BORROWED">Borrowed</option>
                                <option value="MAINTENANCE">Maintenance</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Dock Location <span class="required">*</span></label>
                            <input type="text" name="locationCode" id="edit_locationCode" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Hourly Rate ($) <span class="required">*</span></label>
                            <input type="number" name="hourlyRate" id="edit_hourlyRate"
                                   class="form-control" step="0.01" min="0" required>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Description</label>
                            <input type="text" name="description" id="edit_description" class="form-control">
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Update Bicycle</button>
                        <button type="button" class="btn btn-ghost" onclick="closeEditForm()">Cancel</button>
                    </div>
                </form>
            </div>

            <!-- Table Toolbar -->
            <div class="table-toolbar">
                <div class="table-toolbar-left">
                    <h3 style="font-size:.95rem;">Fleet Overview</h3>
                    <span class="table-count">${not empty bicycleList ? bicycleList.size() : 0} bikes total</span>
                </div>
            </div>

            <!-- Bicycle Table -->
            <div class="table-wrap">
                <c:choose>
                    <c:when test="${empty bicycleList}">
                        <div class="empty-state">
                            <div class="empty-state-icon">🚲</div>
                            <h4>No bicycles added yet</h4>
                            <p>Click "Add New Bike" to start building your fleet.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Bicycle</th>
                                    <th>Type</th>
                                    <th>Status</th>
                                    <th>Location</th>
                                    <th>Rate/hr</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="bike" items="${bicycleList}">
                                    <tr>
                                        <td class="td-id">#${bike.bicycleId}</td>
                                        <td>
                                            <div class="td-name">${bike.bicycleName}</div>
                                            <div class="td-meta">${bike.description}</div>
                                        </td>
                                        <td>
                                            <span class="type-tag type-${fn:toLowerCase(bike.bicycleType)}">
                                                ${bike.bicycleType}
                                            </span>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${bike.bicycleStatus == 'AVAILABLE'}">
                                                    <span class="badge badge-success">Available</span>
                                                </c:when>
                                                <c:when test="${bike.bicycleStatus == 'BORROWED'}">
                                                    <span class="badge badge-info">Borrowed</span>
                                                </c:when>
                                                <c:when test="${bike.bicycleStatus == 'MAINTENANCE'}">
                                                    <span class="badge badge-warning">Maintenance</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>${bike.locationCode}</td>
                                        <td><strong>$<fmt:formatNumber value="${bike.hourlyRate}" pattern="#,##0.00"/></strong></td>
                                        <td>
                                            <div class="td-actions">
                                                <button class="btn btn-outline btn-sm"
                                                    onclick="openEditForm(
                                                        '${bike.bicycleId}',
                                                        '${bike.bicycleName}',
                                                        '${bike.bicycleType}',
                                                        '${bike.bicycleStatus}',
                                                        '${bike.locationCode}',
                                                        '${bike.hourlyRate}',
                                                        '${bike.description}'
                                                    )">✏️ Edit</button>

                                                <form action="${pageContext.request.contextPath}/manageBicycles"
                                                      method="post"
                                                      onsubmit="return confirm('Delete this bicycle permanently?');"
                                                      style="display:inline;">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="bicycleId" value="${bike.bicycleId}">
                                                    <button type="submit" class="btn btn-danger btn-sm">🗑️ Delete</button>
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
    function toggleAddForm() {
        var form = document.getElementById('addBikeForm');
        var editForm = document.getElementById('editBikeForm');
        editForm.style.display = 'none';
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
        if (form.style.display === 'block') form.scrollIntoView({ behavior: 'smooth' });
    }

    function openEditForm(id, name, type, status, location, rate, desc) {
        document.getElementById('addBikeForm').style.display = 'none';
        document.getElementById('edit_bicycleId').value    = id;
        document.getElementById('edit_bicycleName').value  = name;
        document.getElementById('edit_bicycleType').value  = type;
        document.getElementById('edit_bicycleStatus').value= status;
        document.getElementById('edit_locationCode').value = location;
        document.getElementById('edit_hourlyRate').value   = rate;
        document.getElementById('edit_description').value  = desc !== 'null' ? desc : '';
        var form = document.getElementById('editBikeForm');
        form.style.display = 'block';
        form.scrollIntoView({ behavior: 'smooth' });
    }

    function closeEditForm() {
        document.getElementById('editBikeForm').style.display = 'none';
    }
</script>

</body>
</html>
