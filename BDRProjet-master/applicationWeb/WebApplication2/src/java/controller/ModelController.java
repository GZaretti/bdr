/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.MapperFactory;
import service.ModelService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author ZEED
 */
@RequestMapping("/model")
@Controller
public class ModelController {

    private ModelService modelSercice;

    public ModelController() {
        modelSercice = new ModelService(MapperFactory.createMapperManager());
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws PersistenceException {

        ModelAndView modelAndView = new ModelAndView("model/index");
        modelAndView.addObject("mods", modelSercice.getModels());

        return modelAndView;
    }
    
        @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView indexRecherche(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {

        ModelAndView modelAndView = new ModelAndView("model/index");
        modelAndView.addObject("mods", modelSercice.getModels(request.getParameter("nom")));

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("model/create");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {
        
        
        if (Long.parseLong(request.getParameter("id")) == 0 ) {
            IModel model = gza.transportPublic.domain.Model.builder()
                    .nom(request.getParameter("nom")).build();
            modelSercice.create(model);
        } else {
            IModel model = gza.transportPublic.domain.Model.builder()
                .nom(request.getParameter("nom"))
                    .id(Long.parseLong(request.getParameter("id"))).build();
            modelSercice.update(model);
        }

         ModelAndView modelAndView = new ModelAndView("redirect:/app/model/index");
        modelAndView.addObject("mods", modelSercice.getModels());
        return modelAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable long id) throws PersistenceException {
        ModelAndView modelAndView = new ModelAndView("model/update");
        IModel model = modelSercice.getById(id);
        modelAndView.addObject("model", model);
        return modelAndView;
    }

    /*@RequestMapping(value = "delete", method = RequestMethod.GET, params = "id")
    public ModelAndView delete(@RequestParam("id") int id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("id", id);
        return modelAndView;

    }*/
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("model/delete");
        modelAndView.addObject("id", id);
        return modelAndView;

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteOk(@PathVariable long id) throws PersistenceException {
        modelSercice.deleteById(id);
          ModelAndView modelAndView = new ModelAndView("redirect:/app/model/index");
        modelAndView.addObject("mods", modelSercice.getModels());
        return modelAndView;
    }
}
