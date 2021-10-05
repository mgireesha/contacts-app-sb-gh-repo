package com.capp.springboot.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.capp.springboot.object.Contact;
import com.capp.springboot.services.ListContactService;


public class CAppFileUtilImpl implements cAppFileUtil {

	private static final Logger LOG = LoggerFactory.getLogger(CAppFileUtilImpl.class);
	
	public String writeFile(HttpServletRequest request, String id, String filePath){
		File file ;
	    String fileName="";
	    int maxFileSize = 10000 * 1024;
	    int maxMemSize = 10000 * 1024;
	    	DiskFileItemFactory factory = new DiskFileItemFactory();
	    	// maximum size that will be stored in memory
	    	factory.setSizeThreshold(maxMemSize);
		                 
		    // Location to save data that is larger than maxMemSize.
	        factory.setRepository(new File(filePath));

	        // Create a new file upload handler
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        
	        upload.setHeaderEncoding(request.getCharacterEncoding());
	                 
	        // maximum file size to be uploaded.
	        upload.setSizeMax( maxFileSize );
	                 
	        try { 
	             // Parse the request to get file items.
	             List fileItems = upload.parseRequest(request);

	             // Process the uploaded file items
	             Iterator i = fileItems.iterator(); 
	                    
	             while(i.hasNext()){
	             	FileItem fi = (FileItem)i.next();    
	                if ( !fi.isFormField () ) {
	                	// Get the uploaded file parameters
	                    fileName = fi.getName();
	                    if(null!=id && !"".equalsIgnoreCase(id)){
	                    	fileName =id+".jpg"; 
	                    }
	                    // Write the file
	                    if( fileName.lastIndexOf("\\") >= 0 ) {
	                    	file = new File( filePath + "\\" +
	                                   fileName.substring( fileName.lastIndexOf("\\"))) ;
	                    } else {
	                    		file = new File( filePath + "\\" + 
	                                   	fileName.substring(fileName.lastIndexOf("\\")+1)) ;
	                    }
	                    fi.write( file ) ;
	                 }
	             }
	         }catch(Exception e) {
	         	e.printStackTrace();
	            System.err.println(e);
	         }    
	    return fileName;
	}

	public List<Contact> readXLSFile(String uploadedfile) throws IOException {
		
		InputStream contactXLSFileIS = new FileInputStream(uploadedfile);
		HSSFWorkbook cXlWrkBk = new HSSFWorkbook(contactXLSFileIS);
		HSSFSheet cXlSheet = cXlWrkBk.getSheetAt(0);
		HSSFRow cXlRow = null;
		HSSFCell cXlCell =null;
		CellType cXlCellType =null;
		Iterator<Cell> cXlCells = null;
		Contact contact = null;
		int columnIndex = 0;
		List<Contact> cList = new ArrayList<Contact>();
		Iterator<Row> cXlRows = cXlSheet.rowIterator();
		cXlRows.next();//This extra command is to skip the header in xls file
		while(cXlRows.hasNext()) {
			cXlRow = (HSSFRow) cXlRows.next();
			cXlCells = cXlRow.cellIterator();
			contact = new Contact();
			while(cXlCells.hasNext()) {
				cXlCell =(HSSFCell)cXlCells.next();
				cXlCellType = cXlCell.getCellType();
				columnIndex = cXlCell.getColumnIndex();				
				switch (columnIndex) {
				case 0:
					contact.setName(cXlCell.getStringCellValue());
					break;
				case 1:
					contact.setGender(cXlCell.getStringCellValue());
					break;
				case 2:
					contact.setEmail(cXlCell.getStringCellValue());
					break;
				case 3:
					if(cXlCellType==CellType.NUMERIC) {
						contact.setPhone(new String(new BigDecimal(cXlCell.getNumericCellValue()).toString()));
					}if(cXlCellType==CellType.STRING) {
						contact.setPhone(cXlCell.getStringCellValue());
					}
					break;
                case 4:
                    contact.setCity(cXlCell.getStringCellValue());
                    break;
                case 5:
                    contact.setAddress(cXlCell.getStringCellValue());
                    break;
                case 6:
                    contact.setState(cXlCell.getStringCellValue());
                    break;
				}
			}
			cList.add(contact);
		}
		return cList;
	}

