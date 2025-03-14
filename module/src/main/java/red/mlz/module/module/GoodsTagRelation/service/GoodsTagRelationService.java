package red.mlz.module.module.GoodsTagRelation.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.GoodsTagRelation.entity.GoodsTagRelation;
import red.mlz.module.module.GoodsTagRelation.mapper.GoodsTagRelationMapper;
import red.mlz.module.module.tag.service.TagsService;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class GoodsTagRelationService {
    @Resource
    private GoodsTagRelationMapper relationMapper;

    @Resource
    private TagsService tagsService;

    // 获取所有标签列表
    public List<GoodsTagRelation> getAll() {
        return relationMapper.getAll();
    }

    // 根据ID查询标签
    public GoodsTagRelation getById(BigInteger id) {
        return relationMapper.getById(id);
    }

    // 根据商品ID获取标签关联
    public List<BigInteger> getByGoodsId(BigInteger goodsId) {
        return relationMapper.getTagIdGoodsId(goodsId);
    }

    // 根据ID提取标签
    public GoodsTagRelation extractById(BigInteger id) {
        return relationMapper.extractById(id);
    }

    // 插入标签关联
    public int insert(BigInteger goodsId, BigInteger tagId) {
        GoodsTagRelation goodsTag = new GoodsTagRelation();
        goodsTag.setGoodsId(goodsId);
        goodsTag.setTagId(tagId);
        goodsTag.setCreatedTime(BaseUtils.currentSeconds());
        goodsTag.setUpdatedTime(BaseUtils.currentSeconds());
        goodsTag.setIsDeleted(0);
        return relationMapper.insert(goodsTag);

    }

    // 更新标签关联
    public int update(BigInteger id,BigInteger goodsId,BigInteger tagId) {
        GoodsTagRelation goodsTag = new GoodsTagRelation();
        goodsTag.setId(id);
        goodsTag.setGoodsId(goodsId);
        goodsTag.setGoodsId(goodsId);
        goodsTag.setTagId(tagId);
        goodsTag.setCreatedTime(BaseUtils.currentSeconds());
        goodsTag.setUpdatedTime(BaseUtils.currentSeconds());
        goodsTag.setIsDeleted(0);
        return relationMapper.update(goodsTag);
    }

    // 删除标签关联（按ID）
    public int deleteRelation(BigInteger goodsId, BigInteger tagId) {
        return relationMapper.deleteRelation(goodsId, tagId);
    }


}
