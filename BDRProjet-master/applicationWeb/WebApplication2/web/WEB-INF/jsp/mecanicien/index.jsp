<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel='stylesheet' href='../../bootstrap.css'>
<!DOCTYPE html>

<html>

    <body>
        <%@ include file="../menu.jsp" %>
        <h1>Liste des mecaniciens!</h1>

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
                                <th>N° certification</th>
                                <th>Nom</th>
                                <th>Prenom</th>
                                <th>Date embauche</th>   
                                <th>PRCtage</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${mecaniciens}" var="mo">


                                <tr>
                                    <td>${mo.getNoCertification()}</td>
                                    <td>${mo.getNom()}</td>
                                    <td>${mo.getPrenom()}</td>
                                    <td>${mo.getDateEmbauche()}</td>
                                    <td>${mo.getPourcentage()}</td>
                                    <td><a href="update/${mo.getId()}" > update </a> &nbsp; &nbsp; <a href="delete/${mo.getId()}" > delete </a> &nbsp; &nbsp; <a href="details/${mo.getId()}" > detai </a></td>
                                </tr>


                            </c:forEach>
                        </tbody>
                    </table>

                    </body>
                    </html>
