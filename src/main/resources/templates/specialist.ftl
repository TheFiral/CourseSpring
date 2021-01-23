<!DOCTYPE html>
<html lang="en" xmlns:input="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Specialist</title>
</head>
<body>
<div align="right">
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Sign out">
    </form>
</div>
<table align="center" border="1" cellpadding="5">
    <tr>
        <th>Приказ</th>
        <th>От</th>
        <th>Статус</th>
        <th>Action</th>
    </tr>
    <#list orders as order>
        <tr>
            <td>${order.orderName}</td>
            <td>${order.orderOwner}</td>
            <td>${order.status}</td>
            <td>
                <form method="get" enctype="multipart/form-data" action=${order.orderFile}  >
                    <input type="submit" value="скачать приказ">
                </form>
            </td>
        </tr>
    </#list>
</table>
<div align="center">
<form method="post" enctype="multipart/form-data">
    <input type="text" name="orderName" placeholder="Введите название приказа:"/>
    <input type="file" name="orderDecision">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit">Загрузка решения</button>
</form>
</div>
</body>
</html>