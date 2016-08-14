<%@page import="java.io.*,sun.misc.*,java.util.*"%>
<%
String path=application.getRealPath("/avatar/");

String _savePath = path + "/";
String x = request.getContextPath();

long savePicName = new Date().getTime();

String filename160 = _savePath + savePicName + "_160.jpg";  //保存162
String filename50 = _savePath + savePicName + "_50.jpg";  //保存50



String pic=request.getParameter("file160");
String pic1=request.getParameter("file50");


if(pic!=null && !pic.equals("")){
	//160
	if(pic.contains("base64,"))
		pic = pic.substring(pic.indexOf("base64,")+7);
	File file = new File(filename160);
	FileOutputStream fout = null;
	fout = new FileOutputStream(file);
	fout.write(new BASE64Decoder().decodeBuffer(pic));
	fout.close();
}

if(pic1!=null && !pic1.equals("")){
	//50
	if(pic1.contains("base64,"))
		pic1 = pic1.substring(pic1.indexOf("base64,")+7);
	File file = new File(filename50);
	FileOutputStream fout = null;
	fout = new FileOutputStream(file);
	fout.write(new BASE64Decoder().decodeBuffer(pic1));
	fout.close();
}

String picUrl = "avatar/"+savePicName;

out.println("{\"status\":1,\"picUrl\":\""+picUrl+"\"}"); //返回图片地址

%>