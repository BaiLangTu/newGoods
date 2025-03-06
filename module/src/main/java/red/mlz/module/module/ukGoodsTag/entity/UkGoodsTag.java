package red.mlz.module.module.ukGoodsTag.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;


@Data
@Accessors(chain = true)
public class UkGoodsTag {
    //
    private BigInteger id;
    //商品id
    private BigInteger goodsId;
    //标签id
    private BigInteger tagId;
    //创建时间
    private Integer createTime;
    //修改时间
    private Integer updateTime;
    //是否删除，1-是，0-否
    private Integer isDeleted;
}