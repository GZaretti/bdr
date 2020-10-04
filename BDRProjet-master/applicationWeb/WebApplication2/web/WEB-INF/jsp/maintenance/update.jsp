<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>update mecanicien</title>
    </head>
    <body>
         <%@ include file="../menu.jsp" %>
        <div>
            <h1>Update mecanicien</h1>
            <form:form action="/WebApplication2/app/mecanicien/save" method="post">
                <table>
                    <input type="hidden" name="id" value="${mecanicien.getId()}"/>
                    <tr>
                        <td>N° matricule :</td>
                        <td><input name="noMatricule" value="${mecanicien.getNoMatricule()}"/></td>
                    </tr
                    <tr>
                        <td>Nombre de passagers max :</td>
                        <td><input name="nbPassagerMax" value="${mecanicien.getNbPassagerMax()}"/></td>
                    </tr>
                    <tr>
                        <td>Transport pour handicap :</td>
                        <td>
                            <input type="checkbox" name="transportHandicap" 
                                   <c:if test="${mecanicien.getHandicap()}">checked</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>Model :</td>
                        <td>

                            <select name="modelId">
                                <c:forEach items="${models}" var="mo">
                                    <option value="${mo.getId()}"
                                             <c:if test="${mecanicien.getModel().getId() == mo.getId()}">selected</c:if>
                                         
                                            >${mo.getNom()}</option>
                                </c:forEach>
                            </select>
                        </td>


                    </tr>
                    <tr>
                        <td>Categories :</td>
                        <td>

                            <select name="categorieId">
                                <c:forEach items="${categories}" var="ca">
                                    <option value="${ca.getId()}"
                                             <c:if test="${mecanicien.getCategorie().getId() == ca.getId()}">selected</c:if>
                                             >${ca.getNom()}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Les places des mecanicien ne sont pas gérer par l'application. L'application attribue ue place par défaut</td>
                    </tr>

                    <tr>
                        <td colspan="2" ><input type="submit" value="save"></td>
                    </tr>


                </table>
            </form:form>

        </div>
    </body>
</html>
