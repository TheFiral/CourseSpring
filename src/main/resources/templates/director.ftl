<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Director</title>
</head>
<body>
<div align="right">
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
<#--                <form method="get" enctype="multipart/form-data" action=${order.orderFile}  >-->
<#--                    <input type="submit" value="скачать приказ">-->
<#--                </form>-->
                <form method="get" enctype="multipart/form-data" action=${order.orderDecision}>
                    <input type="submit" value="скачать решеиние">
                </form>
            </td>
        </tr>
    </#list>
</table>
<br>
<div align="center">
    <form action="/dircheak" method="post">
        <input type="text" name="orderName" placeholder="Введите название приказа:"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Отправить на доработку</button>
    </form>
</div>
<br>
<div align="center">
    <form action="/direnter" method="post">
        <input type="text" name="orderName" placeholder="Введите название приказа:"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Принять решение.</button>
    </form>
</div>
</body>
</html>