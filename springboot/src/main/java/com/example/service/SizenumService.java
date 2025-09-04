package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Order;
import com.example.entity.Sizenum;
import com.example.exception.CustomException;
import com.example.mapper.OrderMapper;
import com.example.mapper.SizenumMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SizenumService {

    @Resource
    private SizenumMapper sizenumMapper;

    @Resource
    private OrderService orderService;

    /**
     * 新增
     */
    public void add(Sizenum sizenum) {
        sizenumMapper.insert(sizenum);

        //核对商品总数
//        Order order =orderService.selectByOrderId(sizenum.getOrderNo());
//        int num = order.getQuantity() - sizenum.getStotal();
//        if (num<0){
//            throw new CustomException(ResultCodeEnum.TOTAL_NUM_LIMIT);
//        }
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        sizenumMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            sizenumMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Sizenum sizenum) {
        sizenumMapper.updateById(sizenum);
    }

    /**
     * 根据ID查询
     */
    public Sizenum selectById(Integer id) {
        return sizenumMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Sizenum> selectAll(Sizenum sizenum) {
        return sizenumMapper.selectAll(sizenum);
    }

    /**
     * 分页查询
     */
    public PageInfo<Sizenum> selectPage(Sizenum sizenum, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.STAFF.name().equals(currentUser.getRole())) {
            sizenum.setStaffId(currentUser.getId());
        }
        if (RoleEnum.FACTORY.name().equals(currentUser.getRole())) {
            sizenum.setFactoryId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Sizenum> list = sizenumMapper.selectAll(sizenum);
        return PageInfo.of(list);
    }

}

