package com.ruoyi;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.mail.domain.WcStudent;
import com.ruoyi.mail.service.IWcStudentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Controller
 * 
 * @author ruoyi
 * @date 2020-09-25
 */
@Controller
@RequestMapping("/mail/stu")
public class WcStudentController extends BaseController
{
    private String prefix = "mail/stu";

    @Autowired
    private IWcStudentService wcStudentService;

    @RequiresPermissions("mail:stu:view")
    @GetMapping()
    public String stu()
    {
        return prefix + "/stu";
    }

    /**
     * 查询学生信息列表
     */
    @RequiresPermissions("mail:stu:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WcStudent wcStudent)
    {
        startPage();
        List<WcStudent> list = wcStudentService.selectWcStudentList(wcStudent);
        return getDataTable(list);
    }

    /**
     * 导出学生信息列表
     */
    @RequiresPermissions("mail:stu:export")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WcStudent wcStudent)
    {
        List<WcStudent> list = wcStudentService.selectWcStudentList(wcStudent);
        ExcelUtil<WcStudent> util = new ExcelUtil<WcStudent>(WcStudent.class);
        return util.exportExcel(list, "stu");
    }

    /**
     * 新增学生信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存学生信息
     */
    @RequiresPermissions("mail:stu:add")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WcStudent wcStudent)
    {
        return toAjax(wcStudentService.insertWcStudent(wcStudent));
    }

    /**
     * 修改学生信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        WcStudent wcStudent = wcStudentService.selectWcStudentById(id);
        mmap.put("wcStudent", wcStudent);
        return prefix + "/edit";
    }

    /**
     * 修改保存学生信息
     */
    @RequiresPermissions("mail:stu:edit")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WcStudent wcStudent)
    {
        return toAjax(wcStudentService.updateWcStudent(wcStudent));
    }

    /**
     * 删除学生信息
     */
    @RequiresPermissions("mail:stu:remove")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(wcStudentService.deleteWcStudentByIds(ids));
    }
}
