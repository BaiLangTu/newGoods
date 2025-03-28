package red.mlz.console.controller.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.console.domain.goods.ConsoleVo;
import red.mlz.module.module.goods.entity.Category;
import red.mlz.module.module.goods.service.GoodsService;
import red.mlz.module.module.goods.service.CategoryService;
import red.mlz.module.utils.Response;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 类目表 前端控制器
 * </p>
 *
 * @author 小白-945
 * @since 2024-12-22
 */
@RestController
public class CategoryController {
     @Autowired
     private CategoryService service;

     @Autowired
     private GoodsService goodsService;

     @RequestMapping("/category/info")
     public Category categoryInfo (@RequestParam(name = "categoryId") BigInteger categoryId) {
          return service.getById(categoryId);
     }
     @RequestMapping("/category/all")
     public List<Category> categoryAll(){
          return service.getAll();
     }
     @RequestMapping("/category/add")
     public Response insertCategory(@RequestParam(name = "parentId")BigInteger parentId,@RequestParam(name = "name") String name, @RequestParam(name = "image") String image) {

          try {
               service.insert(parentId,name, image);
               return new Response<>(1001);

          } catch (Exception e) {
               return new Response<>(1002);
          }


     }
     @RequestMapping("/category/update")
     public Response updateCategory(@RequestParam(name = "categoryId") BigInteger categoryId,
                                  @RequestParam(name = "name") String name,
                                  @RequestParam(name = "image") String image) {

          try {

            service.update(categoryId, name, image);
            return new Response<>(1001);
          } catch (Exception e) {
               return new Response<>(4005);
          }

     }

     @RequestMapping("/category/delete")
     public Response delete (@RequestParam(name = "categoryId") BigInteger categoryId)
     {



          int category= service.delete(categoryId);

          int goods =goodsService.deleteGoods(categoryId);


          ConsoleVo consoleVo = new ConsoleVo();

          if( category == 1){
               if(goods == 1){
                    return new Response(1001);
               }
          } else {
               return new Response(3002);
          }
          return new Response(1001, consoleVo);
     }

}
