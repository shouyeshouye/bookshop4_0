package com.example.bookshop1_0.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101700706509";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCXAN4eeME6yrwG/DLKqBo7Zz9Ay1baVZBrz4EqNebZpKOy4CJsdLa6gEdtqN8EoisPZfbr/DybOLTVdVftoaMz4ARY0QLZ6ktUhRFgV3OeZNYpwMW0Dlo8pddjjWT715xvD5YYAT7P+ONknN2AgNhdoDpIUftxNKeFAbG3QLdzfRgfPsodbBFsEGjgyl6nm1mcMIrm+Jaadc9pXgfpz/bmGLAKsbmr2j6kAZ/yk2ROQrGAtNF+0tyI9JGXlPyhWTprS8I47Xb0GKPqgI3KT7e5yMtzXqURNPHDa45lpbxC9X+PVjSAP5R4yFAtQ3ume5bsP5dh+jBqK1AELhF+/Ah5AgMBAAECggEBAJVmVV0eqkeJ2BitI2+gfDuzvkBULn1cVMRmDGx/4gQJwbCz0zstCrFhQ8p6XxTNgf79Kh13dVs58qiTLci9uKQVixd4y/JgveFEutlwDMN1DvNGeBTacms4xe8IMRZ+z5aOfDkJ2mSX4htknD6MvAV+BswQ2M67gta0w2kEzbyjTUPCtoMYZf5dEoelPtwU0XMNytg2Evf6VX5xFfwWmqlULVSYrkcU3T/ZgcAgdxO1U3TxE2T72h7MnYQUgtTx/TswV/oPoclTaCZ0Pp142zbEmZmZXYc9hoGIl46CFcYtH5h9yD4Q1zVtbBaegkZprrUD+jUHIujMHw3omS7T2wECgYEAz1zaKErH+ZO+18jnP7glX/jINbGNmk0n7In4qqlPcnP5f+KPY5nOpz8RjR/qx5Ny41Vr4Q821IHa9QgnMaiyH5vFmEKC3ULac6tK5E2qviTEDyoZlvs2wX2XsLfuJNjPhNfqr2Po+E73rweKR768DRmFRexncKS6VWxLWLYJXSECgYEAumvn2syDjGb91eHr1hdfp4KR8UYbX/3k/6W9QW/QX33dOm098SRG8jPAydnrGwuQooF6aGY7U2IGufmW+d2BlcYsM5ATJm5ecVYOSogjtfbScWG3dYxFPWt175U5dLGgIZFHmNoVqI4wf+dufi98VkMMkYiE1xLE4YXK8yf9qFkCgYEAsAUCjndEHwAiXKuGWNloqHnXAXURXWynOiu4MK396Yq2vK7FxudVuzRXIrN4KSdpu73UEDxI78S2546fLpEjep9Q8LlsuoM4Sj2aGggvqq4s2y6CgF4IkeCZdgH7nb2JEhvydCVcKgNmTmYZJGOs33XNYpiVCdJa9Y4yuz/y3OECgYAlCSL62P6ZeiybvVJ5KhOa19nesSrnGy3PjEHZKjmffwXggat4qNhfbeCzOVP3/4gwgMCs1QKcQjFL6UYRRexksQY/CDSFwg+JPKuCKyHNGMgeNmeYNtHareq0OlU71b4WbSJoP4tl/Tmtb3EIKK7vBEsO7qxVfikXFNmJDTzboQKBgQDBQTM+DhAeDVos8pWmh3HCfoYiHMKsUfgWnQIFu48ZUpBchU93GhXoA6cagZBKb/oOndhyc0FP/fUTSXyz2T8TIpGKS0vyUrZApijyRd9zauwY0ESEDfNrd2W4h1e1wVHXqBpofw6fY66MjGUxLQiuEbDfuPTBVTAIpfxjrjXjmg==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAne5VsNJp4CeG4fOsMnrBknR+RaC1FDeld9rFUB9A1UYZTnqT4TZwBhdnQ9i205hYy6weIHskeycmKr0eNXVJvvHVxxudcyNls60o1eR4oB0CzIyw7PK+NTKSTArw6Apn6GLoOkNTRuJfNvgcl2FvWaIOSpLzyVDzZxtFAu5XXV8/IXNHiiDb3fNv3BBp+1FgSs2JD77in/C5M8xiGcLaabqtg6c6fu9EkYtm7GaKyM7XSLbumQVYjf4LV86UHm0Ohc7ODl0emEnT5XNouWc3BK4C/mtLj2n7NlJSGjRlDTyJIHDxaAfvh9t3k5Gv0Jvu2SV4XKbTobH/2nrkIVks9wIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://127.0.0.1:8080/notify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://127.0.0.1:8080/return";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

