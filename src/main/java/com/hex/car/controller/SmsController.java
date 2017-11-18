package com.hex.car.controller;

import com.aliyuncs.exceptions.ClientException;
import com.hex.car.enums.ResultEnum;
import com.hex.car.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hex.car.utils.SmsDemo.sendSms;

/**
 * User: hexuan
 * Date: 2017/11/16
 * Time: 下午4:19
 */
@RestController
public class SmsController {
    @PostMapping(value = "/front/sendSMS")
    public Object sendSMS(String phone) throws InterruptedException, ClientException {
        Integer randomNum = (int) ((Math.random() * 9 + 1) * 100000);

        //发短信
        try {
            sendSms(phone, randomNum.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UN_KNOW_ERRO.getCode(), ResultEnum.UN_KNOW_ERRO.getMsg());
        }
        return ResultUtil.success(randomNum.toString());
//
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());
//
//        Thread.sleep(3000L);
//
//        //查明细
//        if (response.getCode() != null && response.getCode().equals("OK")) {
//            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
//            System.out.println("短信明细查询接口返回数据----------------");
//            System.out.println("Code=" + querySendDetailsResponse.getCode());
//            System.out.println("Message=" + querySendDetailsResponse.getMessage());
//            int i = 0;
//            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
//                System.out.println("SmsSendDetailDTO[" + i + "]:");
//                System.out.println("Content=" + smsSendDetailDTO.getContent());
//                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//            }
//            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//        }

    }
}
