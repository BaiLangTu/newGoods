package red.mlz.console.domain.goods;

import lombok.Data;
import lombok.experimental.Accessors;
import red.mlz.module.module.tag.entity.Tags;

import java.math.BigInteger;
import java.util.List;

@Data
@Accessors(chain = true)
public class GoodsListVo {

    private BigInteger id;

    private String title;

    private String goodImage;

    private Integer sales;

    private Integer price;

    private List<Tags> tags;


}
