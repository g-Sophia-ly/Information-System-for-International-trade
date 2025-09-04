package com.example.mapper;

import com.example.entity.Goods;
import com.example.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作goods相关数据接口
 */
public interface GoodsMapper {

    /**
     * 新增
     */
    int insert(Goods goods);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Goods goods);

    /**
     * 根据ID查询
     */
    Goods selectById(Integer id);

    /**
     * 查询所有
     */
    List<Goods> selectAll(Goods goods);

    Goods selectByGoodsNo(@Param("goodsNo")String GoodsNo);


    /**
     * 根据good_no修改
     */
    int updateByGoodNo(Goods goods);

}
