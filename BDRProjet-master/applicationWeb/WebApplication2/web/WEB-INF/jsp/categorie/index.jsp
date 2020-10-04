<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel='stylesheet' href='../../bootstrap.css'>
<!DOCTYPE html>

<html>

    <body>
        <%@ include file="../menu.jsp" %>
        <h1>Liste des categories!</h1>

        <p>Créer un article <a href="create" > ici <a> !</p>
                    <form:form action="/WebApplication2/app/categorie/index" method="post">
                        <table>
                            <tr>
                                <td>rechercher :</td>
                                <td><input name="nom" value=""/></td>
                            </tr>
                            <tr>
                                <td colspan="2" ><input type="submit" value="rechercher"></td> 
                            </tr>
                        </table>
                    </form:form>     

                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>modèle</th>
                                <th>action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${mods}" var="mo">


                                <tr>
                                    <td>${mo.getNom()}</td>
                                    <td><a href="update/${mo.getId()}" > update </a> &nbsp; <a href="delete/${mo.getId()}" > delete </a> </td>
                                </tr>


                            </c:forEach>
                        </tbody>
                    </table>

                    </body>
                    </html>
