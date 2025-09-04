package com.example.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Order;
import com.example.entity.Sizenum;
import com.example.service.OrderService;
import com.example.service.SizenumService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/sizenum")
public class SizenumController {

    @Resource
    private SizenumService sizenumService;
    @Resource
    private OrderService orderService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Sizenum sizenum) {
        sizenumService.add(sizenum);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        sizenumService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        sizenumService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Sizenum sizenum) {
        sizenumService.updateById(sizenum);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Sizenum sizenum = sizenumService.selectById(id);
        return Result.success(sizenum);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Sizenum sizenum) {
        List<Sizenum> list = sizenumService.selectAll(sizenum);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(HttpServletRequest request,Sizenum sizenum,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        String role = request.getAttribute("role").toString();
        Integer id = Integer.parseInt(request.getParameter("userId"));
        if("FACTORY".equals(role)){
            sizenum.setFactoryId(id);
        }else if("STAFF".equals(role)){
            sizenum.setStaffId(id);
        }
        PageInfo<Sizenum> page = sizenumService.selectPage(sizenum, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);

        List<Sizenum> list = sizenumService.selectAll(null);// 查询出当前User表的所有数据
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("配码信息表", "UTF-8") + ".xlsx");
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
        List<Sizenum> list = reader.readAll(Sizenum.class);
        // 写入数据到数据库
        try {
            for (Sizenum sizenum : list) {
                sizenumService.add(sizenum);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Result.error(ResultCodeEnum.DATA_IMPORT_ERROR);
        }
        return Result.success();
    }

}

