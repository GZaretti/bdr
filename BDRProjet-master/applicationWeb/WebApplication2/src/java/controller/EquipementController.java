/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IEquipement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.MapperFactory;
import service.EquipementService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author ZEED
 */
@RequestMapping("/equipement")
@Controller
public class EquipementController {

    private EquipementService equipementSercice;

    public EquipementController() {
        equipementSercice = new EquipementService(MapperFactory.createMapperManager());
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws PersistenceException {

        ModelAndView equipementAndView = new ModelAndView("equipement/index");
        equipementAndView.addObject("mods", equipementSercice.getEquipements());

        return equipementAndView;
    }
    
        @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView indexRecherche(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {

        ModelAndView equipementAndView = new ModelAndView("equipement/index");
        equipementAndView.addObject("mods", equipementSercice.getEquipements(request.getParameter("nom")));

        return equipementAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("equipement/create");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {
        
        
        if (Long.parseLong(request.getParameter("id")) == 0 ) {
            IEquipement equipement = gza.transportPublic.domain.Equipement.builder()
                    .nom(request.getParameter("nom"))
                    .information(request.getParameter("description")).build();
            equipementSercice.create(equipement);
        } else {
            IEquipement equipement = gza.transportPublic.domain.Equipement.builder()
                .nom(request.getParameter("nom"))
                    .id(Long.parseLong(request.getParameter("id")))
                    .information(request.getParameter("description")).build();
            equipementSercice.update(equipement);
        }

        ModelAndView equipementAndView = new ModelAndView("redirect:/app/equipement/index");
        equipementAndView.addObject("mods", equipementSercice.getEquipements());

        return equipementAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable long id) throws PersistenceException {
        ModelAndView equipementAndView = new ModelAndView("equipement/update");
        IEquipement equipement = equipementSercice.getById(id);
        equipementAndView.addObject("equipement", equipement);
        return equipementAndView;
    }


    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView equipementAndView = new ModelAndView("equipement/delete");
        equipementAndView.addObject("id", id);
        return equipementAndView;

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteOk(@PathVariable long id) throws PersistenceException {
        equipementSercice.deleteById(id);
        ModelAndView equipementAndView = new ModelAndView("redirect:/app/equipement/index");
        equipementAndView.addObject("mods", equipementSercice.getEquipements());
        return equipementAndView;
    }
}
