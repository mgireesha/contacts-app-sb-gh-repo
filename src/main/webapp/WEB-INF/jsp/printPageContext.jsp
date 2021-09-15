<!DOCTYPE html>
<html>
    <head>
    <style>
    #customers {
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    border-collapse: collapse;
    width: 100%;
    }
    #customers td, #customers th {
    border: 1px solid #ddd;
    padding: 8px;
    }
    #customers tr:nth-child( even) {
    background-color: #f2f2f2;
    }
    #customers tr:hover {
    background-color: #ddd;
    }
    #customers th {
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: left;
    background-color: #1377a7;
    color: white;
    }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
    <div class="container-fluid">
        <%@ page import="java.util.Enumeration"%>
        <table class="table table-striped table-bordered table-hover table-condensed table-responsive" id="customers" border="1"><thead><tr><th>Name</th><th>Value</th></tr></thead><tbody><%
        //Print all the Parameters in the request object
        try{
	    Enumeration parameterList = request.getParameterNames();
            %><tr><td style="text-align:center;" colspan="2"><b><i><%
                    out.println("Request Params");
            %></i></b></td></tr><%
            while(parameterList.hasMoreElements()){
                String sName = parameterList.nextElement().toString();
                String[] sMultiple = request.getParameterValues( sName );
                if( 1 >= sMultiple.length ){
                // parameter has a single value. print it.
                    System.out.println(sName + " = " + request.getParameter( sName ));
                    %><tr><td><%
                        out.println(sName);
                    %></td><td><%
                        out.println(request.getParameter( sName ));
                    %></td></tr>
                <%
                }else{
                    for( int i=0; i<sMultiple.length; i++ ){
                    // if a paramater contains multiple values, print all of them
                        System.out.println( sName + "[" + i + "] = " + sMultiple[i]);
                    %><tr><td><%
                        out.println( sName);
                    %></td><td><%
                        out.println(sMultiple[i]);
                    %></td></tr><%
                  }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("");
        }
        
        //Print all the Parameters in the Page Context
	try{	
            Enumeration AttributerListS = pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
            %><tr><td style="text-align:center;" colspan="2"><b><i><%
                    out.println(" SESSION_SCOPE");
            %></i></b></td></</tr><%
            while(AttributerListS.hasMoreElements()){
                String pname = (String) AttributerListS.nextElement();
                System.out.println("  SESSION_SCOPE        "+pname+"== "+pageContext.getAttribute(pname, PageContext.SESSION_SCOPE));
                %><tr><td><%
                    out.println(pname);
                %></td><td><%
                    out.println(pageContext.getAttribute(pname, PageContext.SESSION_SCOPE));
                %></td></tr><%
            }
            Enumeration AttributerListP = pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
            %><tr><td style="text-align:center;" colspan="2"><%
                    out.println(" PAGE_SCOPE");
            %></td></tr><%
            while (AttributerListP.hasMoreElements()) {
                String pname = (String) AttributerListP.nextElement();
                System.out.println("  PAGE_SCOPE        "+pname+"== "+pageContext.getAttribute(pname, PageContext.PAGE_SCOPE));
                %><tr><td><%
                    out.println(pname);
                %></td><td><%
                    out.println(pageContext.getAttribute(pname, PageContext.PAGE_SCOPE));
                %></td></tr><%
            }
            Enumeration AttributerListR = pageContext.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
            %><tr><td style="text-align:center;" colspan="2"><b><i><%
                    out.println(" REQUEST_SCOPE");
            %></i></b></td></tr><%
            while(AttributerListR.hasMoreElements()){
                String pname = (String) AttributerListR.nextElement();
                System.out.println("  REQUEST_SCOPE        "+pname+"== "+pageContext.getAttribute(pname, PageContext.REQUEST_SCOPE));
                %><tr><td><%
                    out.println(pname);
                %></td><td><%
                    out.println(pageContext.getAttribute(pname, PageContext.REQUEST_SCOPE));
                %></td></tr><%
            }
	}catch(Exception e){
            e.printStackTrace();
            System.out.println("");
	}	
        //Print all the cookies in the request object
        try{
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                %><tr><td style="text-align:center;" colspan="2"><b><i><%
                    out.println(" Request Cookies");
                %></i></b></td></tr><%
                for (Cookie cookie : cookies) {
                    System.out.print("request cookies  "+cookie.getName()+" : ");
                %><tr><td><%
                    out.println(cookie.getName());
                %></td><td><%
                    out.println(cookie.getValue());
                %></td></tr><%
                    System.out.println(cookie.getValue());
                    out.println();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("");
	}
    %>
            </tbody>
        </table>
    </div>
    </body>
</html>