package com.capp.springboot.controllers;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capp.springboot.services.UpdateService;
import com.capp.springboot.util.SendEmail;


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
		String isFromForgoPwdMail = request.getParameter("isFromForgoPwdMail");
		String registeredEmail = request.getParameter("registeredEmail");
		String tableName = (String)request.getSession().getAttribute("tableName");
		String status = "";
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		UpdateService updt = (UpdateService)ctx.getBean("updateS");
		status = updt.updatePassword(currentPwd, newPwd, tableName, isFromForgoPwdMail, registeredEmail);
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
	
	@RequestMapping("/process-request-pwr")
	public ModelAndView passwordReset(@RequestParam("uq") String uq) {
	    String userEmail = new String(Base64.getDecoder().decode(new String(Base64.getDecoder().decode(new String(Base64.getDecoder().decode(new String(Base64.getDecoder().decode(uq))))))));
		ModelAndView mv = new ModelAndView();
		mv.addObject("isFromPmail", "Yes");
		mv.addObject("userEmail",userEmail);
		mv.setViewName("process-request");
		return mv;
	}
	
	@RequestMapping("/send-pw-reset-link")
	public @ResponseBody String sendPwdResetLink(@RequestParam("registeredEmail") String registeredEmail) {
		String status = "";
		SendEmail send = new SendEmail();
		boolean isRsNext = send.mailPwd(registeredEmail);
		if(isRsNext)
			status = "An Email with new password has been successfully sent to your registered email... ";
		else
			status = "The email you have entered is not registered....";
		return status;
	}
}
