/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IMecanicien;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.MapperFactory;
import service.MecanicienService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import service.CategorieService;
import service.ModelService;

/**
 *
 * @author ZEED
 */
@RequestMapping("/mecanicien")
@Controller
public class MecancienController {

    private MecanicienService mecanicienSercice;
    private ModelService modelService;
    private CategorieService categorieSercice;

    public MecancienController() {
        IMapperManager mapperManager = MapperFactory.createMapperManager();
        mecanicienSercice = new MecanicienService(mapperManager);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws PersistenceException {

        ModelAndView mecanicienAndView = new ModelAndView("mecanicien/index");
        mecanicienAndView.addObject("mecaniciens", mecanicienSercice.getMecaniciens());
        return mecanicienAndView;
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView indexRecherche(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {

        ModelAndView mecanicienAndView = new ModelAndView("mecanicien/index");
        mecanicienAndView.addObject("mecaniciens", mecanicienSercice.getMecaniciens(request.getParameter("recherche")));

        return mecanicienAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() throws PersistenceException {
        ModelAndView mecanicienAndView = new ModelAndView("mecanicien/create");
        return mecanicienAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws PersistenceException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        if (Long.parseLong(request.getParameter("id")) == 0) {
            IMecanicien mecanicien;
            mecanicien = gza.transportPublic.domain.Mecanicien.builder()
                    .noCertification(request.getParameter("noCertification"))
                    .nom(request.getParameter("nom"))
                    .prenom(request.getParameter("prenom"))
                    .dateEmbauche(sdf.parse(request.getParameter("dateEmbauche")))
                    .pourcentage(Integer.parseInt(request.getParameter("pourcentage")))
                    .build();
            mecanicienSercice.create(mecanicien);
        } else {
            IMecanicien mecanicien;
            mecanicien = gza.transportPublic.domain.Mecanicien.builder()
                    .id(Long.parseLong(request.getParameter("id")))
                    .noCertification(request.getParameter("noCertification"))
                    .nom(request.getParameter("nom"))
                    .prenom(request.getParameter("prenom"))
                    .dateEmbauche(sdf.parse(request.getParameter("dateEmbauche")))
                    .pourcentage(Integer.parseInt(request.getParameter("pourcentage")))
                    .build();
            mecanicienSercice.update(mecanicien);
        }

        ModelAndView mecanicienAndView = new ModelAndView("redirect:/app/mecanicien/index");
        mecanicienAndView.addObject("mecanos", mecanicienSercice.getMecaniciens());

        return mecanicienAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable long id) throws PersistenceException {
        ModelAndView mecanicienAndView = new ModelAndView("mecanicien/update");
        IMecanicien mecanicien = mecanicienSercice.getById(id);
        mecanicienAndView.addObject("mecanicien", mecanicien);
        return mecanicienAndView;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView mecanicienAndView = new ModelAndView("mecanicien/delete");
        mecanicienAndView.addObject("id", id);
        return mecanicienAndView;

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteOk(@PathVariable long id) throws PersistenceException {
        mecanicienSercice.deleteById(id);
        ModelAndView mecanicienAndView = new ModelAndView("redirect:/app/mecanicien/index");
        mecanicienAndView.addObject("mecanos", mecanicienSercice.getMecaniciens());
        return mecanicienAndView;
    }
}