	public void writeXlsFile(final HttpServletResponse response, String tableName, ApplicationContext ctx) throws IOException {
		FileInputStream fileIS = new FileInputStream("Template.xls");
		HSSFWorkbook hfWb = new HSSFWorkbook(fileIS);
		HSSFSheet sheet = hfWb.getSheetAt(0);
		List<Contact> cList = null;
		ListContactService lc = (ListContactService) ctx.getBean("listContact");
		cList = lc.getAllContacts(tableName);
        Iterator<Contact> cIt = cList.iterator(); 
        Contact ct = null;
        
        HSSFCell cell = null;
        HSSFRow row = null;
        int r = 1;
        while(cIt.hasNext()){
            ct = (Contact) cIt.next();
                row = sheet.createRow(r);
                ++r;            
                //iterating c number of columns
                for (int c=0;c < 7; c++ )
                {
                    switch(c){ 
                    
                    case 0: cell = row.createCell(0);
                            cell.setCellValue(ct.getName());
                            break;
                    case 1: cell = row.createCell(1);
                            cell.setCellValue(ct.getGender());
                            break;
                    case 2: cell = row.createCell(2);
                            cell.setCellValue(ct.getEmail());
                            break;
                    case 3: cell = row.createCell(3);
                            cell.setCellValue(ct.getPhone());
                            break;
                    case 4: cell = row.createCell(4);
                            cell.setCellValue(ct.getCity());
                            break;
                    case 5: cell = row.createCell(5);
                            cell.setCellValue(ct.getAddress());
                            break;
                    case 6: cell = row.createCell(6);
                            cell.setCellValue(ct.getState());
                            break;                                    
                    }
                }                        
        }
        DateFormat dateFormat = new SimpleDateFormat("_yyyy/MM/dd_HH:mm:ss");
        Date date = new Date();
        String dateAndTime = dateFormat.format(date);
        
    response.reset();
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=Contacts_backup"+dateAndTime+".xls");
    hfWb.write(response.getOutputStream());
    response.getOutputStream().close();
	}

	public void writeVCF(HttpServletResponse response, String tableName, ApplicationContext ctx) {
        List<Contact> cList = new ArrayList<Contact>();
        ListContactService lc = (ListContactService) ctx.getBean("listContact");
		cList = lc.getAllContacts(tableName);
        Iterator<Contact> it = cList.iterator();
        StringBuilder sb=new StringBuilder();
        while(it.hasNext()){
            Contact ct = it.next();
            sb.append("BEGIN:VCARD\n");
            sb.append("VERSION:4.0\n");
            sb.append("FN:"+ct.getName()+"\n");
            sb.append("TEL;TYPE=CELL:"+ct.getPhone()+"\n");
            sb.append("EMAIL:"+ct.getEmail()+"\n");
            sb.append("END:VCARD\n");        
        }
        try{
        	DateFormat dateFormat = new SimpleDateFormat("_yyyy/MM/dd_HH:mm:ss");
        	Date date = new Date();
        	String dateAndTime = dateFormat.format(date);
        	response.reset();
        	response.setContentType("Content-Type: text/x-vcard");
        	response.setHeader("Content-Disposition", "attachment; filename=Contacts_backup"+dateAndTime+".vcf");
        	ServletOutputStream sout=response.getOutputStream();
        	sout.write(sb.toString().getBytes());
        	sout.flush();
        	sout.close();
        }catch(IOException e){
        	e.printStackTrace();
        }
	}

