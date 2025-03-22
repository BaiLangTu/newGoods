package red.mlz.module.module.smsCrond.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import red.mlz.module.module.smsCrond.entity.SmsCrond;
import red.mlz.module.module.smsCrond.mapper.SmsCrondMapper;
import red.mlz.module.utils.SendSms;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class SendCrond {
    @Autowired
    private SmsCrondMapper mapper;
    @Autowired
    private SendSms sendSms;

    // 定时任务：每分钟检查待发送的任务
    @Scheduled(fixedDelay = 60000)  // 每分钟执行一次
    public int sendScheduledSms() throws ExecutionException, InterruptedException {
        // 获取待发送的任务列表
        List<SmsCrond> smsTasks = mapper.getAll();

        // 如果没有待发送的任务，直接返回
        if (smsTasks == null || smsTasks.isEmpty()) {
            log.info("No tasks to send.");
            return 0;
        }

        // 遍历任务列表，发送短信
        for (SmsCrond smsTask : smsTasks) {
            // 如果任务没有手机号，跳过
            if (smsTask.getPhone() == null) {
                log.error("SmsTask is null or phone number is missing, skipping task.");
                continue;
            }

            // 随机生成验证码
            String phone = smsTask.getPhone();
            String code = "验证码：" + (int) (Math.random() * 900000);  // 随机生成验证码

            // 发送短信
            Boolean result = sendSms.sms(phone, code);
            // 更新任务状态和发送结果
            smsTask.setPhone(phone);
            smsTask.setStatus(result ? 1 : 0);  // 1表示已发送，0表示发送失败
            smsTask.setContent(code);
            smsTask.setSendTime((int) (System.currentTimeMillis() / 1000));
            smsTask.setResult(result ? "短信发送成功" : "短信发送失败");
            smsTask.setUpdatedTime((int) (System.currentTimeMillis() / 1000));  // 更新时间戳
            mapper.update(smsTask);  // 更新任务记录
        }
        return 0;
    }
}
