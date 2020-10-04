<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>

<html>

    <body>
        <%@ include file="../menu.jsp" %>
        <h1>Liste des maintenances!</h1>

        <p>Créer un article <a href="create" > ici <a> !</p>
                    <form:form action="/WebApplication2/app/mecanicien/index" method="post">
                        <table>
                            <tr>
                                <td>rechercher :</td>
                                <td><input name="recherche" value=""/></td>
                            </tr>
                            <tr>
                                <td colspan="2" ><input type="submit" value="rechercher"></td> 
                            </tr>
                        </table>
                    </form:form>     

                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Matricule</th>
                                <th>Model</th>
                                <th>Categorie</th>
                                <th>Max passager</th>   
                                <th>Pour handicap</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${maintenances}" var="mo">


                                <tr>
                                    <td>${mo.getNoMatricule()}</td>
                                    <td>${mo.getModel().getNom()}</td>
                                    <td>${mo.getCategorie().getNom()}</td>
                                    <td>${mo.getNbPassagerMax()}</td>
                                    <td>${mo.getHandicap()}</td>
                                    <td><a href="update/${mo.getId()}" > update </a> &nbsp; &nbsp; <a href="delete/${mo.getId()}" > delete </a> &nbsp; &nbsp; <a href="details/${mo.getId()}" > detai </a></td>
                                </tr>


                            </c:forEach>
                        </tbody>
                    </table>

                    </body>
                    </html>
