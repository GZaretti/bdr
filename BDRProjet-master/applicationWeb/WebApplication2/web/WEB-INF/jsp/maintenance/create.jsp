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
        <title>Create maintenance</title>
    </head>
    <body>
         <%@ include file="../menu.jsp" %>
        <div>
            <h1>Create maintenance</h1>
            <form:form action="/WebApplication2/app/mecanicien/save" method="post">
                <table>
                    <input type="hidden" name="id" value="0"/>
                    <tr>
                        <td>Bus :</td>
                        <td>

                            <select name="busId">
                                <c:forEach items="${bus}" var="mo">
                                    <option value="${mo.getId()}">${mo.getNoMatricule()} ${mo.getModel().getNom()} ${mo.getCategorie().getNom()} </option>
                                    </c:forEach>
                            </select>
                        </td>


                    </tr>
                    <tr>
                        <td>Mechanicien :</td>
                        <td>

                            <select name="mecanicienId">
                                <c:forEach items="${mecaniciens}" var="ca">
                                    <option value="${ca.getId()}">${ca.getNoCertification()} ${ca.getNom()} ${ca.getPrenom()}</option>
                                    </c:forEach>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>Date de début :</td>
                        <td>

                            <input type="date" name="dateDebut" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>Date de fin :</td>
                        <td>
                            <input type="date" name="dateFin" value=""/>
                        </td>   
                    </tr> 
                     <tr>
                        <td>commentaires:</td>
                        <td>
                            <input type="texte" name="commentaires" value=""/>
                        </td>   
                    </tr> 

                    <tr>
                        <td colspan="2" ><input type="submit" value="save"></td>

                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>