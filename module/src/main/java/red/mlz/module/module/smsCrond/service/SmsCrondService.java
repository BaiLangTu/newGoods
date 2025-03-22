package red.mlz.module.module.smsCrond.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.smsCrond.entity.SmsCrond;
import red.mlz.module.module.smsCrond.mapper.SmsCrondMapper;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class SmsCrondService {
    @Resource
    public SmsCrondMapper mapper;


    // 获取发送任务列表
    public List<SmsCrond> getAll() { return mapper.getAll(); }

    // 获取id信息
    public SmsCrond getById(BigInteger id){ return mapper.getById(id);}

    // 获取id信息
    public SmsCrond extractById(BigInteger id){ return mapper.extractById(id);}

    // 记录一个新的发送任务
    public int addSendTask(String phone) {
        SmsCrond smsTask = new SmsCrond();
        smsTask.setPhone(phone);
        smsTask.setSendTime((int)(System.currentTimeMillis() / 1000));  // 当前时间戳，作为发送时间
        smsTask.setStatus(0);  // 设置状态为待发送
        smsTask.setResult("未发送");  // 初始结果为空
        smsTask.setCreatedTime((int)(System.currentTimeMillis() / 1000));  // 创建时间
        smsTask.setUpdatedTime((int)(System.currentTimeMillis() / 1000));  // 更新时间
        smsTask.setIsDeleted(0);  // 默认未删除
        // 插入到数据库
        return mapper.insert(smsTask);
    }



}

