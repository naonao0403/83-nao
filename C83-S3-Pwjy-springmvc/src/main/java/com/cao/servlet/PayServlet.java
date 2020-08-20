//package com.cao.servlet;
//
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayTradePagePayRequest;
//import com.cao.config.AlipayConfig;
//
///**
// * 支付Servlet PayServlet
// */
//@WebServlet("/pay.do")
//public class PayServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//获得初始化的AlipayClient
//		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
//		
//		//设置请求参数
//		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//		alipayRequest.setReturnUrl(AlipayConfig.return_url);
//		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
//		
//		//商户订单号，商户网站订单系统中唯一订单号，必填
//		String oid = new String(request.getParameter("oid").getBytes("ISO-8859-1"),"UTF-8");
//		//付款金额，必填
//		String total = new String(request.getParameter("total").getBytes("ISO-8859-1"),"UTF-8");
//		//订单名称，必填
//		String subject = new String(request.getParameter("oid").getBytes("ISO-8859-1"),"UTF-8");
//		//商品描述，可空
//		//String body = new String(request.getParameter("").getBytes("ISO-8859-1"),"UTF-8");	
////		String oid ="73";
////		String total ="125";
////		String subject="73";
//		String body=""; 
//		
//		alipayRequest.setBizContent("{\"out_trade_no\":\""+oid+"\"," 
//				+ "\"total_amount\":\""+ total +"\"," 
//				+ "\"subject\":\""+ subject +"\"," 
//				+ "\"body\":\""+ body +"\"," 
//				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//		//请求
//		String result = null;
//		try {
//			result = alipayClient.pageExecute(alipayRequest).getBody();
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//		
//		//System.out.println(result);
//		response.getWriter().append(result);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}
//
//}
