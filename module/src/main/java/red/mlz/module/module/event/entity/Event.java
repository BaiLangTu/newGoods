package red.mlz.module.module.event.entity;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class Event {
    //
    private Long id;
    //活动内容
    private String content;
    //创建时间
    private Integer createdTime;
    //更新时间
    private Integer updatedTime;
    //删除
    private Byte isDeleted;
}