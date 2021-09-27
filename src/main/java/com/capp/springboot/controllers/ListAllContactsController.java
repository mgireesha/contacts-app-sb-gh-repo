package com.capp.springboot.controllers;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capp.springboot.object.Contact;
import com.capp.springboot.services.ListContactService;

@Controller
public class ListAllContactsController {
	
	@RequestMapping("/listAll")
	public ModelAndView ListAllContacts(HttpServletRequest request, HttpServletResponse response) {
		String tableName = (String) request.getSession().getAttribute("tableName");
		ModelAndView mv = new ModelAndView();
		if(null!=tableName && !"".equalsIgnoreCase(tableName)) {
			String view = (String) request.getSession().getAttribute("View");
			List<Contact> cList = null;
			ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
			ListContactService lc = (ListContactService) ctx.getBean("listContact");
			cList = lc.getAllContacts(tableName);
			mv.addObject("cList", cList);
			mv.addObject("TableName",tableName);
			mv.addObject("View",view);
			mv.addObject("showSearchbar", "true");
			mv.addObject("actionDropDown", "true");
			//mv.addObject("dark", "showDarkTheme");
			mv.setViewName("ListAllContacts");
			((ClassPathXmlApplicationContext) ctx).close();
		}else {
			mv.addObject("errorMsg", "Session expired, login again");
			mv.setViewName("Login");
		}
		return mv;
	}
	
	@RequestMapping("/viewContact")
	public ModelAndView viewContact(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String tableName = (String) request.getSession().getAttribute("tableName");
		if(null!=tableName && !"".equalsIgnoreCase(tableName)) {
			String cId = request.getParameter("mess");
			ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
			ListContactService listCS = (ListContactService)ctx.getBean("listContact");
			Contact contact = listCS.getContactById(cId, tableName);
			File file1 = new File("D:\\SWorkspace\\ContactsAppSpringBoot\\src\\main\\webapp\\static_resources\\user_images\\"+tableName+"\\"+contact.getId()+".jpg");
			if(file1.exists())
				mv.addObject("isImgExists", "true");
			mv.addObject("contact", contact);
			mv.setViewName("ViewContact");
			((ClassPathXmlApplicationContext) ctx).close();
		}else {
			mv.addObject("errorMsg", "Session expired, login again");
			mv.setViewName("Login");
		}
		return mv;
	}
}
