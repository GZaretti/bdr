<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel='stylesheet' href='../../bootstrap.css'>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create bus</title>
    </head>
    <body>
         <%@ include file="../menu.jsp" %>
        <div>
            <h1>Create bus</h1>
            <form:form action="/WebApplication2/app/bus/save" method="post">
                <table>
                    <input type="hidden" name="id" value="0"/>
                    <tr>
                        <td>N° matricule :</td>
                        <td><input name="noMatricule" value=""/></td>
                    </tr
                    <tr>
                        <td>Nombre de passagers max :</td>
                        <td><input name="nbPassagerMax" value=""/></td>
                    </tr>
                    <tr>
                        <td>Transport pour handicap :</td>
                        <td><input type="checkbox" name="transportHandicap" value="" /></td>
                    </tr>
                    <tr>
                        <td>Model :</td>
                        <td>

                            <select name="modelId">
                                <c:forEach items="${models}" var="mo">
                                    <option value="${mo.getId()}">${mo.getNom()}</option>
                                </c:forEach>
                            </select>
                        </td>


                    </tr>
                    <tr>
                        <td>Categories :</td>
                        <td>

                            <select name="categorieId">
                                <c:forEach items="${categories}" var="ca">
                                    <option value="${ca.getId()}">${ca.getNom()}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Les places des bus ne sont pas gérer par l'application. L'application attribue ue place par défaut</td>
                    </tr> 

                    <tr>
                        <td colspan="2" ><input type="submit" value="save"></td>

                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>