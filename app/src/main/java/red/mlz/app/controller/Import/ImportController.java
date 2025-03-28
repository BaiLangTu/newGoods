package red.mlz.app.controller.Import;

import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import red.mlz.module.module.goods.entity.Category;
import red.mlz.module.module.goods.service.CategoryService;

import java.io.IOException;
import java.util.List;


/**
 * 导出类目表
 */
@RestController
public class ImportController {
    @Autowired
    private CategoryService service;

    @RequestMapping("/export/file")
    public String export() {
        List<Category> categories = service.getAll();
        String fileName = "categories.xlsx";
        EasyExcel.write(fileName, Category.class).sheet("类目列表").doWrite(categories);
        return "导出成功" + fileName;

    }

    /**
     * 导入类目表
     */
    @RequestMapping("/import/file")
    public String importFile(@RequestParam(name = "file") MultipartFile file) {

        if (file.isEmpty()) {
            return "文件为空，请选择文件上传！";
        }

        // 读取 Excel 文件
        List<Category> categoryList;  // 同步读取
        try {
            categoryList = EasyExcel.read(file.getInputStream())
                    .head(Category.class)
                    .sheet()
                    .doReadSync();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        // 处理导入的数据
        for (Category category : categoryList) {
            int result = service.insert(category.getParentId(),category.getName(), category.getImage());
            // 如果插入失败
            if (result <= 0) {
                return "导入失败，请检查w数据格式！";
            }
        }
        return "导入成功，共导入了" + categoryList.size() + "条数据";
    }



}
