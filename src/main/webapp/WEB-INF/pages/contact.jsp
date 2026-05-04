<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="dashboard-body">
<jsp:include page="_sidebar.jsp" />

<main class="main-content">
    <div class="topbar">
        <h2><i data-lucide="help-circle"></i> Contact & Feedback</h2>
    </div>

    <div class="content-wrapper">
        <div class="card" style="max-width: 800px; margin: 0 auto;">
            <div style="padding: 24px;">
                <h3>Get in Touch</h3>
                <p style="color: var(--clr-text-light); margin-bottom: 24px;">
                    Have a suggestion to improve CycleSync? Or want to report a complaint about a bike or station? 
                    We're here to listen.
                </p>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-error" style="margin-bottom: 20px;">
                        <i data-lucide="alert-triangle"></i> ${errorMessage}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/contact" method="post">
                    <div class="form-group">
                        <label class="form-label">Type <span class="required">*</span></label>
                        <div class="input-wrap">
                            <span class="input-icon"><i data-lucide="tag"></i></span>
                            <select name="type" class="form-control" style="padding-left: 38px; appearance: none;" required>
                                <option value="SUGGESTION">Suggestion</option>
                                <option value="COMPLAINT">Complaint</option>
                            </select>
                            <span style="position: absolute; right: 12px; top: 50%; transform: translateY(-50%); pointer-events: none;"><i data-lucide="chevron-down" style="width: 16px;"></i></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="subject">Subject <span class="required">*</span></label>
                        <div class="input-wrap">
                            <span class="input-icon"><i data-lucide="heading"></i></span>
                            <input type="text" id="subject" name="subject" class="form-control" placeholder="Brief summary" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="message">Your Message <span class="required">*</span></label>
                        <div class="input-wrap" style="align-items: flex-start;">
                            <span class="input-icon" style="top: 14px;"><i data-lucide="message-square"></i></span>
                            <textarea id="message" name="message" class="form-control" rows="6" 
                                      placeholder="Describe your suggestion or complaint in detail..." required 
                                      style="padding-left: 38px; min-height: 150px;"></textarea>
                        </div>
                    </div>

                    <div style="margin-top: 24px;">
                        <button type="submit" class="btn btn-primary btn-lg" style="width: 100%;">
                            Submit Feedback →
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        if (typeof lucide !== 'undefined') lucide.createIcons();
    });
</script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
