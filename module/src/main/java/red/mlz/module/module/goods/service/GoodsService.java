package red.mlz.module.module.goods.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.mlz.module.module.GoodsTagRelation.service.GoodsTagRelationService;
import red.mlz.module.module.goods.dto.GoodsDTO;
import red.mlz.module.module.goods.entity.Category;
import red.mlz.module.module.goods.entity.Goods;
import red.mlz.module.module.goods.mapper.GoodsMapper;
import red.mlz.module.module.goods.service.impl.CategoryServiceImpl;
import red.mlz.module.module.tag.entity.Tag;
import red.mlz.module.module.tag.service.TagsService;
import red.mlz.module.utils.BaseUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private CategoryServiceImpl categoryService;
    @Resource
    private TagsService tagsService;
    @Resource
    private GoodsTagRelationService relationService;

    // 商品详情
    @Transactional
    public Goods getById(BigInteger id) {
        return goodsMapper.getById(id);
    }

    // 获取一条商品数据
    public Goods extractById(BigInteger id) {
        return goodsMapper.extractById(id);
    }


    // 商品列表
    public List<Goods> getAllGoodsInfo(String title, int page, int pageSize) {
        // 获取符合类目的 category_id 列表
        List<BigInteger> categoryIds = categoryService.selectIdByTitle(title);

        // StringBuilder 拼接 ID 列表
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < categoryIds.size(); i++) {
            sb.append(categoryIds.get(i));
            if (i < categoryIds.size() - 1) {
                sb.append(",");  // 逗号分隔
            }
        }
        // 获取拼接字符串-ids
        String ids = sb.toString();

        // System.out.println(ids);

        int offset = (page - 1) * pageSize;

        return goodsMapper.getAll(title, offset, pageSize, ids);
    }

    // 商品列表(连表方式）
    public List<GoodsDTO> getAllGoods(String title, int page, int pageSize) {


        int offset = (page - 1) * pageSize;

        return goodsMapper.getGoodsDtoAll(title, offset, pageSize);
    }


    // 商品数量
    public int getGoodsTotalForConsole(String title) {
        return goodsMapper.getGoodsTotalForConsole(title);
    }


    // 新增修改
    @Transactional
    public BigInteger edit(BigInteger id, BigInteger categoryId, String title, String goodsImages, Integer sales,
                           String goodsName, Integer price, String source,
                           Integer sevenDayReturn,String tagNames) {
//        try {
//            List<GoodsContentDto> checkContents = JSON.parseArray(content, GoodsContentDto.class);
//            for (GoodsContentDto checkContent : checkContents) {
//                if (!GoodsDefine.isArticleContentType(checkContent.getType())) {
//                    throw new RuntimeException("goods content is error");
//                }
//            }
//        } catch (Exception cause) {
//            // ignores
//            throw new RuntimeException("goods content is error");
//        }
        if (BaseUtils.isEmpty(title) || BaseUtils.isEmpty(goodsImages)) {
            throw new RuntimeException("goods title or goodsImages is error");
        }

//        // 参数校验
//        if (title == null || title.trim().isEmpty()) {
//            throw new RuntimeException("商品标题不能为空");
//        }
//
//        if (goodsImages == null || goodsImages.trim().isEmpty()) {
//            throw new RuntimeException("商品图片不能为空");
//        }

        if (sales == null || sales < 0) {
            throw new IllegalArgumentException("销量不能为负数");
        }
        if (goodsName == null || goodsName.trim().isEmpty()) {
            throw new RuntimeException("商品名称不能为空");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("价格不能为负数");
        }
        if (source == null || source.trim().isEmpty()) {
            throw new RuntimeException("来源不能为空");
        }
        if (sevenDayReturn == null || (sevenDayReturn != 0 && sevenDayReturn != 1)) {
            throw new IllegalArgumentException("七天退货字段取值只能为0或1");
        }


        // 校验类目id是否存在
        Category existCategoryId = categoryService.getById(categoryId);
        if (existCategoryId == null) {
            throw new IllegalArgumentException("类目id不存在");
        }

        Goods goods = new Goods();
        goods.setCategoryId(categoryId);
        goods.setTitle(title);
        goods.setGoodsImages(goodsImages);
        goods.setSales(sales);
        goods.setGoodsName(goodsName);
        goods.setPrice(price);
        goods.setSource(source);
        goods.setSevenDayReturn(sevenDayReturn);
//        goods.setGoodsDetails(content);

        List<BigInteger> tagIds = new ArrayList<>();

        // 解析标签
        String[] tags = tagNames.split("$");
        for (String tagName : tags) {
            // 查询标签是否存在
            Tag tag = tagsService.getTagByName(tagName);
            if (tag == null) {
                // 标签不存在，创建新标签
                tagsService.insert(tagName);
                // 插入后重新获取标签对象，确保能获取到ID
                return tag.getId();
            } else {
                // 标签已存在，直接添加ID
                tagIds.add(tag.getId());
            }

        }
        // 事务回滚
        if (tagIds.size() > 5) {
            throw new RuntimeException("不允许超过5个标签");
        }

        goods.setUpdatedTime(BaseUtils.currentSeconds());

        // 更新逻辑
        if (id != null) {
            // 判断id是否存在
            Goods existId = goodsMapper.getById(id);
            if (existId == null) {
                throw new RuntimeException("Id不存在，更新失败。");
            }
            goods.setId(id);
            goodsMapper.update(goods);

            // 获取商品当前已关联的标签ID列表
            List<BigInteger> existingTagIds = relationService.getByGoodsId(id);
            // 删除现有的标签关系中不再存在的标签
            for (BigInteger existingTagId : existingTagIds) {
                if (!tagIds.contains(existingTagId)) {
                    // 如果当前标签ID不在新的标签ID列表中，删除该关系
                    relationService.deleteRelation(id, existingTagId);
                }
            }

            // 建立新的标签关系
            for (BigInteger tagId : tagIds) {
                if (!existingTagIds.contains(tagId)) {
                    // 如果该标签ID之前没有和商品建立关系，插入新的关系
                    relationService.insert(id, tagId);
                }

                return id;
            }
        } else {

            // 新增逻辑
            goods.setCreatedTime(BaseUtils.currentSeconds());
            goods.setIsDeleted(0);

            try {
                goodsMapper.insert(goods);
            } catch (Exception cause) {
                throw new RuntimeException("error");
            }

            // 确保获取到插入商品后的 ID
            BigInteger goodsId = goods.getId();  // 获取新插入商品的 ID

            if (goodsId == null) {
                throw new RuntimeException("商品插入失败，未生成ID");
            }
        }

        return goods.getId();
    }



        // 删除商品
        public int deleteGoods (BigInteger id){
            return goodsMapper.delete(id, (int) (System.currentTimeMillis() / 1000));
        }

        // 商品类目里的所有商品

        public int deleteCategory (BigInteger id){
            return goodsMapper.deleteCategory(id, (int) (System.currentTimeMillis() / 1000));
        }
    }