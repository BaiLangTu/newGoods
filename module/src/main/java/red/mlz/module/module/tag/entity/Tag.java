package red.mlz.module.module.tag.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;


@Data
@Accessors(chain = true)
public class Tag {
    //标签ID
    private BigInteger id;
    //标签名
    private String name;

    private Integer createdTime;
    //修改时间
    private Integer updatedTime;
    //是否删除，1-是，0-否
    private Integer isDeleted;
}