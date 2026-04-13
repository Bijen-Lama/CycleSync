<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
</head>
<body>

<div class="auth-page">

    <!-- ── Left Brand Panel ────────────────────────────────────── -->
    <div class="auth-panel-left">
        <div class="auth-brand">
            <div class="auth-brand-icon">🚲</div>
            <div>
                <div class="auth-brand-name">CycleSync</div>
                <div class="auth-brand-tagline">Eco Bicycle Sharing</div>
            </div>
        </div>

        <div class="auth-panel-hero">
            <span class="auth-hero-emoji">🌿</span>
            <h1 class="auth-hero-headline">Start Your<br><span>Eco Journey.</span></h1>
            <p class="auth-hero-desc">
                Create a free CycleSync account today. Access the full
                bike fleet, track your rides, and become part of the
                campus green transport movement.
            </p>
        </div>

        <div class="auth-features">
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Free account — no subscription fee
            </div>
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Instant access to available bikes
            </div>
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Pay only for hours you ride
            </div>
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Your data is private and secure
            </div>
        </div>
    </div>

    <!-- ── Right Form Panel ────────────────────────────────────── -->
    <div class="auth-panel-right">

        <div class="auth-form-header">
            <h2>Create your account</h2>
            <p>Join thousands of campus cyclists today.</p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <span class="alert-icon">⚠️</span>
                ${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" autocomplete="on">

            <div class="form-group">
                <label class="form-label" for="fullName">Full Name <span class="required">*</span></label>
                <div class="input-wrap">
                    <span class="input-icon">👤</span>
                    <input type="text"
                           id="fullName"
                           name="fullName"
                           class="form-control"
                           placeholder="Your full name"
                           value="${not empty param.fullName ? param.fullName : ''}"
                           required
                           autocomplete="name">
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="userEmail">Email Address <span class="required">*</span></label>
                <div class="input-wrap">
                    <span class="input-icon">✉️</span>
                    <input type="email"
                           id="userEmail"
                           name="userEmail"
                           class="form-control"
                           placeholder="you@university.edu"
                           value="${not empty param.userEmail ? param.userEmail : ''}"
                           required
                           autocomplete="email">
                </div>
            </div>

            <div class="form-row-2">
                <div class="form-group">
                    <label class="form-label" for="userPassword">Password <span class="required">*</span></label>
                    <div class="input-wrap">
                        <span class="input-icon">🔒</span>
                        <input type="password"
                               id="userPassword"
                               name="userPassword"
                               class="form-control"
                               placeholder="Min. 8 characters"
                               required
                               autocomplete="new-password">
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="confirmPassword">Confirm Password <span class="required">*</span></label>
                    <div class="input-wrap">
                        <span class="input-icon">🔒</span>
                        <input type="password"
                               id="confirmPassword"
                               name="confirmPassword"
                               class="form-control"
                               placeholder="Repeat password"
                               required
                               autocomplete="new-password">
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="phoneNumber">Phone Number</label>
                <div class="input-wrap">
                    <span class="input-icon">📱</span>
                    <input type="tel"
                           id="phoneNumber"
                           name="phoneNumber"
                           class="form-control"
                           placeholder="Your contact number"
                           value="${not empty param.phoneNumber ? param.phoneNumber : ''}"
                           autocomplete="tel">
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="userAddress">Address</label>
                <div class="input-wrap">
                    <span class="input-icon">📍</span>
                    <input type="text"
                           id="userAddress"
                           name="userAddress"
                           class="form-control"
                           placeholder="Your home address"
                           value="${not empty param.userAddress ? param.userAddress : ''}"
                           autocomplete="street-address">
                </div>
            </div>

            <div class="form-group">
                <label class="form-check">
                    <input type="checkbox" name="terms" required>
                    I agree to the <a href="#">Terms &amp; Conditions</a> and <a href="#">Privacy Policy</a>
                </label>
            </div>

            <button type="submit" class="btn btn-primary btn-lg" style="width:100%; margin-top:6px;">
                Create Account →
            </button>

        </form>

        <div class="auth-switch">
            Already have an account?
            <a href="${pageContext.request.contextPath}/login">Sign in</a>
        </div>

    </div>
</div>

</body>
</html>
