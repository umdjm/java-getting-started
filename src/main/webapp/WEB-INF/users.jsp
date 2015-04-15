<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>

    <table>
        <tr>
            <th>ID</th>
            <th>Username</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.username}"/></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>