package com.cao.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.cao.po.Result;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.classfile.Field;

/**
 * 文件上传
 * 有一个问题是，上传图片如果重启服务器就会找不到该图片
 * 解决问题：把该图片保存到 upload目录下
 */
@MultipartConfig
@WebServlet("/UploadServlet.do")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part part=request.getPart("file");
		//定义内部工程的上传文件夹
		//String webpath="/products/upload";
		
		//获取工程根目录 tomcat服务/webapps/工程名
		String webpath="/";
		//返回web路径对应的磁盘路径
		String diskpath=request.getServletContext().getRealPath(webpath);
		//将路径转成File对象
		File webappsDir=new File(diskpath);
		System.out.println(webappsDir);
		//获取上级的上级目录  tomcat
		File tomcatDir=webappsDir.getParentFile().getParentFile();
		//获取上级目录   webapps/ROOT/upload
		File uploadDir=new File(tomcatDir,"/webapps/ROOT/upload");
		System.out.println(uploadDir);
		//重新赋值磁盘路径
		diskpath=uploadDir.getAbsolutePath();//获取绝对路径
		System.out.println(diskpath);	
		diskpath=diskpath+"/"+part.getSubmittedFileName();
		webpath +="/"+part.getSubmittedFileName();
		//去掉首部的 /
		webpath=webpath.substring(1);
		part.write(diskpath);

		Gson gson=new Gson();
		Result res=new Result(1,"文件上传成功！","/upload/"+part.getSubmittedFileName());
		String json=gson.toJson(res);
		response.getWriter().append(json);
	}

}
