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
                <form action=director_down/${task.id} method="post">
                    <input type="hidden" name=${task.id}/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Отправить на доработку</button>
                </form>
                <form action=director_deleted/${task.id} method="post">
                    <input type="hidden" name=${task.id}/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Принять</button>
                </form>
                <form method="get" enctype="multipart/form-data" action=director/${task.decisionTaskName}>
                    <input type="submit" value="скачать решение">
                </form>
            </td>
        </tr>
    </#list>
</table>
<br><br>
<div align="center">
    <form action="/director_new_task" method="post" enctype="multipart/form-data">
        <input name="name" placeholder="Введите название приказа:"/>
        <select name="owner">
            <option value="DIR">Директор</option>
        </select>
        <select name="executor">
            <option value="HEAD">Глава отдела</option>
            <option value="SPEC">Специалист</option>
        </select>
        <input type="file" name="taskFileName">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>
</div>
<br>
<dir align="center">
    <input type="button" value="на главную" onclick="window.location.href = 'main'">
</dir>
</body>
</html>