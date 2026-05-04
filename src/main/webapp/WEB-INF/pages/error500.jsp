<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Server Error — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            background: var(--clr-bg);
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        .error-page-wrap {
            text-align: center;
            max-width: 500px;
            padding: 40px 24px;
        }

        .error-code {
            font-family: var(--font-heading);
            font-size: 8rem;
            font-weight: 800;
            line-height: 1;
            color: var(--clr-danger);
            opacity: .12;
            margin-bottom: -20px;
        }

        .error-emoji {
            font-size: 4rem;
            display: block;
            margin-bottom: 16px;
            animation: wobble 2.5s ease-in-out infinite;
        }

        @keyframes wobble {
            0%, 100% { transform: rotate(0deg); }
            20%       { transform: rotate(-8deg); }
            40%       { transform: rotate(8deg); }
            60%       { transform: rotate(-4deg); }
            80%       { transform: rotate(4deg); }
        }

        .error-title {
            font-family: var(--font-heading);
            font-size: 1.75rem;
            font-weight: 800;
            margin-bottom: 10px;
            color: var(--clr-text);
        }

        .error-desc {
            color: var(--clr-text-secondary);
            font-size: .95rem;
            margin-bottom: 28px;
            line-height: 1.7;
        }

        .error-actions {
            display: flex;
            gap: 12px;
            justify-content: center;
            flex-wrap: wrap;
        }


    </style>
    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>
    <div class="error-page-wrap">

        <div class="error-code">500</div>
        <span class="error-emoji"><i data-lucide="settings"></i></span>

        <h2 class="error-title">Something went wrong.</h2>
        <p class="error-desc">
            Our server hit an unexpected bump in the road.
            This has been noted. Please try again in a moment,
            or go back to the home page.
        </p>

        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                <i data-lucide="layout-dashboard"></i> Go Home
            </a>
            <a href="javascript:history.back()" class="btn btn-outline">
                ← Try Again
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
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
