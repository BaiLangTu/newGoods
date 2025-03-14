package red.mlz.module.module.tag.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.GoodsTagRelation.entity.GoodsTagRelation;
import red.mlz.module.module.GoodsTagRelation.mapper.GoodsTagRelationMapper;
import red.mlz.module.module.tag.entity.Tag;
import red.mlz.module.module.tag.mapper.TagMapper;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagsService {
    @Resource
    private TagMapper mapper;

    @Resource
    private GoodsTagRelationMapper relationMapper;

    public List<Tag> getAll() { return mapper.getAll(); }


    public Tag getById(BigInteger id) { return mapper.getById(id); }

    public Tag extractById(BigInteger id) { return mapper.extractById(id); }


    // 获取商品的标签列表
    public List<String> getGoodsTags(BigInteger goodsId) {
        // 获取商品与标签的关联列表
        List<GoodsTagRelation> goodsTagList = relationMapper.getTagIdByGoodsId(goodsId);

        // 如果没有标签关联，直接返回空列表
        if (goodsTagList.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取所有标签ID
        List<BigInteger> tagIds = goodsTagList.stream()
                .map(GoodsTagRelation::getTagId)
                .collect(Collectors.toList());


        // 获取所有标签（批量查询标签）
        List<Tag> tags = mapper.getTagByIds(tagIds.toString());

        // 创建一个空的列表来存储标签名称
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        return tagNames; // 返回标签名称列表
    }

    public Tag getTagByName(String name) { return mapper.getTagByName(name); }

    //创建商品标签
    public Tag insert(String name){
        Tag tag = new Tag();
        tag.setName(name);
        tag.setCreatedTime(BaseUtils.currentSeconds());
        tag.setUpdatedTime(BaseUtils.currentSeconds());
        tag.setIsDeleted(0);
        mapper.insert(tag);
        return tag;

    }


    //更新商品标签
    public int update(BigInteger id,String name){
        Tag tags = new Tag();
        tags.setId(id);
        tags.setName(name);
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        tags.setUpdatedTime(timestamp);
        tags.setIsDeleted(0);
        return mapper.update(tags);

    }

    // 删除商品类目
    public int delete(BigInteger id) { return mapper.delete(id, (int) (System.currentTimeMillis() / 1000)); }


}


