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
                        <td>N° certification :</td>
                        <td><input name="noCertification" value="${mecanicien.getNoCertification()}"/></td>
                    </tr
                    <tr>
                        <td>Nom</td>
                        <td><input name="nom" value="${mecanicien.getNom()}"/></td>
                    </tr>
                    <tr>
                        <td>Prenom</td>
                        <td><input name="prenom" value="${mecanicien.getPrenom()}"/></td>
                    </tr>
                    <tr>
                        <td>date embauche</td>
                        <td><input name="dateEmbauche" value="${mecanicien.getDateEmbauche()}"/></td>
                    </tr>
                    <tr>
                        <td>Pourcentage</td>
                        <td><input name="pourcentage" value="${mecanicien.getPourcentage()}"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" ><input type="submit" value="save"></td>
                    </tr>


                </table>
            </form:form>

        </div>
    </body>
</html>
