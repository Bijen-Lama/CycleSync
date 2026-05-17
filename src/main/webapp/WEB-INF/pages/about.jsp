<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>About Us — CycleSync</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .about-hero {
                background: linear-gradient(135deg, var(--clr-primary), var(--clr-primary-dark));
                color: white;
                padding: 80px 20px;
                text-align: center;
            }

            .about-section {
                max-width: 900px;
                margin: 60px auto;
                padding: 0 20px;
                line-height: 1.8;
                color: var(--clr-text-secondary);
            }

            .about-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 30px;
                margin-top: 40px;
            }

            .feature-box {
                padding: 30px;
                background: #fff;
                border-radius: var(--radius-lg);
                border: 1px solid var(--clr-border-light);
                text-align: center;
            }

            .feature-box i {
                font-size: 2.5rem;
                color: var(--clr-primary);
                margin-bottom: 20px;
            }
        </style>
    </head>

    <body>
        <header class="about-hero">
            <h1 style="font-size: 3.5rem; margin-bottom: 15px;">CycleSync</h1>
            <p style="font-size: 1.2rem; opacity: 0.9;">Revolutionizing Campus Mobility, One Pedal at a Time.</p>
        </header>

        <div class="about-section">
            <h2 style="color: var(--clr-text-primary); margin-bottom: 25px;">Our Mission</h2>
            <p>
                CycleSync was born from a simple idea: making campus transportation sustainable, accessible, and smart.
                We believe that every student and staff member should have the freedom to move quickly across campus
                without the carbon footprint of a motor vehicle.
            </p>

            <div class="about-grid">
                <div class="feature-box">
                    <i class="fa-solid fa-leaf"></i>
                    <h3>Eco-Friendly</h3>
                    <p>Reducing campus emissions by encouraging bicycle use for the short trips.</p>
                </div>
                <div class="feature-box">
                    <i class="fa-solid fa-bolt"></i>
                    <h3>Instant Access</h3>
                    <p>Find, unlock, and ride a bike in seconds using our seamless digital platform.</p>
                </div>
                <div class="feature-box">
                    <i class="fa-solid fa-shield-halved"></i>
                    <h3>Safe & Secure</h3>
                    <p>Regular maintenance and real-time tracking ensure a safe riding experience.</p>
                </div>
            </div>

            <div style="margin-top: 60px; text-align: center;">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-lg">Back to Login</a>
            </div>
        </div>

        <footer style="padding: 40px; text-align: center; border-top: 1px solid var(--clr-border-light);">
            <p>&copy; 2026 CycleSync. All rights reserved.</p>
        </footer>
    </body>

    </html>