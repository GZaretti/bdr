/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
 
import service.simpleProjService;
 
public class CarController implements Controller {
 
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
 
		simpleProjService carManager = new simpleProjService();
 
		ModelAndView modelAndView = new ModelAndView("carList");
		modelAndView.addObject("carList", carManager.getCarList());
 
		return modelAndView;
	}
}