package com.capp.springboot.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.capp.springboot.object.Contact;


public interface cAppFileUtil {

	public String writeFile(HttpServletRequest request, String id, String filePath);

	public List<Contact> readXLSFile(String uploadedfile) throws IOException;

	public void writeXlsFile(final HttpServletResponse response, String tableName, ApplicationContext ctx) throws IOException;

	public void writeVCF(HttpServletResponse response, String tableName, ApplicationContext ctx);

	public void writeCSV(HttpServletResponse response, String tableName, ApplicationContext ctx);

	public List<Contact> readContactsFromCSV(String uploadedfile);

	public List<Contact> readContactsFromVCF(String uploadedfile) throws IOException;

	public void resizeDp(String filePath, String fileName);

}
