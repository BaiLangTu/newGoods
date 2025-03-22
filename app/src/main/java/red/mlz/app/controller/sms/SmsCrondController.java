package red.mlz.app.controller.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.module.module.smsCrond.service.SendCrond;
import red.mlz.module.module.smsCrond.service.SmsCrondService;
import red.mlz.module.utils.Response;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@RestController
public class SmsCrondController {

    @Autowired
    private SmsCrondService smsCrondService;

    @Autowired
    private SendCrond sendCrond;

    // 添加任务列表
    @RequestMapping("/add/send/task")
    public Response addSendTask(@RequestParam(name = "phone") String phone) {
        try {

            BigInteger result = BigInteger.valueOf(smsCrondService.addSendTask(phone));
            return new Response(1001);

        } catch (Exception exception) {
            return new Response(4004);
        }

    }

    // 短信发送
    @RequestMapping("/send")
    public int sendSmsTask() {

        try {
            return sendCrond.sendScheduledSms();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

