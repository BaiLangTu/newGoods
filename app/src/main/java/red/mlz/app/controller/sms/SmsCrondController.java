package red.mlz.app.controller.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.module.module.sms_crond.service.SmsCrondService;
import red.mlz.module.utils.Response;

@RestController
public class SmsCrondController {

    @Autowired
    private SmsCrondService smsCrondService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestParam String phoneNumbers) {
        try {
            // 调用发送短信的服务方法
            smsCrondService.sendThread(phoneNumbers);
            return ResponseEntity.ok("短信发送请求已接收，正在处理中...");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("短信发送失败: " + e.getMessage());
        }
    }
    // 多线程发送
    @RequestMapping("send/thread")
    public Response sendThread(@RequestParam(name = "phone") String phone){

        smsCrondService.sendThread(phone);
        return new Response(1001);

    }

    // 同步发送
    @RequestMapping("send/sync")
    public Response sendSync(@RequestParam(name = "phone") String phone){

         return new Response(1001,smsCrondService.sendSmsSync(phone));
    }


    // 异步发送
    @RequestMapping("send/async")
    public Response sendAsync(@RequestParam(name = "phone") String phone) {

        return new Response(1001,smsCrondService.SendAsync(phone));

    }

}

