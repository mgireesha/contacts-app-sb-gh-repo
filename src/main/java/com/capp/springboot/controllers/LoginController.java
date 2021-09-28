package com.capp.springboot.controllers;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capp.springboot.object.SignInParams;
import com.capp.springboot.services.LoginService;
import com.capp.springboot.util.CApUtil;
import com.capp.springboot.util.DaoException;


@Controller
public class LoginController {
	
	private static final String USER_DETAIL_TABLE = "CONTACTS_APP_CREDS";
	
	@RequestMapping("/")
	public ModelAndView welcomeMessage() {
		return new ModelAndView("Login");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView gotToLogin() {
		return new ModelAndView("Login");
	}
	
	@RequestMapping(value="/login" , method = RequestMethod.POST)
	public ModelAndView validateLogin(HttpServletRequest request) {
		String unserName = request.getParameter("uname");
		String psswd = request.getParameter("psw");
		String errorMsg = "";
		Boolean isShowRed = true;
		Map<String,String> colours = null;
		String navbar = "";
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		SignInParams userDtls = new SignInParams();
		ModelAndView mv = new ModelAndView();
		LoginService login = (LoginService)ctx.getBean("loginw2");
		userDtls = login.validateLogin(unserName, psswd);
		errorMsg = userDtls.getErrMsg();
		mv.addObject("errorMsg", errorMsg);
		mv.addObject("isShowRed", isShowRed);
		if(null!=errorMsg && errorMsg.equalsIgnoreCase("Success")) {
			mv.addObject("Name", userDtls.getNameFromDb());
			mv.addObject("Email", userDtls.getUseNameFromDb());
			mv.addObject("View", userDtls.getViewFromDb());
			mv.addObject("tableName", userDtls.getTableNameFromDb());
			request.getSession().setAttribute("Name", userDtls.getNameFromDb());
			request.getSession().setAttribute("Email", userDtls.getUseNameFromDb());
			request.getSession().setAttribute("View", userDtls.getViewFromDb());
			request.getSession().setAttribute("tableName", userDtls.getTableNameFromDb());
			
			CApUtil cApUtil = new CApUtil();
            try {
				colours=cApUtil.getColours(userDtls.getTableNameFromDb());
				if(!colours.isEmpty()){
	                navbar = colours.get("navbar");
	            }
			} catch (DaoException e) {
				e.printStackTrace();
			}
            request.getSession().setAttribute("navbar", navbar);
			mv.setViewName("Home");
			mv.addObject("dark", "showDarkTheme");
			((ClassPathXmlApplicationContext) ctx).close();
		}else {
			mv.setViewName("Login");
		}
		return mv;
	}
	
	@RequestMapping("/Home")
	public ModelAndView goToHome(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		if(null!=request.getSession().getAttribute("Name")) {
			mv.addObject("Name", request.getSession().getAttribute("Name"));
			mv.addObject("Email", request.getSession().getAttribute("Email"));
			mv.addObject("View", request.getSession().getAttribute("View"));
			mv.addObject("TableName", request.getSession().getAttribute("TableName"));
			mv.addObject("errorMsg", "Success");
			mv.addObject("dark", "showDarkTheme");
			mv.setViewName("Home");
		}else {
			mv.addObject("errorMsg", "Session expired, login again");
			mv.setViewName("Login");
		}
		
		return mv;
	}
	
	@RequestMapping("/LogOut")
	public ModelAndView logOut(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		request.getSession().invalidate();
		mv.setViewName("Login");
		
		return mv;
	}
	
	@RequestMapping("/registerUser")
	public @ResponseBody String registerUser(HttpServletRequest request) {
		String status = "";
		SignInParams userObj = null;
		CApUtil cUtil = new CApUtil();
		userObj = cUtil.requestObectToSignInParams(request);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		LoginService login = (LoginService)ctx.getBean("loginw2");
		status = login.registeruser(userObj,USER_DETAIL_TABLE);
		((ClassPathXmlApplicationContext) ctx).close();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	

}
