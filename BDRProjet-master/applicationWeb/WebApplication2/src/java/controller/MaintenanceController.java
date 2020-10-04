/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import gza.transportPublic.domain.Mecanicien;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IMaintenance;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.MapperFactory;
import service.MaintenanceService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import service.BusService;
import service.CategorieService;
import service.MecanicienService;
import service.ModelService;

/**
 *
 * @author ZEED
 */
@RequestMapping("/maintenance")
@Controller
public class MaintenanceController {

    private MaintenanceService maintenanceSercice;
    private BusService busService;
    private MecanicienService mecanicienSercice;

    public MaintenanceController() {
        IMapperManager mapperManager = MapperFactory.createMapperManager();
        maintenanceSercice = new MaintenanceService(mapperManager);
        mecanicienSercice = new MecanicienService(mapperManager);
        busService = new BusService(MapperFactory.createMapperManager());
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws PersistenceException {

        ModelAndView maintenanceAndView = new ModelAndView("maintenance/index");
        maintenanceAndView.addObject("mecanos", mecanicienSercice.getMecaniciens());
        maintenanceAndView.addObject("bus", busService.getBuss());
        maintenanceAndView.addObject("maintenances", maintenanceSercice.getMaintenances());
        return maintenanceAndView;
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView indexRecherche(HttpServletRequest request, HttpServletResponse response) throws PersistenceException {

        ModelAndView maintenanceAndView = new ModelAndView("maintenance/index");
        maintenanceAndView.addObject("maintenances", maintenanceSercice.getMaintenances(request.getParameter("rechercher")));

        return maintenanceAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() throws PersistenceException {
        ModelAndView maintenanceAndView = new ModelAndView("maintenance/create");
        maintenanceAndView.addObject("bus", busService.getBuss());
        maintenanceAndView.addObject("mecaniciens", mecanicienSercice.getMecaniciens());

        return maintenanceAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws PersistenceException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        if (Long.parseLong(request.getParameter("id")) == 0) {
            IMaintenance maintenance;
            maintenance = gza.transportPublic.domain.Maintenance.builder()
                    .mecanicien(mecanicienSercice.getById(Long.parseLong(request.getParameter("mecanicienId"))))
                    .bus(busService.getById(Long.parseLong(request.getParameter("modelId"))))
                    .commentaires(request.getParameter("commentaires"))
                    .dateDebut(sdf.parse(request.getParameter("dateDebut")))
                    .dateFin(sdf.parse(request.getParameter("dateFin")))
                    .build();
            System.out.println(maintenance.toString());
            maintenanceSercice.create(maintenance);
        } else {
            IMaintenance maintenance;
            maintenance = gza.transportPublic.domain.Maintenance.builder()
                    .id(Long.parseLong(request.getParameter("id")))
                    .mecanicien(mecanicienSercice.getById(Long.parseLong(request.getParameter("mecanicienId"))))
                    .bus(busService.getById(Long.parseLong(request.getParameter("modelId"))))
                    .commentaires(request.getParameter("commentaires"))
                    .dateDebut(sdf.parse(request.getParameter("dateDebut")))
                    .dateFin(sdf.parse(request.getParameter("dateFin")))
                    .build();
            System.out.println(maintenance.toString());
            maintenanceSercice.update(maintenance);
        }

        ModelAndView maintenanceAndView = new ModelAndView("redirect:/app/maintenance/index");
        maintenanceAndView.addObject("maintenances", maintenanceSercice.getMaintenances());

        return maintenanceAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable long id) throws PersistenceException {
        ModelAndView maintenanceAndView = new ModelAndView("maintenance/update");
        IMaintenance maintenance = maintenanceSercice.getById(id);
        maintenanceAndView.addObject("bus", busService.getBuss());
        maintenanceAndView.addObject("mecano", mecanicienSercice.getMecaniciens());
        maintenanceAndView.addObject("maintenance", maintenance);
        return maintenanceAndView;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView maintenanceAndView = new ModelAndView("maintenance/delete");
        maintenanceAndView.addObject("id", id);
        return maintenanceAndView;

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteOk(@PathVariable long id) throws PersistenceException {
        maintenanceSercice.deleteById(id);
        ModelAndView maintenanceAndView = new ModelAndView("redirect:/app/maintenance/index");
        maintenanceAndView.addObject("mods", maintenanceSercice.getMaintenances());
        return maintenanceAndView;
    }
}
