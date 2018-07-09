<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<script language="javascript" type="text/javascript"

		src="Scripts/jquery-1.4.1.js"></script>
<script language="javascript" type="text/javascript"

		src="Scripts/jquery.tmpl.js"></script>
		
<script id="templateStructure" type="text/x-jquery-tmpl">
    <tr>
        <td>${ProductName}</td>
        <td>${ProductDescription}</td>
        <td>${Price}</td>
    </tr>
</script>

<table border="1" id="templateTable">
    <tr>
        <td><b>Product Name</b></td>
        <td><b>Product Description</b></td>
        <td><b>Price</b></td>   
    </tr>
</table>

</body>
</html>