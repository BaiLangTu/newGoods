package red.mlz.console.controller.goods;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.console.domain.BaseContentValueVo;
import red.mlz.console.domain.BaseListVo;
import red.mlz.console.domain.goods.*;
import red.mlz.module.module.goods.entity.Category;
import red.mlz.module.module.goods.entity.Goods;
import red.mlz.module.module.goods.service.GoodsService;
import red.mlz.module.module.goods.service.impl.CategoryServiceImpl;
import red.mlz.module.utils.BaseUtils;
import red.mlz.module.utils.Response;
import red.mlz.module.utils.SpringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryServiceImpl categoryService;


    @RequestMapping("goods/console_list")
    public Response getConsoleAll(@RequestParam(name = "keyword",required = false) String keyword,
                                  @RequestParam(name = "page", defaultValue = "1") int page) {



        // 获取商品数据

        String pageSize = SpringUtils.getProperty("application.pagesize");
        List<Goods> consoleList = goodsService.getAllGoodsInfo(keyword,page,Integer.parseInt(pageSize));
        int total = goodsService.getGoodsTotalForConsole(keyword);

        BaseListVo result = new BaseListVo();
        result.setTotal(total);
        result.setPageSize(Integer.valueOf(pageSize));

        // 创建商品展示对象列表

        List<GoodsListVo> consoleItemVoList = new ArrayList<>();

        for (Goods goods : consoleList) {
            GoodsListVo consoleItemVo = new GoodsListVo();

            if(!BaseUtils.isEmpty(goods.getGoodsImages())){
                String[] images = goods.getGoodsImages().split("\\$");
                consoleItemVo.setGoodImage(images[0]);
            }

            consoleItemVo.setId(goods.getId())
                    .setTitle(goods.getTitle())
                    .setSales(goods.getSales())
                    .setPrice(goods.getPrice());
            consoleItemVoList.add(consoleItemVo);
        }
        result.setList(consoleItemVoList);


        return new Response<>(1001, result);
    }

    @RequestMapping("/goods/console_info")
    public Response consoleInfoVo(@RequestParam(name = "goodsId") BigInteger goodsId) {

        Goods goods = goodsService.getById(goodsId);
//        if (BaseUtils.isEmpty(goods)) {
//            return new Response(4004);
//        }

        GoodsInfoVo consoleInfo = new GoodsInfoVo();

        // 设置商品图片轮播图，将商品图片字符串转换为 List
        String[] imagesArray = goods.getGoodsImages().split("\\$");
        consoleInfo.setGoodsImages(Arrays.asList(imagesArray));

        consoleInfo.setPrice(goods.getPrice());
        consoleInfo.setSales(goods.getSales());
        consoleInfo.setGoodsName(goods.getGoodsName());
        consoleInfo.setSevenDayReturn(goods.getSevenDayReturn());
        try {
            List<BaseContentValueVo> contents = JSON.parseArray(goods.getGoodsDetails(), BaseContentValueVo.class);
            consoleInfo.setContent(contents);
        } catch (Exception cause) {
            // ignores
            return new Response(4004);
        }
        consoleInfo.setCreatedTime(BaseUtils.timeStamp2Date(goods.getCreatedTime()));
        consoleInfo.setUpdatedTime(BaseUtils.timeStamp2Date(goods.getUpdatedTime()));

        return new Response<>(1001, consoleInfo);

    }

    @RequestMapping("/goods/console/categories/tree")
    public Response categoryTree() {


        // 获取所有类目
        List<Category> categories = categoryService.getAll();


        List<CategoryVO> categoryList = buildCategoryTree(categories,null);

        // 封装返回结果
        CategoryTree categoryTree = new CategoryTree();
        categoryTree.setData(categoryList);

        return new Response<>(1001, categoryTree);
    }

    // 递归构建类目树
    private List<CategoryVO> buildCategoryTree(List<Category> categories, BigInteger parentId) {
        List<CategoryVO> result = new ArrayList<>();
        for (Category category : categories) {
            if (category.getParentId() == null && parentId == null || category.getParentId() != null && category.getParentId().equals(parentId)) {
                // 构建子类目树
                CategoryVO categoryVO = new CategoryVO();
                categoryVO.setId(category.getId());
                categoryVO.setName(category.getName());
                categoryVO.setChildren(buildCategoryTree(categories, category.getId()));  // 递归获取子类目
                result.add(categoryVO);
            }
        }
            return result;

        }

    @RequestMapping("/goods/add")
    public Response addGoods(@RequestParam(name = "goodsId" ,required = false ) BigInteger goodsId,
                              @RequestParam(name = "categoryId") BigInteger categoryId,
                              @RequestParam(name = "title") String title,
                              @RequestParam(name = "goodsImages") String goodsImages,
                              @RequestParam(name = "sales") Integer sales,
                              @RequestParam(name = "goodsName") String goodsName,
                              @RequestParam(name = "price") Integer price,
                              @RequestParam(name = "source") String source,
                              @RequestParam(name = "sevenDayReturn") Integer sevenDayReturn,
                              @RequestParam(name = "goodsDetails",required = false) String goodsDetails,
                              @RequestParam(name = "tagsName") String tagsName){


        if (BaseUtils.isEmpty(categoryId)) {
            return new Response(3051);
        }
        //parameters check
        title = title.trim();
        if (BaseUtils.isEmpty(title)) {
            return new Response(3051);
        }

        if (!BaseUtils.isEmpty(categoryId)) {
            Category category = categoryService.getById(categoryId);
            if (BaseUtils.isEmpty(category)){
                return new Response(3052);
            }
        }

        try {
            BigInteger result = goodsService.edit(goodsId,categoryId,title,goodsImages,sales,goodsName,price,source,sevenDayReturn,goodsDetails,tagsName);

            return new Response(1001,result);
        } catch (Exception exception) {
            return new Response(4004);

        }

    }


    @RequestMapping("/goods/update")
    public Response updateGoods(  @RequestParam(name = "goodsId") BigInteger goodsId,
                                  @RequestParam(name = "categoryId") BigInteger categoryId,
                                  @RequestParam(name = "title") String title,
                                  @RequestParam(name = "goodsImages") String goodsImages,
                                  @RequestParam(name = "sales") Integer sales,
                                  @RequestParam(name = "goodsName") String goodsName,
                                  @RequestParam(name = "price") Integer price,
                                  @RequestParam(name = "source") String source,
                                  @RequestParam(name = "sevenDayReturn") Integer sevenDayReturn,
                                  @RequestParam(name = "goodsDetails") String goodsDetails,
                                  @RequestParam(name = "tags",required = false) String tagsName) {


        if (!BaseUtils.isEmpty(categoryId)) {
            Category category = categoryService.getById(categoryId);
            if (BaseUtils.isEmpty(category)){
                return new Response(3052);
            }
        }
        if (BaseUtils.isEmpty(goodsId)) {
            return new Response(3051);
        }
        //parameters check
        title = title.trim();
        if (BaseUtils.isEmpty(title)) {
            return new Response(3051);
        }

        // 调用 service 层修改商品
        ConsoleVo consoleVo = new ConsoleVo();
        try {

            BigInteger result = goodsService.edit(goodsId, categoryId, title.trim(), goodsImages, sales, goodsName.trim(), price, source.trim(), sevenDayReturn, goodsDetails.trim(),tagsName);

            consoleVo.setId(result.toString());
            return new Response(1001, consoleVo);

        } catch (Exception exception) {
            return new Response(4004);
        }

    }

    @RequestMapping("/goods/delete")
    public Response goodsDelete (@RequestParam(name = "goodsId") BigInteger goodsId) {

        if (BaseUtils.isEmpty(goodsId)) {
            return new Response(4004);
        }
        try {
            goodsService.deleteGoods(goodsId);
            return new Response(1001);
        } catch (Exception exception) {
            return new Response(4004);
        }

    }

    }


