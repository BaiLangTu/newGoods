package red.mlz.app.domain.goods;

import lombok.Data;
import lombok.experimental.Accessors;
import red.mlz.module.utils.ImageInfo;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class GoodsListVO {

    private BigInteger id;

    private String categoryName;

    private String title;

    private ImageInfo goodsImage;

    private Integer sales;

    private Integer price;
}
