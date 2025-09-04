package com.example.mapper;

import com.example.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OrderMapper {

    /**
     * 新增
     */
    int insert(Order order);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Order order);


    /**
     * 根据ID查询
     */
    Order selectById(Integer id);

    /**
     * 查询所有
     */
    List<Order> selectAll(Order order);

    Order selectByOrderId(@Param("orderId")String orderId);

    int updateByOrderNo(@Param("orderNo") String orderNo, @Param("execId")Integer execId);

    List<Order> selectAllByStaffId(Integer staffId);

    void updateStatus(String orderNo);

    Order checkProdSta(String orderNo);

    List<Map<String, Object>> getTotalSalesForCurrentYear();

}
