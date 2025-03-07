package red.mlz.module.module.GoodsTagRelation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import red.mlz.module.module.GoodsTagRelation.entity.GoodsTagRelation;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface GoodsTagRelationMapper {

// 获取标签列表
@Select("SELECT * FROM goods_tag_relation WHERE is_deleted=0")
List<GoodsTagRelation> getAll();


// 根据ID查询操作
@Select("SELECT * FROM goods_tag_relation WHERE id =  #{id} AND is_deleted=0")
GoodsTagRelation getById(@Param("id")BigInteger id);

// 根据ID提取操作
@Select("SELECT * FROM goods_tag_relation WHERE id =  #{id}")
GoodsTagRelation extractById(@Param("id")BigInteger id);

// 插入操作
int insert(@Param("goodsTagRelation") GoodsTagRelation goodsTagRelation);

// 更新操作
int update(@Param("goodsTagRelation") GoodsTagRelation goodsTagRelation);



@Select("SELECT * FROM goods_tag_relation WHERE goods_id =  #{goodsId} AND is_deleted=0")
List<GoodsTagRelation> getByGoodsId(@Param("goodsId") BigInteger goodsId);


@Update("UPDATE goods_tag_relation SET updated_time = #{time}, is_deleted = 1 WHERE goods_id = #{goodsId} AND tag_id = #{tagId}")
int delete(@Param("goodsId") BigInteger goodsId, @Param("tagId") List<GoodsTagRelation> tagId, @Param("time") Integer time);


}