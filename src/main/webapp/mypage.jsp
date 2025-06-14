<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.File, java.net.URLEncoder" %>

<html>
    <head>
        <title>File Explorer</title>
    </head>
    <body>
        <%
            String dirPath = (String) request.getAttribute("dirPath");
            String currentTime = (String) request.getAttribute("currentTime");
            String parentPath = (String) request.getAttribute("parentPath");
            File[] files = (File[]) request.getAttribute("files");
        %>

        <h2>Welcome, ${username}</h2>
        <p>Page generated on: <%= currentTime %></p>

        <form action="logout" method="get">
            <button>Logout</button>
        </form>

        <h3>
            <% if (dirPath == null) { %>
                Available Disks:
            <% } else { %>
                Contents of Directory: <%= dirPath %>
            <% } %>
        </h3>



        <% if (parentPath != null) {
               String encodedParent = URLEncoder.encode(parentPath, "UTF-8");
        %>
            <p><a href="?path=<%= encodedParent %>">Go Up</a></p>
        <% } else if (dirPath != null) { %>
            <p><a href="?">Back to Disk List</a></p>
        <% } %>

        <ul>
            <% if (files != null) {
                   for (File file : files) {
                       String fileName = file.getName().isEmpty() ? file.getAbsolutePath() : file.getName();
                       String absolutePath = file.getAbsolutePath();
                       String encodedPath = URLEncoder.encode(absolutePath, "UTF-8");
            %>
                <li>
                    <% if (file.isDirectory()) { %>
                        <a href="?path=<%= encodedPath %>"><%= fileName %>/</a>
                    <% } else { %>
                        <a href="download?filePath=<%= encodedPath %>"><%= fileName %></a>
                    <% } %>
                </li>
            <%     }
               } else {
            %>
                <li>No files or directories found.</li>
            <% } %>
        </ul>
    </body>
</html>