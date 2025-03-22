package red.mlz.module.utils;// This file is auto-generated, don't edit it. Thanks.

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class SendSms {

    // 设置为常量，直接在类中定义
    private static final String ACCESS_KEY_ID = "LTAI5tSTHrrGMq4NFYnBMUJC";
    private static final String ACCESS_KEY_SECRET = "Weo3iVvnrfyIRAaJUY9WDhTXGsdGCJ";

    public Boolean sms(String phoneNumber, String code){

        // 检查常量是否为空（一般不为空，但可以加个防护）
        if (ACCESS_KEY_ID == null || ACCESS_KEY_SECRET == null) {
            log.error("AccessKeyId or AccessKeySecret is not set in the configuration.");
            return false;
        }

        // 使用常量创建 StaticCredentialProvider
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(ACCESS_KEY_ID)
                .accessKeySecret(ACCESS_KEY_SECRET)
                .build());

        // 配置客户端
        AsyncClient client = AsyncClient.builder()
                .region("cn-qingdao") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();

        // 配置短信请求
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("验证码短信")
                .templateCode("SMS_314820817")
                .phoneNumbers(phoneNumber)
                .templateParam("{\"code\":\""+ code + "\"}")
                .build();

        // 发送短信并等待响应
        try {
            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
            SendSmsResponse resp = response.get();
            System.out.println(new Gson().toJson(resp));

            // 根据发送结果判断是否成功
            if ("OK".equals(resp.getBody().getCode())) {
                return true;  // 发送成功
            } else {
                log.error("SMS sending failed: " + resp.getBody().getMessage());
                return false;  // 发送失败
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error sending SMS", e);
            return false;  // 出现异常时返回失败
        } finally {
            client.close();  // 确保客户端关闭
        }
    }
}
