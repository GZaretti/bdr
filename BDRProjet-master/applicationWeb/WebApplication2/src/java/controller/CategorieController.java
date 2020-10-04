/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.ICategorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.MapperFactory;
import service.CategorieService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author ZEED
 */
@RequestMapping("/categorie")
@Controller
public class CategorieController {

    private CategorieService categorieSercice;

    public CategorieController() {
        categorieSercice = new CategorieService(MapperFactory.createMapperManager());
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws PersistenceException {

        ModelAndView categorieAndView = new ModelAndView("categorie/index");
        categorieAndView.addObject("mods", categorieSercice.getCategories());

        return categorieAndView;
    }
    
        @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView indexRecherche(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {

        ModelAndView categorieAndView = new ModelAndView("categorie/index");
        categorieAndView.addObject("mods", categorieSercice.getCategories(request.getParameter("nom")));

        return categorieAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("categorie/create");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {
        
        
        if (Long.parseLong(request.getParameter("id")) == 0 ) {
            ICategorie categorie = gza.transportPublic.domain.Categorie.builder()
                    .nom(request.getParameter("nom")).build();
            categorieSercice.create(categorie);
        } else {
            ICategorie categorie = gza.transportPublic.domain.Categorie.builder()
                .nom(request.getParameter("nom"))
                    .id(Long.parseLong(request.getParameter("id"))).build();
            categorieSercice.update(categorie);
        }

         ModelAndView categorieAndView = new ModelAndView("redirect:/app/categorie/index");
        categorieAndView.addObject("mods", categorieSercice.getCategories());
        return categorieAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable long id) throws PersistenceException {
        ModelAndView categorieAndView = new ModelAndView("categorie/update");
        ICategorie categorie = categorieSercice.getById(id);
        categorieAndView.addObject("categorie", categorie);
        return categorieAndView;
    }

    /*@RequestMapping(value = "delete", method = RequestMethod.GET, params = "id")
    public CategorieAndView delete(@RequestParam("id") int id) {
        CategorieAndView categorieAndView = new CategorieAndView("delete");
        categorieAndView.addObject("id", id);
        return categorieAndView;

    }*/
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView categorieAndView = new ModelAndView("categorie/delete");
        categorieAndView.addObject("id", id);
        return categorieAndView;

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteOk(@PathVariable long id) throws PersistenceException {
        categorieSercice.deleteById(id);
          ModelAndView categorieAndView = new ModelAndView("redirect:/app/categorie/index");
        categorieAndView.addObject("mods", categorieSercice.getCategories());
        return categorieAndView;
    }
}
