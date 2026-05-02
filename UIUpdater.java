import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class UIUpdater {
    public static void main(String[] args) throws Exception {
        String baseDir = "C:/Users/Acer/Downloads/CycleSync/src/main/webapp";
        
        // 1. Process CSS files
        updateCss(baseDir + "/css/style.css");
        updateCss(baseDir + "/css/dashboard.css");
        updateCss(baseDir + "/css/auth.css");
        
        // 2. Process JSPs
        Files.walk(Paths.get(baseDir, "WEB-INF", "pages")).filter(Files::isRegularFile).forEach(p -> {
            if (p.toString().endsWith(".jsp")) {
                try {
                    updateJsp(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        // What about index.jsp?
        updateJsp(Paths.get(baseDir, "index.jsp"));
        
        System.out.println("Update complete!");
    }
    
    static void updateCss(String path) throws Exception {
        Path p = Paths.get(path);
        if (!Files.exists(p)) return;
        String content = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
        
        if (path.endsWith("style.css")) {
            content = content.replace("--shadow-xs:  0 1px 3px rgba(0,0,0,.06);", "--shadow-xs:  0 1px 2px rgba(0, 0, 0, 0.04);");
            content = content.replace("--shadow-sm:  0 2px 8px rgba(0,0,0,.08);", "--shadow-sm:  0 4px 12px rgba(0, 0, 0, 0.03), 0 1px 3px rgba(0, 0, 0, 0.05);");
            content = content.replace("--shadow-md:  0 4px 16px rgba(0,0,0,.1);", "--shadow-md:  0 8px 24px rgba(0, 0, 0, 0.04), 0 2px 8px rgba(0, 0, 0, 0.04);");
            content = content.replace("--shadow-lg:  0 8px 32px rgba(0,0,0,.12);", "--shadow-lg:  0 16px 48px rgba(0, 0, 0, 0.06), 0 4px 16px rgba(0, 0, 0, 0.04);");
            
            content = content.replace("transform: translateY(-2px);", "/* transform removed for natural feel */");
            content = content.replace("transform: translateY(-1px);", "/* transform removed */");
            content = content.replace("transform: translateY(-3px);", "/* transform removed */");
            
            content = content.replace("width: 20px;", "width: 20px; height: 20px; display: flex; align-items: center; justify-content: center;");
            if (!content.contains(".nav-icon svg")) {
                content = content.replace(".nav-icon {", ".nav-icon svg { width: 100%; height: 100%; stroke-width: 2px; }\n.nav-icon {");
            }
            if (!content.contains(".alert-icon svg")) {
                content += "\n.alert-icon svg { width: 20px; height: 20px; }\n";
                content += "\n.sidebar-brand-icon svg { width: 24px; height: 24px; color: #fff; }\n";
                content += "\n.sidebar-brand-icon .fa-bicycle { font-size: 1.2rem; color: #fff; }\n";
            }
            if (!content.contains(".empty-state-icon svg")) {
                content += "\n.empty-state-icon svg { width: 48px; height: 48px; margin: 0 auto; }\n";
            }
        }
        
        if (path.endsWith("dashboard.css")) {
            content = content.replace("font-size: 2.4rem;", "width: 42px; height: 42px; display: flex; align-items: center; justify-content: center;");
            if (!content.contains(".bike-card-emoji svg")) {
                content += "\n.bike-card-emoji svg { width: 100%; height: 100%; stroke-width: 1.5px; opacity: 0.9; color: #fff; }\n";
                content += "\n.bike-card-emoji .fa-bicycle { font-size: 2rem; color: #fff; }\n";
            }
            if (!content.contains(".stat-icon svg")) {
                content += "\n.stat-icon svg { width: 24px; height: 24px; stroke-width: 2px; }\n";
                content += "\n.stat-icon .fa-bicycle { font-size: 1.2rem; }\n";
            }
            if (!content.contains(".fine-summary-icon svg")) {
                content += "\n.fine-summary-icon svg { width: 32px; height: 32px; stroke-width: 1.5px; color: var(--clr-danger); }\n";
            }
        }
        
        Files.write(p, content.getBytes(StandardCharsets.UTF_8));
    }
    
    static void updateJsp(Path p) throws Exception {
        if (!Files.exists(p)) return;
        String content = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
        
        // Emojis to Lucide/FA
        content = content.replace("🏠", "<i data-lucide=\"layout-dashboard\"></i>");
        content = content.replace("🚲", "<i class=\"fa-solid fa-bicycle\"></i>");
        content = content.replace("👥", "<i data-lucide=\"users\"></i>");
        content = content.replace("💰", "<i data-lucide=\"wallet\"></i>");
        content = content.replace("🔍", "<i data-lucide=\"search\"></i>");
        content = content.replace("🕐", "<i data-lucide=\"clock\"></i>");
        content = content.replace("🚪", "<i data-lucide=\"log-out\"></i>");
        content = content.replace("🟢", "<span style=\"display:inline-block;width:8px;height:8px;background:#3dba6f;border-radius:50%;\"></span>");
        content = content.replace("✅", "<i data-lucide=\"check-circle-2\"></i>");
        content = content.replace("⚠️", "<i data-lucide=\"alert-triangle\"></i>");
        content = content.replace("📭", "<i data-lucide=\"inbox\"></i>");
        content = content.replace("🔄", "<i data-lucide=\"arrow-left-right\"></i>");
        content = content.replace("🔧", "<i data-lucide=\"wrench\"></i>");
        content = content.replace("📋", "<i data-lucide=\"clipboard-list\"></i>");
        content = content.replace("⚡", "<i data-lucide=\"zap\"></i>");
        content = content.replace("🚴", "<i class=\"fa-solid fa-person-biking auth-hero-icon\" style=\"font-size:4.5rem; color: #fff; margin-bottom: 24px; display: block;\"></i>");
        content = content.replace("✉️", "<i data-lucide=\"mail\"></i>");
        content = content.replace("🔒", "<i data-lucide=\"lock\"></i>");
        content = content.replace("👤", "<i data-lucide=\"user\"></i>");
        content = content.replace("📱", "<i data-lucide=\"smartphone\"></i>");
        content = content.replace("📍", "<i data-lucide=\"map-pin\"></i>");
        content = content.replace("➕", "<i data-lucide=\"plus\"></i>");
        content = content.replace("✏️", "<i data-lucide=\"edit-2\"></i>");
        content = content.replace("🗑️", "<i data-lucide=\"trash-2\"></i>");
        content = content.replace("💳", "<i data-lucide=\"credit-card\"></i>");
        content = content.replace("ℹ️", "<i data-lucide=\"info\"></i>");
        content = content.replace("⚙️", "<i data-lucide=\"settings\"></i>");
        content = content.replace("🛡️", "<i data-lucide=\"shield\"></i>");
        content = content.replace("🛑", "<i data-lucide=\"slash\"></i>");
        
        // Inject CDNs and script if it's a full HTML page
        if (content.contains("</head>") && !content.contains("lucide@latest")) {
            String headInject = "    <!-- Icons -->\n    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css\">\n    <script src=\"https://unpkg.com/lucide@latest\"></script>\n</head>";
            content = content.replace("</head>", headInject);
        }
        
        if (content.contains("</body>") && !content.contains("lucide.createIcons()")) {
            String scriptInject = "\n<script>\n    document.addEventListener(\"DOMContentLoaded\", function() {\n        if (typeof lucide !== 'undefined') {\n            lucide.createIcons();\n        }\n    });\n</script>\n</body>";
            content = content.replace("</body>", scriptInject);
        }
        
        Files.write(p, content.getBytes(StandardCharsets.UTF_8));
    }
}
