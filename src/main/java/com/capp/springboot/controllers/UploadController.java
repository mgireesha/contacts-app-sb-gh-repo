package com.capp.springboot.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.LinkOption;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.capp.springboot.object.Contact;
import com.capp.springboot.services.SaveContactService;
import com.capp.springboot.services.UpdateService;
import com.capp.springboot.util.cAppFileUtil;

@Controller
public class UploadController {
	
	@Value("${upload.filepath}")
	private String filePath;
	
	@RequestMapping(method=RequestMethod.POST , value = "/upload")
	public ModelAndView importContacts(HttpServletRequest request, HttpServletResponse response) {
		String tableName = (String) request.getSession().getAttribute("tableName");
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		ServletContext sCtx = request.getSession().getServletContext();
		ModelAndView mv = new ModelAndView();
		if("dp".equalsIgnoreCase(action)) {
			mv.setViewName("redirect:/viewContact?mess="+id);
		}else {
			mv.setViewName("ListAllContacts");
			
		}
		String contentType = request.getContentType();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		cAppFileUtil cFileUtil = (cAppFileUtil)ctx.getBean("cAppFUtil");
		SaveContactService saveContact = (SaveContactService)ctx.getBean("saveContact");
		UpdateService updateSrc = (UpdateService)ctx.getBean("updateS");
		String fileName = cFileUtil.writeFile(request, id, filePath);
        String uploadedfile=filePath+"\\"+fileName;
        // Getting Filetype (extension)     
        String fileType="";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            fileType = fileName.substring(i+1);
        }
        List<Contact> contacts =null;
        try {
        	if("import".equalsIgnoreCase(action)) {
        		if("csv".equals(fileType)){
            		contacts = cFileUtil.readContactsFromCSV(uploadedfile);
            	}else if("xls".equals(fileType)){ 
            		contacts = cFileUtil.readXLSFile(uploadedfile);
            	}else if("vcf".equals(fileType)){
            		contacts = cFileUtil.readContactsFromVCF(uploadedfile);
            	}
            	saveContact.saveContacts(contacts, tableName);
        	}else if("dp".equalsIgnoreCase(action)) {
        		System.out.println(filePath);
        		fileName=id+".jpg";
        		cFileUtil.resizeDp(filePath,fileName);      
                File filu = new File(filePath+"\\"+fileName);
                Path userIPath = Paths.get("static_resources\\user_images\\"+tableName);
                if(!Files.isDirectory(userIPath)) {
                	File f1 = new File(userIPath.toString()); 
                	boolean bool = f1.mkdir();
                }
                Path temp = Files.move(Paths.get(filePath+"\\"+fileName), Paths.get(userIPath+"\\"+fileName), StandardCopyOption.REPLACE_EXISTING);
                if(temp != null)
                {
                    System.out.println("File renamed and moved successfully");
                    mv.addObject("isImgExists", "true");
                }
                else
                {
                    System.out.println("Failed to move the file");
                }
                //byte[] fileContent=null;// removed this as images are dispayed from directory
                //try{ 
                //    fileContent = Files.readAllBytes(filu.toPath());
                //}catch(java.nio.file.AccessDeniedException e) {
                //    e.printStackTrace();
                //}
                //updateSrc.updateDp(fileContent,id,tableName);
               //filu.delete();
                byte[] dummy = "".getBytes();
                updateSrc.updateDp(dummy,id,tableName);
        	}
        } catch (IOException e) {
			e.printStackTrace();
		}
        mv.addObject("errorMsg", "Success");
        
        return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST , value = "/uploadMulti", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public void testmutipart(@RequestParam("file") MultipartFile mFile) {
		String filePath = "D:\\SWorkspace\\ContactsAppSpringBoot\\src\\main\\resources\\temp";
		try {
			mFile.transferTo(new File(filePath));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/exportContacts")
	public void exportContacts(HttpServletRequest request, HttpServletResponse response) {
		String exportFileType = request.getParameter("exportFileType");
		String tableName = (String) request.getSession().getAttribute("tableName");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
		cAppFileUtil cFUtil = (cAppFileUtil) ctx.getBean("cAppFUtil");
		try {
			if("xls".equalsIgnoreCase(exportFileType)) {
				cFUtil.writeXlsFile(response, tableName,ctx);
			}else if("vcf".equalsIgnoreCase(exportFileType)) {
				cFUtil.writeVCF(response, tableName,ctx);
			}else if("csv".equalsIgnoreCase(exportFileType)) {
				cFUtil.writeCSV(response, tableName,ctx);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
