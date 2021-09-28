package com.capp.springboot.controllers;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capp.springboot.services.RemoveService;

@Controller
public class RemoveController {
	
	@RequestMapping(value="/deleteContact", method=RequestMethod.DELETE)
	public @ResponseBody String deleteContactById(HttpServletRequest request) {
		String tableName = (String)request.getSession().getAttribute("tableName");
		String cId = request.getParameter("mess");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		RemoveService rem = (RemoveService)ctx.getBean("removeS");
		String status = rem.deleteContactById(cId, tableName);
		((ClassPathXmlApplicationContext) ctx).close();
		return status;
	}
	
	@RequestMapping("/deleteAC")
	public @ResponseBody String deleteAccount(HttpServletRequest request, HttpServletResponse response) {
		String status ="";
		String tableName = (String)request.getSession().getAttribute("tableName");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		RemoveService rem = (RemoveService)ctx.getBean("removeS");
		status = rem.deleteAccount(tableName);
		if("Success".equalsIgnoreCase(status))
			request.getSession().invalidate();
		((ClassPathXmlApplicationContext) ctx).close();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	return status;
	}
	
	@RequestMapping("/deleteACPwdVerify")
	public @ResponseBody String deleteACPwdVerify(HttpServletRequest request, HttpServletResponse response) {
		String checkPwd = "";
		String pwd = request.getParameter("pwd");
		System.out.println(pwd);
		String tableName = (String)request.getSession().getAttribute("tableName");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		RemoveService rem = (RemoveService)ctx.getBean("removeS");
		checkPwd = rem.verifyPwd(tableName,pwd);
		if(!"VERIFIED".equalsIgnoreCase(checkPwd)){
			response.setStatus(300);
		}
		((ClassPathXmlApplicationContext) ctx).close();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return checkPwd;
	}
	
	@RequestMapping("/deleteSelected")
	public @ResponseBody String deleteSelected(HttpServletRequest request) {
		String status ="";
		String tableName = (String)request.getSession().getAttribute("tableName");
		String checkedContactIds = request.getParameter("checkedBoxes");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		RemoveService rem = (RemoveService)ctx.getBean("removeS");
		status = rem.deleteSelectedContacts(tableName,checkedContactIds);
		((ClassPathXmlApplicationContext) ctx).close();
		return status;
	}
}
