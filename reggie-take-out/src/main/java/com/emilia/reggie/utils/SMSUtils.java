package com.emilia.reggie.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {

    /**
     * 发送短信
     *
     * @param signName 签名
     * @param templateCode 模板
     * @param phoneNumbers 手机号
     * @param param 参数
     */
    static DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GHhatiznGG52T5EidAW",
            "xOFzvwf7G8jPhL3eNpyOmjPjFRZJfx");
    static IAcsClient client = new DefaultAcsClient(profile);

    public static void sendMessage(String signName, String templateCode, String phoneNumbers, String param) {


        SendSmsRequest request = new SendSmsRequest();

        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + param + "\"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println("发送短信的状态：" + response.getMessage());

        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        //生成一个验证码
//        Integer code = ValidateCodeUtils.generateValidateCode(4);
//        System.out.println("生成验证码：" + code);
//		/*
//			参数一：签名
//			参数二： 模板
//			参数三： 接收人手机号
//			参数四： 验证码
//		 */
//        sendMessage("黑马旅游网", "SMS_205125234", "18773710057", code + "");
//
//    }


}
