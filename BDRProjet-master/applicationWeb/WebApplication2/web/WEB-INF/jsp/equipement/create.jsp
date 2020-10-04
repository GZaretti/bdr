<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link rel='stylesheet' href='../../bootstrap.css'>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create equipement</title>
</head>
<body>
     <%@ include file="../menu.jsp" %>
    <div>
        <h1>Create equipement</h1>
        <form:form action="/WebApplication2/app/equipement/save" method="post">
        <table>
            <input type="hidden" name="id" value="0"/>
            <tr>
                <td>Nom :</td>
                <td><input name="nom" value=""/></td>
            </tr
             <tr>
                <td>Description :</td>
                <td><input name="description" value=""/></td>
            </tr>
            <tr>
                <td colspan="2" ><input type="submit" value="save"></td>
                
            </tr>
        </table>
        </form:form>
    </div>
</body>
</html>