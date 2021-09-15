package com.capp.springboot.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capp.springboot.object.Contact;
import com.capp.springboot.services.ListContactService;
import com.capp.springboot.services.SaveContactService;
import com.capp.springboot.util.CApUtil;

@Controller
public class AddUpdateController {

	private final Logger LOG = LoggerFactory.getLogger(CApUtil.class);
	
	@RequestMapping("/AddUpdate")
	public ModelAndView goToAddUpdate(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		if(null!=request.getSession().getAttribute("tableName")) {
		String tableName = (String)request.getSession().getAttribute("tableName");
		String action = request.getParameter("action");
		String cId = request.getParameter("mess");
		Contact contact = new Contact();
		List<String> sList = new ArrayList<String>();
		ListContactService listCS = null;
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		CApUtil cUtil = new CApUtil();
		sList = cUtil.getStatesList();
		mv.addObject("staeList", sList);
		if(null!=action && "update".equalsIgnoreCase(action)) {
			listCS = (ListContactService)ctx.getBean("listContact");
			contact = listCS.getContactById(cId, tableName);
			mv.addObject("action", "update");
			mv.addObject("contact", contact);
			mv.addObject("AddNew", "true");
		}else {
			mv.addObject("AddNew", "true");
			mv.addObject("action", "new");
			mv.addObject("action1",true);
		}
		mv.setViewName("AddUpdate");
		}else {
			mv.addObject("errorMsg", "Session expired, login again");
			mv.setViewName("../../Login");
		}
		return mv;
	}
	
	@RequestMapping(value="/getCityList", method=RequestMethod.GET, produces="text/html")
	public @ResponseBody String getCityList(@RequestParam("state") String state) {
		List<String> cityList = new ArrayList<String>();
		CApUtil cUtil = new CApUtil();
		cityList = cUtil.getCityList(state);
		String cityOptList ="<select name='city' class='form-control response' id='city'>"+
							"<option value=''>Select your city</option>";
		Iterator<String> it = cityList.iterator();
		String tempCity = "";
		while(it.hasNext()) {
			tempCity = it.next();
			cityOptList+="<option value='"+tempCity+"'>"+tempCity+"</option>";
		}
		cityOptList+="</select>";
		return cityOptList;
	}
	
	@RequestMapping(value="/saveContact", method=RequestMethod.POST)
	public @ResponseBody String saveEditContact(HttpServletRequest request) {
		CApUtil cUtil = new CApUtil();
		String tableName = (String)request.getSession().getAttribute("tableName");
		String action = request.getParameter("action");
		Contact contact = cUtil.requestObectToContact(request);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		SaveContactService save = (SaveContactService)ctx.getBean("saveContact");
		if("update".equalsIgnoreCase(action)) {
			save.updateContactById(contact, tableName);
		}else {
			List<Contact> cList = new ArrayList<Contact>();
			cList.add(contact);
			LOG.info("TableName: "+tableName);
			save.saveContacts(cList, tableName);
		}
	return save.getMaXContactid(tableName);
	}
	
}