	public void writeCSV(HttpServletResponse response, String tableName, ApplicationContext ctx) {
        List<Contact> cList = new ArrayList<Contact>();
        ListContactService lc = (ListContactService) ctx.getBean("listContact");
		cList = lc.getAllContacts(tableName);
        Iterator<Contact> it = cList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("Name,Gender,Email,Phone,City,Address,State\n");
        while(it.hasNext()){
        	Contact contact = (Contact)it.next();
            sb.append(contact.getName()+","+contact.getGender()+","+contact.getEmail()+","+contact.getPhone()+","+contact.getCity()+","+contact.getAddress()+","+contact.getState()+"\n");
        }
        int last = sb.lastIndexOf("\n");
        if (last >= 0) { sb.delete(last, sb.length()); }
        
        DateFormat dateFormat = new SimpleDateFormat("_yyyy/MM/dd_HH:mm:ss");
        Date date = new Date();
        String dateAndTime = dateFormat.format(date);        
        try {
            response.reset();
            response.setContentType("application/csv");
            //response.setContentType("application/unknown"); //this also works for csv
            response.setHeader("content-disposition","attachment; filename=Contacts_backup"+dateAndTime+".csv");
            PrintWriter out = response.getWriter();
            out.println(sb);
            out.flush();
            out.close();            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public List<Contact> readContactsFromCSV(String fileName) {
        List<Contact> contacts = new ArrayList<Contact>(); 
        Path pathToFile = Paths.get(fileName); 
            // create an instance of BufferedReader 
            // using try with resource, Java 7 feature to close resources 
        try 
        (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) { 
            // read the first line from the text file 
        String line = br.readLine(); 
        line = br.readLine();//skip first line in the document
            // loop until all lines are read 
        while (line != null) { 
            // use string.split to load a string array with the values from 
            // each line of
            // the file, using a comma as the delimiter 
        String[] contactDetailsArray = line.split(",",-1); 
        Contact contact = createContact(contactDetailsArray); 
            // adding contact into ArrayList 
        contacts.add(contact); 
            // read next line before looping 
            // if end of file reached, line would be null 
        line = br.readLine(); 
        } 
        }catch (IOException ioe){ 
            ioe.printStackTrace(); 
        } 
        return contacts;
	}
	
    private static Contact createContact(String[] contactDetailsArray) { 
            String name = contactDetailsArray[0];
            String gender = contactDetailsArray[1];
            String email = contactDetailsArray[2];
            String phone = contactDetailsArray[3];
            String city = contactDetailsArray[4];
            String address = contactDetailsArray[5];
            String state = contactDetailsArray[6];
            //String id = metadata[7];
        
            // create and return contact of this contactDetailsArray 
            return new Contact(name, email, phone, gender, city, address, state); 
    }

	@Override
	public List<Contact> readContactsFromVCF(String filePath) throws IOException {
		LOG.info("filePath:"+ filePath);
	    final String BEGIN_VCARD = "BEGIN:VCARD";
	    final String END_VCARD = "END:VCARD";
	    final String VERSION = "VERSION:2.1";
	    
	    //Create a path object which holds the location of the vCard file.
	      Path fileLocation = Paths.get(filePath);
	      if( ! Files.exists(fileLocation)){
	        System.out.println("File Doesn't exist. Exiting...");
	        System.exit(1);
	      }

	      //Create a path object which holds the directory of the CSV file to be created.
	      Contact contact = null;
	      List<Contact> cList = new ArrayList<Contact>();
	      boolean addToList = false;

	      //1. Reading the contents of the file into a list and iterating through it.
	      for (String str : Files.readAllLines(fileLocation )) {
	        //2. Indicates the beginning of vCard 
	        if (str.contains(BEGIN_VCARD)) {
	        	contact = new Contact();
	          addToList = false;
	        }

	        //3. Populating the instance of MyCSVContacts with the data obtained from the vCard parsing.
	        if (contact != null) {
	          String[] contactDetArr = str.split(":");
	          if (contactDetArr.length > 1) {
	            String contactDet = contactDetArr[0];
	            String contactVal = contactDetArr[1];

	            if(contactDet.startsWith("TEL")){
	                    if(contactVal.startsWith("+91")){
	                        contactVal = contactVal.substring(3, contactVal.length());
	                    }
	                    if(contactVal.contains("-")){
	                        String [] removeArray=contactVal.split("-");
	                        String temp="";
	                        for(String numb : removeArray){
	                            if(!"".equals(temp)){
	                                //System.out.println(temp);
	                                temp=temp+numb;
	                            }else{
	                                temp=numb;
	                                //System.out.println(temp);
	                            }
	                        }
	                        //System.out.println(contactVal);
	                        //contactVal="+91-"+temp;
	                    }
	                    contact.setPhone(contactVal);
	                addToList = true;
	            }
	            else if (contactDet.startsWith("FN")){
	            	contact.setName(contactVal);
	            }else if(contactDet.startsWith("EMAIL")){
	            	contact.setEmail(contactVal);
	            }

	          }
	        }
	        //4. End of the vCard is found and hence the instance of MyCSVContact is added to the list.
	        if (str.contains(END_VCARD)) {
	          if (addToList)
	          {
	            cList.add(contact);
	          }
	          contact = null;
	        }
	      }
	      LOG.info("csv exit:");
	    return cList;
	}

	@Override
	public void resizeDp(String filePath, String fileName) {
        String inputOutputImagePath= filePath+"\\"+fileName;
        System.out.println(inputOutputImagePath);
        int scaledWidth = 1200;
        int scaledHeight = 1025;
        System.out.println(inputOutputImagePath);
            File inputFile = new File(inputOutputImagePath);
            BufferedImage inputImage;
            try {
                inputImage = ImageIO.read(inputFile);
                // creates output image
                BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
        
                // scales the input image to the output image
                Graphics2D g2d = outputImage.createGraphics();
                g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
                g2d.dispose();
        
                // extracts extension of output file
                String formatName = inputOutputImagePath.substring(inputOutputImagePath
                                    .lastIndexOf(".") + 1);
        
                // writes to output file
                ImageIO.write(outputImage, formatName, new File(inputOutputImagePath));
            } catch (IOException e) {
                e.printStackTrace();
            } 
	}

}
