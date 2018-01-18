<jsp:directive.page contentType="text/html; charset=Utf-8" isELIgnored="false" isErrorPage="true"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Status Code:${pageContext.errorData.statusCode}
Message:${pageContext.exception.message}
${pageContext.exception.printStackTrace()}
</body>
</html>
