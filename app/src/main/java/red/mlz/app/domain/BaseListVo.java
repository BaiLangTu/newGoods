package red.mlz.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import red.mlz.app.domain.goods.GoodsListVO;

import java.util.List;

@Data
@Accessors(chain = true)
public class BaseListVo {
    private List<GoodsListVO> list;
    private String wp;
    private Boolean isEnd;
}
