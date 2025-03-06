package red.mlz.module.module.tag.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.tag.entity.Tags;
import red.mlz.module.module.tag.mapper.TagsMapper;
import red.mlz.module.module.ukGoodsTag.entity.UkGoodsTag;
import red.mlz.module.module.ukGoodsTag.mapper.UkGoodsTagMapper;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagsService {
    @Resource
    private TagsMapper mapper;

    @Resource
    private UkGoodsTagMapper ukGoodsTagMapper;

    public List<Tags> getAll() { return mapper.getAll(); }


    public Tags getById(BigInteger id) { return mapper.getById(id); }

    public Tags extractById(BigInteger id) { return mapper.extractById(id); }


    // 获取商品的标签列表
    public List<String> getGoodsTags(BigInteger goodsId) {
        List<UkGoodsTag> ukGoodsTags = ukGoodsTagMapper.getByGoodsId(goodsId);  // 获取商品与标签的关联
        List<String> tags = new ArrayList<>();
        for (UkGoodsTag ukGoodsTag : ukGoodsTags) {
            Tags tag = mapper.getById(ukGoodsTag.getTagId());  // 根据标签ID获取标签详细信息
            if (tag != null) {
                tags.add(tag.getName());  // 只获取标签名称
            }
        }
        return tags;  // 返回标签名称列表
    }

    //创建商品类目
    public int insert(String name,String image){
        Tags tags = new Tags();
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        tags.setName(name);
        tags.setCreateTime(timestamp);
        tags.setUpdateTime(timestamp);
        tags.setIsDeleted(0);

        return mapper.insert(tags);
    }


    //更新商品类目
    public int update(BigInteger id,String name){
        red.mlz.module.module.tag.entity.Tags tags = new Tags();
        tags.setId(id);
        tags.setName(name);
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        tags.setUpdateTime(timestamp);
        tags.setIsDeleted(0);
        return mapper.update(tags);

    }

    // 删除商品类目
    public int delete(BigInteger id) { return mapper.delete(id, (int) (System.currentTimeMillis() / 1000)); }


}


