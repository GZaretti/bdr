/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IBus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.MapperFactory;
import service.BusService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import service.CategorieService;
import service.ModelService;

/**
 *
 * @author ZEED
 */
@RequestMapping("/bus")
@Controller
public class BusController {

    private BusService busSercice;
    private ModelService modelService;
    private CategorieService categorieSercice;

    public BusController() {
        IMapperManager mapperManager = MapperFactory.createMapperManager();
        busSercice = new BusService(mapperManager);
        modelService = new ModelService(mapperManager);
        categorieSercice = new CategorieService(MapperFactory.createMapperManager());
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws PersistenceException {

        ModelAndView busAndView = new ModelAndView("bus/index");
        busAndView.addObject("categories", modelService.getModels());
        busAndView.addObject("models", modelService.getModels());
        busAndView.addObject("buss", busSercice.getBuss());
        return busAndView;
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView indexRecherche(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {

        ModelAndView busAndView = new ModelAndView("bus/index");
        busAndView.addObject("models", busSercice.getBuss(request.getParameter("nom")));

        return busAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() throws PersistenceException {
        ModelAndView busAndView = new ModelAndView("bus/create");
        busAndView.addObject("categories", categorieSercice.getCategories());
        busAndView.addObject("models", modelService.getModels());

        return busAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {

        String chkb = request.getParameter("transportHandicap");
        boolean chkblean = false;

        if (chkb != null) {
            chkblean = true;
        };

        if (Long.parseLong(request.getParameter("id")) == 0) {
            IBus bus;
            bus = gza.transportPublic.domain.Bus.builder()
                    .noMatricule(request.getParameter("noMatricule"))
                    .model(modelService.getById(Long.parseLong(request.getParameter("modelId"))))
                    .categorie(categorieSercice.getById(Long.parseLong(request.getParameter("categorieId"))))
                    .handicap(chkblean)
                    .nbPassagerMax(Integer.parseInt(request.getParameter("nbPassagerMax")))
                    .build();
            System.out.println(bus.toString());
            busSercice.create(bus);
        } else {
            IBus bus;
            bus = gza.transportPublic.domain.Bus.builder()
                    .id(Long.parseLong(request.getParameter("id")))
                    .noMatricule(request.getParameter("noMatricule"))
                    .model(modelService.getById(Long.parseLong(request.getParameter("modelId"))))
                    .categorie(categorieSercice.getById(Long.parseLong(request.getParameter("categorieId"))))
                    .handicap(chkblean)
                    .nbPassagerMax(Integer.parseInt(request.getParameter("nbPassagerMax")))
                    .build();
            System.out.println(bus.toString());
            busSercice.update(bus);
        }

        ModelAndView busAndView = new ModelAndView("redirect:/app/bus/index");
        busAndView.addObject("mods", busSercice.getBuss());

        return busAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable long id) throws PersistenceException {
        ModelAndView busAndView = new ModelAndView("bus/update");
        IBus bus = busSercice.getById(id);
        busAndView.addObject("categories", categorieSercice.getCategories());
        busAndView.addObject("models", modelService.getModels());
        busAndView.addObject("bus", bus);
        return busAndView;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView busAndView = new ModelAndView("bus/delete");
        busAndView.addObject("id", id);
        return busAndView;

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteOk(@PathVariable long id) throws PersistenceException {
        busSercice.deleteById(id);
        ModelAndView busAndView = new ModelAndView("redirect:/app/bus/index");
        busAndView.addObject("mods", busSercice.getBuss());
        return busAndView;
    }
}
