package com.nao.thread.d0727;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
	public static void main(String[] args) throws IOException {
		
		ServerSocket tomcat=new ServerSocket(8080);
		System.out.println("tomcat服务器启动完成，监听端口：8080");
		
		boolean running=true;
		while(running) {
			Socket socket=tomcat.accept();
			
			new Thread() {
				public void run() {
					try {
						System.out.println("接收到请求");	
						InputStream in=socket.getInputStream();
						OutputStream out=socket.getOutputStream();
						byte[] buffer=new byte[1024];
						int count;
						count=in.read(buffer);
						if(count>0) {
							System.out.println(new String(buffer,0,count));
							
							//打印请求报文
							String requestText=new String(buffer,0,count);
							System.out.println(requestText);
							/**
							 * 解析出请求资源路径
							 */
							String[] lines=requestText.split("\\n");
							String[] requestLines=lines[0].split("\\s");
							String webpath=requestLines[1];
							String contentType;
							//结果码
							int statusCode=200;
							//定义磁盘文件路径
							String path="D:/software/workspace/photo/photo/"+webpath;
							File file=new File(path);
							if (!file.exists()) {
								statusCode=404;
								path="D:/software/workspace/photo/photo/404.html";
							}
							if (webpath.endsWith(".js")) {
								contentType ="application/javascript;charset=utf-8";
							}else if(webpath.endsWith(".css")){
								contentType ="text/css;charset=utf-8";
							}else {
								//潜规则：图片可以返回HTML 浏览器可以自动识别
								contentType ="text/html;charset=utf-8";
							}
							//响应头行
							out.write(("HTTP/1.1 "+statusCode +" OK\n").getBytes());
							//响应头域
							out.write(("Content-Type:"+contentType +"\n").getBytes());
							//空行CRLF
							out.write("\n".getBytes());
							//实体
//							out.write("<h1>Hello Daniu</h1>".getBytes());
//							
							
							FileInputStream fis=new FileInputStream(path);
							while((count=fis.read(buffer))>0) {
								out.write(buffer,0,count);
							}
//							/**
//							 * 返回一个超链接 连接到某宝
//							 */
//							out.write("<a href='https://www.taobao.com/'>淘宝网</a>".getBytes());
							
							/**
							 * 问题：
							 * 	1.只能回复一次
							 * 	2.回复的永远不变
							 * 		1)解析出请求行的资源名 /photo/new.html
							 * 		2)读取文件内容输出到实体中
							 */
							fis.close();
						}
						
						socket.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
			
		}
		//Unreachable code 报错代码不可达
		tomcat.close();
	}
}
