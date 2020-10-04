/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import service.MapperFactory;
@RequestMapping("/database")
@Controller
public class DatabaseController {
    
    private IMapperManager mapperManager;

    public DatabaseController(){
        mapperManager = MapperFactory.createMapperManager();
    }
    
    /*@RequestMapping(method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        model.addAttribute("message", "database!");
        return "database";
    }*/
    @RequestMapping(value="/index", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("database/index");
    }
    
    @RequestMapping(value="/create", method = RequestMethod.POST)
    public ModelAndView create() throws PersistenceException{
        ((DbMapperManager)mapperManager).getDatabaseSetup().createTables();
        return new ModelAndView("database/index");
    }
    
    @RequestMapping(value="/drop", method = RequestMethod.POST)
    public ModelAndView drop() throws PersistenceException{
        ((DbMapperManager)mapperManager).getDatabaseSetup().dropTables();
        return new ModelAndView("database/index");
    }
    
    
}
