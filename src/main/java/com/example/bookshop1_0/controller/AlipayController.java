package com.example.bookshop1_0.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.bookshop1_0.config.AlipayConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: zhaoxinguo
 * @Date: 2018/8/30 15:11
 * @Description: 支付宝后台接口
 */
@Log4j2
@RestController
public class AlipayController {

    /**
     * 支付网站扫码支付接口-统一下单支付接口
     *
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/pay")
    public String alipayPay() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101002\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result; //这里生成一个表单，会自动提交
    }


    /**
     * 支付宝服务器同步通知页面
     *
     * @param request
     * @param out_trade_no 商户订单号
     * @param trade_no     支付宝交易凭证号
     * @param total_amount 交易状态
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/return")
    public String alipayReturn(HttpServletRequest request, String out_trade_no, String trade_no, String total_amount) throws AlipayApiException {
        Map<String, String> params = getParamsMap(request);
        // 验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        if (signVerified) {
            return ("success");
        } else {
            return ("fail");
        }
    }

    private Map<String, String> getParamsMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return params;
    }

}

