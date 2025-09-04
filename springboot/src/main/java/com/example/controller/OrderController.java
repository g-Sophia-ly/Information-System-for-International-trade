package com.example.controller;

import cn.hutool.core.lang.Dict;
import com.example.common.enums.ResultCodeEnum;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.entity.Order;
import com.example.service.GoodsService;
import com.example.service.LogsService;
import com.example.service.OrderService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private GoodsService goodsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Order order) {
        order.setStatus("未出货");
        order.setFstatus("未完成");
        orderService.add(order);

        //记录新增订单日志
        Account currentUser = TokenUtils.getCurrentUser();
        LogsService.recordLog("新增订单" + order.getOrderNo(),"新增",currentUser.getUsername());

        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    @Transactional
    public Result deleteById(@PathVariable Integer id, Order order) {

//        //记录删除订单日志
//        Account currentUser = TokenUtils.getCurrentUser();
//        Order order2 = orderService.selectById(id);
//        LogsService.recordLog("删除订单" + order2.getOrderNo(),"删除",currentUser.getUsername());

        Order order1 = orderService.selectById(id);
        orderService.deleteById(id, order);
        Goods goods = goodsService.selectByGoodsNo(order1.getGoodNo());
        goods.setPtotal(goods.getPtotal() - order1.getPrice() * order1.getQuantity());
        goods.setStotal(goods.getStotal() - order1.getQuantity());
        goods.setGoodsNo(order1.getGoodNo());
        goodsService.updateByGoodNo(goods);

        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            Order order = orderService.selectById(id);
            Goods goods = goodsService.selectByGoodsNo(order.getGoodNo());
            goods.setPtotal(goods.getPtotal() - order.getPrice() * order.getQuantity());
            goods.setStotal(goods.getStotal() - order.getQuantity());
            goods.setGoodsNo(order.getGoodNo());
            goodsService.updateByGoodNo(goods);
        }
        orderService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Order order,HttpServletRequest request) {
        Order orderParam = orderService.selectById(order.getId());
        Goods goods = goodsService.selectByGoodsNo(order.getGoodNo());

        //修改生产问题状态系统日志
        String role = request.getAttribute("role").toString();
        Account currentUser = TokenUtils.getCurrentUser();
        if("FACTORY".equals(role)) {
            LogsService.recordLog("更新" + orderParam.getOrderNo()+ "生产状态", "更新状态", currentUser.getUsername());
        }

        goods.setStotal(goods.getStotal() - orderParam.getQuantity());
        goods.setPtotal(goods.getPtotal() - orderParam.getQuantity() * orderParam.getPrice());
        goods.setStotal(goods.getStotal() + order.getQuantity());
        goods.setPtotal(goods.getPtotal() + order.getQuantity() * order.getPrice());
        goodsService.updateById(goods);
        orderService.updateById(order);
        return Result.success();
    }


    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Order order = orderService.selectById(id);
        return Result.success(order);
    }

    @GetMapping("/selectByOrderId/{orderNoOld}")
    public Result selectByOrderId(@PathVariable("orderNoOld") String OrderId) {
        //System.out.println("------------------"+id);
        Order order = orderService.selectByOrderId(OrderId);
        return Result.success(order);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Order order) {
        System.out.println(order.getStaffId() + "----------------");
        List<Order> list = orderService.selectAll(order);
        return Result.success(list);
    }

    @GetMapping("/selectAllByStaffId")
    public Result selectAllByStaffId() {
        Account currentUser = TokenUtils.getCurrentUser();
        Integer id = currentUser.getId();
        System.out.println(id + "----------------");
        List<Order> list = orderService.selectAllByStaffId(id);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(HttpServletRequest request,Order order,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        String role = request.getAttribute("role").toString();
        //Integer id = Integer.parseInt(request.getParameter("userId"));
        Account currentUser = TokenUtils.getCurrentUser();
        Integer id = currentUser.getId();
        if("FACTORY".equals(role)){
            order.setFactoryId(id);
        }else if("STAFF".equals(role)){
            order.setStaffId(id);
        }
        PageInfo<Order> page = orderService.selectPage(order, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);

        List<Order> list = orderService.selectAll(null);// 查询出当前User表的所有数据
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("订单信息表", "UTF-8") + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.flush();
        writer.close();
        outputStream.close();
    }


    /**
     * 批量导入
     *
     * @param file 传入的excel文件对象
     * @return 导入结果
     */
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Order> list = reader.readAll(Order.class);
        // 写入数据到数据库
        try {
            for (Order order : list) {
                orderService.add(order);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Result.error(ResultCodeEnum.DATA_IMPORT_ERROR);
        }
        return Result.success();
    }

    @GetMapping("/updateExec/{orderNo}/{execId}")
    public void updateExec(@PathVariable("orderNo") String orderNo, @PathVariable("execId") Integer execId) {
        System.out.println("====================================" + orderNo);
        orderService.updateExec(orderNo, execId);
    }

    @GetMapping("/updateStatus/{orderNo}")
    public void updateStatus(@PathVariable("orderNo") String orderNo) {
        orderService.updateStatus(orderNo);
    }
    @GetMapping("/checkProdSta/{orderNo}")
    public Order checkProdSta(@PathVariable("orderNo") String orderNo) {
        return orderService.checkProdSta(orderNo);
    }


    /**
     * 查询柱状图数据
     */
    @GetMapping("/selectPie")
    public Result selectBar() {
        List<Dict> dictList = orderService.selectPie();
        return Result.success(dictList);
    }

}

