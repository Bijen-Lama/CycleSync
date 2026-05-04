<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title"><i data-lucide="user"></i> Edit Profile</span>
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

            <div class="card" style="max-width: 600px; margin: 0 auto;">
                <div class="card-header">
                    <h3>Personal Information</h3>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/editProfile" method="post">
                        
                        <div class="form-group">
                            <label class="form-label" for="userEmail">Email Address <span class="required">*</span></label>
                            <div class="input-wrap">
                                <span class="input-icon"><i data-lucide="mail"></i></span>
                                <input type="email" id="userEmail" name="userEmail" class="form-control" value="${sessionScope.loggedInUser.userEmail}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="fullName">Full Name <span class="required">*</span></label>
                            <div class="input-wrap">
                                <span class="input-icon"><i data-lucide="user"></i></span>
                                <input type="text" id="fullName" name="fullName" class="form-control" value="${sessionScope.loggedInUser.fullName}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="phoneNumber">Phone Number</label>
                            <div class="input-wrap">
                                <span class="input-icon"><i data-lucide="smartphone"></i></span>
                                <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" value="${sessionScope.loggedInUser.phoneNumber}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label" for="nationality">Nationality <span class="required">*</span></label>
                            <div class="input-wrap">
                                <span class="input-icon"><i data-lucide="globe"></i></span>
                                <select id="nationality" name="nationality" class="form-control" style="padding-left: 38px; appearance: none;" required>
                                    <option value="" disabled>Select Nationality</option>
                                    <option value="Nepalese" ${sessionScope.loggedInUser.nationality == 'Nepalese' ? 'selected' : ''}>Nepal</option>
                                    <option value="American" ${sessionScope.loggedInUser.nationality == 'American' ? 'selected' : ''}>United States</option>
                                    <option value="British" ${sessionScope.loggedInUser.nationality == 'British' ? 'selected' : ''}>United Kingdom</option>
                                    <option value="Indian" ${sessionScope.loggedInUser.nationality == 'Indian' ? 'selected' : ''}>India</option>
                                    <option value="Chinese" ${sessionScope.loggedInUser.nationality == 'Chinese' ? 'selected' : ''}>China</option>
                                    <option value="Australian" ${sessionScope.loggedInUser.nationality == 'Australian' ? 'selected' : ''}>Australia</option>
                                    <option value="Canadian" ${sessionScope.loggedInUser.nationality == 'Canadian' ? 'selected' : ''}>Canada</option>
                                    <option value="French" ${sessionScope.loggedInUser.nationality == 'French' ? 'selected' : ''}>France</option>
                                    <option value="German" ${sessionScope.loggedInUser.nationality == 'German' ? 'selected' : ''}>Germany</option>
                                    <option value="Japanese" ${sessionScope.loggedInUser.nationality == 'Japanese' ? 'selected' : ''}>Japan</option>
                                    <option value="Korean" ${sessionScope.loggedInUser.nationality == 'Korean' ? 'selected' : ''}>South Korea</option>
                                    <option value="Other" ${sessionScope.loggedInUser.nationality == 'Other' ? 'selected' : ''}>Other</option>
                                </select>
                                <span style="position: absolute; right: 12px; top: 50%; transform: translateY(-50%); pointer-events: none;"><i data-lucide="chevron-down" style="width: 16px;"></i></span>
                            </div>
                        </div>

                        <hr style="margin: 24px 0; border: none; border-top: 1px solid var(--clr-border-light);">
                        <h4 style="margin-bottom: 16px; color: var(--clr-text-secondary);">Change Password</h4>

                        <div class="form-group">
                            <label class="form-label" for="userPassword">New Password</label>
                            <div class="input-wrap">
                                <span class="input-icon"><i data-lucide="lock"></i></span>
                                <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="Leave blank to keep current password">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 12px;">
                            <i data-lucide="save"></i> Save Changes
                        </button>
                    </form>
                </div>
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
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
