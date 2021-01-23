<!DOCTYPE html>
<html lang="en" xmlns:form="http://www.w3.org/1999/html" xmlns:input="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
</head>
<body>
<div>
    <a href="specialist">ЛК</a>
</div>
<div>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Sign out">
    </form>
</div>
<div align="center">
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="orderName" placeholder="Введите название приказа:"/>

        <select name="orderOwner">
            <option value="DIRECTOR">Директор</option>
            <option value="HEAD">Глава отдела</option>
        </select>

        <select name="orderExecutor">
            <option value="HEAD">Глава отдела</option>
            <option value="SPECIALIST">Специалист</option>
        </select>

        <input type="file" name="orderFile">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>
</div>
<table align="center" border="1" cellpadding="5">
    <tr>
        <th>Приказ</th>
        <th>От</th>
        <th>K</th>
        <th>Статус</th>
    </tr>
    <#list orders as order>
    <tr>
        <td>${order.orderName}</td>
        <td>${order.orderOwner}</td>
        <td>${order.orderExecutor}</td>
        <td>
            <form method="get" enctype="multipart/form-data" action=${order.orderFile}  >
                <input type="submit" value="get-file">
            </form>
        </td>
    </tr>
    </#list>
</table>
</body>
</html>