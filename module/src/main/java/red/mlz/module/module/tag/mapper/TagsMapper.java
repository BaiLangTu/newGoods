
package red.mlz.module.module.tag.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.tag.entity.Tags;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TagsMapper {

// 获取标签列表
@Select("SELECT * FROM tags WHERE is_deleted=0")
List<Tags> getAll();


// 根据ID查询操作
@Select("SELECT * FROM tags WHERE id =  #{id} AND is_deleted=0")
Tags getById(@Param("id")BigInteger id);

// 查询标签
@Select("SELECT * FROM tags WHERE name = #{name} AND is_deleted = 0")
Tags getTagByName(String name);

// 根据ID提取操作
@Select("SELECT * FROM tags WHERE id =  #{id}")
Tags extractById(@Param("id")BigInteger id);

// 插入操作
int insert(@Param("category") Tags tags);

// 更新操作
int update(@Param("tags") Tags tags);

// 删除操作
@Update("UPDATE tags SET updated_time = #{time} , is_deleted = 1 WHERE id = #{id}")
int delete(@Param("id")BigInteger id,@Param("time") Integer time );


}