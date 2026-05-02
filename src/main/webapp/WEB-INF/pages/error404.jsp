<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Not Found — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body { background: var(--clr-bg); display:flex; align-items:center; justify-content:center; min-height:100vh; }
        .error-page-wrap { text-align:center; max-width:480px; padding:40px 24px; }
        .error-code {
            font-family: var(--font-heading);
            font-size: 8rem;
            font-weight: 800;
            line-height: 1;
            color: var(--clr-primary);
            opacity: .15;
            margin-bottom: -20px;
        }
        .error-emoji { font-size: 4rem; display:block; margin-bottom:16px; animation: floatBike 3s ease-in-out infinite; }
        @keyframes floatBike {
            0%,100% { transform:translateY(0); }
            50% { transform:translateY(-10px); }
        }
        .error-title { font-family:var(--font-heading); font-size:1.75rem; font-weight:800; margin-bottom:10px; }
        .error-desc { color:var(--clr-text-secondary); font-size:.95rem; margin-bottom:28px; line-height:1.7; }
        .error-actions { display:flex; gap:12px; justify-content:center; flex-wrap:wrap; }
    </style>
    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>
    <div class="error-page-wrap">
        <div class="error-code">404</div>
        <span class="error-emoji">🗺️</span>
        <h2 class="error-title">Oops! Wrong turn.</h2>
        <p class="error-desc">
            The page you're looking for doesn't exist or has been moved.
            Let's get you back on track.
        </p>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                <i data-lucide="layout-dashboard"></i> Go Home
            </a>
            <a href="javascript:history.back()" class="btn btn-outline">
                ← Go Back
            </a>
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
