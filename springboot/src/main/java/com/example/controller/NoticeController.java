package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Notice;
import com.example.entity.Order;
import com.example.entity.Staff;
import com.example.service.LogsService;
import com.example.service.NoticeService;
import com.example.service.StaffService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 公告信息表前端操作接口
 **/
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;
    @Resource
    private StaffService staffService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);

        //记录新增公告日志
        Account currentUser = TokenUtils.getCurrentUser();
        LogsService.recordLog("新增" + notice.getOrderNo() + "生产问题","新增",currentUser.getUsername());

        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        noticeService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        noticeService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Notice notice,HttpServletRequest request) {
        noticeService.updateById(notice);

        //修改生产问题状态系统日志
        String role = request.getAttribute("role").toString();
        Account currentUser = TokenUtils.getCurrentUser();
        if("STAFF".equals(role)) {
            LogsService.recordLog("修改" + notice.getOrderNo() + "生产问题状态", "修改状态", currentUser.getUsername());
        }else if("ADMIN".equals(role)) {
            LogsService.recordLog("修改" + notice.getOrderNo() + "生产问题状态", "修改状态", currentUser.getUsername());
        }

        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Notice notice = noticeService.selectById(id);
        return Result.success(notice);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(HttpServletRequest request,Notice notice ) {
        Object role = request.getAttribute("role");
        if(role!=null && "ADMIN".equals(role.toString())){
            List<Notice> list = noticeService.selectAll(notice);
            return Result.success(list);
        }else{
            Staff staff = staffService.selectById(Integer.parseInt(request.getAttribute("userId").toString()));
            notice.setStaff(staff.getName());
            List<Notice> list = noticeService.selectAll(notice);
            return Result.success(list);
        }
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Notice notice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Notice> page = noticeService.selectPage(notice, pageNum, pageSize);
        return Result.success(page);
    }

}