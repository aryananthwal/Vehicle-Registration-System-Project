<c:choose>
    <c:when test="${empty vehicleList}">
        <p>No vehicles found.</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr><th>ID</th><th>Make</th><th>Model</th><th>Year</th><th>Reg Number</th></tr>
            <c:forEach var="vehicle" items="${vehicleList}">
                <tr>
                    <td>${vehicle.id}</td>
                    <td>${vehicle.make}</td>
                    <td>${vehicle.model}</td>
                    <td>${vehicle.year}</td>
                    <td>${vehicle.registrationNumber}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
