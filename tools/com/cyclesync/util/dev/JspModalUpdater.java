package com.cyclesync.util.dev;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

/**
 * JspModalUpdater — One-time dev utility that patches all JSP files to:
 *  1. Inject modal.js script tag before </body>.
 *  2. Replace inline onclick="return confirm(...)" with data-confirm="..." attributes
 *     so the custom modal system handles confirmations instead of browser dialogs.
 *  3. Add logout-confirm attribute to the sidebar logout link.
 *
 * NOT a servlet — run as a standalone Java program from the project root.
 */
public class JspModalUpdater {

    private static final String BASE_DIR =
        "C:/Users/Acer/Downloads/CycleSync/src/main/webapp";

    public static void main(String[] args) throws Exception {
        Files.walk(Paths.get(BASE_DIR))
             .filter(Files::isRegularFile)
             .forEach(p -> {
                 if (p.toString().endsWith(".jsp")) {
                     try { updateJsp(p); } catch (Exception e) { e.printStackTrace(); }
                 }
             });

        System.out.println("Modal integration complete!");
    }

    static void updateJsp(Path p) throws Exception {
        String content = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
        boolean changed = false;

        // 1. Inject modal.js before </body> if not already present
        if (content.contains("</body>") && !content.contains("modal.js")) {
            content = content.replace("</body>",
                "<script src=\"${pageContext.request.contextPath}/js/modal.js\"></script>\n</body>");
            changed = true;
        }

        // 2. Replace confirm calls with data-confirm
        // Catches: onclick="return confirm('...')"  and onsubmit="return confirm('...')"
        String[] patterns = {
            "onclick=\"return confirm\\('([^']+)'\\);?\"",
            "onsubmit=\"return confirm\\('([^']+)'\\);?\"",
            "onclick='return confirm\\(\"([^\"]+)\"\\);?'",
            "onsubmit='return confirm\\(\"([^\"]+)\"\\);?'"
        };
        
        for (String pattern : patterns) {
            if (content.contains("confirm(")) {
                String newContent = content.replaceAll(pattern, "data-confirm=\"$1\"");
                if (!newContent.equals(content)) {
                    content = newContent;
                    changed = true;
                }
            }
        }

        // 3. Add confirm attribute to sidebar logout link if missing
        if (p.toString().endsWith("_sidebar.jsp")) {
            if (content.contains("href=\"${pageContext.request.contextPath}/logout\"")
                    && !content.contains("data-confirm")) {
                content = content.replace(
                    "href=\"${pageContext.request.contextPath}/logout\"",
                    "href=\"${pageContext.request.contextPath}/logout\" " +
                    "data-confirm=\"Are you sure you want to sign out?\" " +
                    "data-confirm-title=\"Sign Out\"");
                changed = true;
            }
        }

        if (changed) {
            Files.write(p, content.getBytes(StandardCharsets.UTF_8));
            System.out.println("Updated: " + p.getFileName());
        }
    }
}
