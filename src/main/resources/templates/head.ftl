<!DOCTYPE html>
<html lang="en" xmlns:form="http://www.w3.org/1999/html" xmlns:input="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Head</title>
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
        <th>К</th>
        <th>Статус</th>
        <th>Action</th>
    </tr>
    <#list orders as order>
        <tr>
            <td>${order.orderName}</td>
            <td>${order.orderOwner}</td>
            <td>${order.orderExecutor}</td>
            <td>${order.status}</td>
            <td>
                <form method="get" enctype="multipart/form-data" action=${order.orderDecision}>
                    <input type="submit" value="скачать решение">
                </form>
            </td>
        </tr>
    </#list>
</table>
<br>
<div align="center">
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="orderName" placeholder="Введите название приказа:"/>
        <input type="file" name="orderDecision">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Загрузка решения</button>
    </form>
</div>
<br>
<div align="center">
    <form action="/headcheak" method="post">
        <input type="text" name="orderName" placeholder="Введите название приказа:"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Отправить на доработку</button>
    </form>
</div>
<br>
<div align="center">
    <form action="/headfile" method="post" enctype="multipart/form-data">
        <input type="text" name="orderName" placeholder="Введите название приказа:"/>

        <select name="orderOwner">
            <option value="HEAD">Глава отдела</option>
        </select>

        <select name="orderExecutor">
            <option value="SPECIALIST">Специалист</option>
        </select>

        <input type="file" name="orderFile">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>
</div>
</body>
</html>