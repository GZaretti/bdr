<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>update model</title>
    </head>
    <body>
         <%@ include file="../menu.jsp" %>
        <div>
            <h1>Update model</h1>
            <form:form action="/WebApplication2/app/model/save" method="post">
                <table>
                    <input type="hidden" name="id" value="${model.getId()}"/>
                    <tr>
                        <td>Nom :</td>
                        <td><input name="nom" value="${model.getNom()}" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" ><input type="submit" value="save"></td>
                    </tr>
                </table>
            </form:form>
            <form action = "/WebApplication2/app/model/index" method = "Get">
                <input type = "submit" value = "cancel" />
            </form>
        </div>
    </body>
</html>