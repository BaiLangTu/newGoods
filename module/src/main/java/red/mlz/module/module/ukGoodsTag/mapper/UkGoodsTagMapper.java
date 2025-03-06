package red.mlz.module.module.ukGoodsTag.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.ukGoodsTag.entity.UkGoodsTag;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface UkGoodsTagMapper {

// 获取类目列表
@Select("SELECT * FROM uk_goods_tag WHERE is_deleted=0")
List<UkGoodsTag> getAll();


// 根据ID查询操作
@Select("SELECT * FROM uk_goods_tag WHERE id =  #{id} AND is_deleted=0")
List<UkGoodsTag> getById(@Param("id")BigInteger id);


@Select("SELECT * FROM uk_goods_tag WHERE goods_id =  #{goodsId} AND is_deleted=0")
List<UkGoodsTag> getByGoodsId(@Param("id")BigInteger id);

// 根据ID提取操作
@Select("SELECT * FROM uk_goods_tag WHERE id =  #{id}")
UkGoodsTag extractById(@Param("id")BigInteger id);

// 插入操作
int insert(@Param("ukGoodsTag") UkGoodsTag ukGoodsTag);


// 更新操作
int update(@Param("ukGoodsTag") UkGoodsTag ukGoodsTag);

   
@Update("UPDATE uk_goods_tag SET updated_time = #{time}, is_deleted = 1 WHERE goods_id = #{goodsId} AND tag_id = #{tagId}")
int delete(@Param("goodsId") BigInteger goodsId, @Param("tagId") List<UkGoodsTag> tagId, @Param("time") Integer time);



}