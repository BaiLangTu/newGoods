package red.mlz.module.module.event.service;


import org.springframework.stereotype.Service;
import red.mlz.module.module.event.entity.Event;
import red.mlz.module.module.event.mapper.EventMapper;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class EventService {
    @Resource
    private EventMapper eventMapper;

    // 获取类目列表
    public List<Event> getAll() {
        return eventMapper.getAll();
    }

    // 根据ID查询操作
    public Event getById(BigInteger id) {
        return eventMapper.getById(id);
    }

    // 根据ID提取操作
    public Event extractById(BigInteger id) {
        return eventMapper.extractById(id);
    }

    // 插入操作
    public int insert(Event event) {
        return eventMapper.insert(event);
    }

    // 更新操作
    public int update(Event event) {
        return eventMapper.update(event);
    }

    // 删除操作
    public int delete(BigInteger id, Integer time) {
        return eventMapper.delete(id, time);
    }
}
