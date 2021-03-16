<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="Shortcut Icon" type="image/x-icon" href="/favicon.ico">
</head>
<body>
<dir align="center">
    <input type="button" value="Для директора" onclick="window.location.href = 'director'">
    <input type="button" value="Для главы отдела" onclick="window.location.href = 'head'">
    <input type="button" value="Для специалиста" onclick="window.location.href = 'spec'">
</dir>
<br><br>
<div align="center">
    <h2>Проверка подписи ${check}</h2>
<form method="post" action="/main" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit">Добавить</button>
</form>
</div>
<br><br>
<dir align="center">
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Выйти">
    </form>
</dir>
</body>
</html>