package red.mlz.module.module.GoodsTagRelation.service;

import org.springframework.stereotype.Service;
import red.mlz.module.module.GoodsTagRelation.entity.GoodsTagRelation;
import red.mlz.module.module.GoodsTagRelation.mapper.GoodsTagRelationMapper;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class GoodsTagRelationService {
    @Resource
    private GoodsTagRelationMapper relationMapper;

    // 获取所有标签列表
    public List<GoodsTagRelation> getAll() {
        return relationMapper.getAll();
    }

    // 根据ID查询标签
    public GoodsTagRelation getById(BigInteger id) {
        return relationMapper.getById(id);
    }

    // 根据商品ID获取标签关联
    public List<GoodsTagRelation> getByGoodsId(BigInteger goodsId) {
        return relationMapper.getByGoodsId(goodsId);
    }

    // 根据ID提取标签
    public GoodsTagRelation extractById(BigInteger id) {
        return relationMapper.extractById(id);
    }

    // 插入标签关联
    public int insert(GoodsTagRelation goodsTagRelation) {

        return relationMapper.insert(goodsTagRelation);
    }

    // 更新标签关联
    public int update(GoodsTagRelation goodsTagRelation) {
        return relationMapper.update(goodsTagRelation);
    }

    // 删除标签关联（按ID）
//    public int delete(BigInteger id, Integer time) {
//        return relationMapper.delete(id, time);
//    }

    // 删除标签关联（按商品ID和标签ID）
    public int delete(BigInteger goodsId, List<GoodsTagRelation> tagId, Integer time) {
        return relationMapper.delete(goodsId, tagId, time);
    }
}
