package com.capp.springboot.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capp.springboot.services.UpdateService;


@Controller
public class AccountSettingsController {

	@RequestMapping("/AccountSettings")
	public ModelAndView goToAccountsSettings(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		if(null!=request.getSession().getAttribute("Name")) {
			mv.setViewName("AccountSettings");
		}else {
			mv.addObject("errorMsg", "Session expired, login again");
			mv.setViewName("Login");
		}
		return mv;
	}
	
	@RequestMapping("/updatepwd")
	public @ResponseBody String updatePassword(HttpServletRequest request, HttpServletResponse response) {
		String currentPwd = request.getParameter("cupwd");
		String newPwd = request.getParameter("copwd");
		String tableName = (String)request.getSession().getAttribute("tableName");
		String status = "";
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		UpdateService updt = (UpdateService)ctx.getBean("updateS");
		status = updt.updatePassword(currentPwd, newPwd, tableName);
		if("WRONG_PASSWORD".equalsIgnoreCase(status))
			response.setStatus(300);
		return status;
	}
	
	@RequestMapping("/updateColor")
	public @ResponseBody String updateColor(HttpServletRequest request) {
		String tableName = (String)request.getSession().getAttribute("tableName");
		String bgColour = request.getParameter("bgColour");
		String navBarBgClr = request.getParameter("NavBarBgClr");
		String hdrTxtClr = request.getParameter("hdrTxtClr");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		UpdateService updt = (UpdateService)ctx.getBean("updateS");
	    boolean isCUpdated = updt.updateColour(tableName, bgColour, navBarBgClr, hdrTxtClr);
	    if(isCUpdated) {
	    	request.getSession().setAttribute("navbar",bgColour);
	    	return "Success";
	    }
	    return "";
	}
	
	@RequestMapping("/updateView")
	public @ResponseBody String updateView(HttpServletRequest request) {
		String status = "";
		String tableName = (String)request.getSession().getAttribute("tableName");
		String view = request.getParameter("view");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		UpdateService updt = (UpdateService)ctx.getBean("updateS");
		status = updt.updateView(view, tableName);
		if("Success".equalsIgnoreCase(status))
			request.getSession().setAttribute("View", view);
	return status;	
	}
}
