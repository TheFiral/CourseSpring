<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="Shortcut Icon" type="image/x-icon" href="/favicon.ico">
</head>
<body>
<table align="center" border="1" cellpadding="5">
    <tr>
        <th>Приказ</th>
        <th>Передал</th>
        <th>Принимает</th>
        <th>Action</th>
    </tr>
    <#list tasks as task>
        <tr>
            <td>${task.name}</td>
            <td>${task.owner}</td>
            <td>${task.executor}</td>
            <td>
                <form method="get" enctype="multipart/form-data" action=spec/${task.fileTaskName}>
                    <input type="submit" value="скачать задание">
                </form>
                <form method="post" enctype="multipart/form-data" action=spec/${task.id}>
                    <input type="hidden" name=${task.id}/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <input type="file" name="file">
                    <button type="submit">Загрузка решения</button>
                </form>
            </td>
        </tr>
    </#list>
</table>
<br><br>
<dir align="center">
    <input type="button" value="на главную" onclick="window.location.href = 'main'">
</dir>
</body>
</html>