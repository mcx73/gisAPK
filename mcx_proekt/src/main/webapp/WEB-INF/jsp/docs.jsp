<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Документы</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div align="center">
    <h2>Документы</h2>
    <br>
    <br>
    <table>
        <thead>
        <th>
            ID
        </th>
        <th>
            USER
        </th>
        <th>
            NAME
        </th>
        <th>
            FILETYPE
        </th>
        <th>
            DELETE
        </th>
        <th>
            DOWNLOAD
        </th>
        </thead>
        <c:forEach items="${docum}" var="doc">
        <tr>
            <td>${doc.id}</td>
            <td>${doc.authorName}</td>
            <td>${doc.filename}</td>
        <c:if test="${doc.filename!= null}">
            <td align="center"><img src=${doc.icon} width="20" height="20" /></td>
        </c:if>
            <td>
                <form action="/docs/delete" method="post">
                    <input type="hidden" name="docId" value="${doc.id}"/>
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit" >Удалить</button>
                </form>
            </td>
            <td>
                <form action="/downloadfile" method="post">
                    <input type="hidden" name="docId" value="${doc.id}"/>
                    <button type="submit" >Скачать</button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
    <br>
    <form method="POST" enctype="multipart/form-data">
        <input type="file" name="file">
        <button type="submit">добавить</button>
    </form>
    <br>
    <a href="/">Главная</a>
</div>
</body>

</html>
