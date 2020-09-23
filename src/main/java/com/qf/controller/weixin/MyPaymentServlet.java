package com.qf.controller.weixin;


import com.qf.controller.BaseController;
import com.qf.util.SysContant;
import com.qf.util.weixin.PayCommonUtil;
import com.qf.util.weixin.ZxingUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Description:
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/11/10
 * @Time: 下午2:16
 */
@WebServlet("/weixin/payment")
public class MyPaymentServlet extends BaseController {



    public String  pay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String body = req.getParameter("body");
            String price = "1";//单位   分

            String orderId = req.getParameter("orderId");

            System.out.println(orderId+"---");
            //生成二维码？？
            //1,利用价格 ,商品描述，订单编号 生成一个 字符串(url)
            String url = PayCommonUtil.weixin_pay(price, body, orderId);
            //2,利用工具类生成二维码
            BufferedImage bufferedImage = ZxingUtil.createImage(url, 300, 300);

            //3,存储订单编号
            req.getSession().setAttribute("orderID", orderId);

            req.getSession().setAttribute("image", bufferedImage);

            // resp.sendRedirect("/payment.jsp");
            return SysContant.REDIRECT + SysContant.FLAG + "payment.jsp";


        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("msg","支付失败");
        return SysContant.FORWORD + SysContant.FLAG + "message.jsp";



        }

}
