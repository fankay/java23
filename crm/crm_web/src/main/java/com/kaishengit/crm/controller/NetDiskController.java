package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.dto.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网盘控制器
 */
@Controller
@RequestMapping("/disk")
public class NetDiskController extends BaseController {

    @Autowired
    private DiskService diskService;

    /**
     * 首页
     * @return
     */
    @GetMapping
    public String home(@RequestParam(required = false,defaultValue = "0",name = "_") Integer pId,
                       Model model) {
        List<Disk> diskList = diskService.findDiskByPid(pId);

        if(0 != pId) {
            //如果不是0，则是查看子目录或文件
            Disk disk = diskService.findById(pId);
            model.addAttribute("disk",disk);
        }

        model.addAttribute("diskList",diskList);
        return "disk/home";
    }

    /**
     * 新建文件夹
     */
    @PostMapping("/new/folder")
    @ResponseBody
    public AjaxResult saveNewFolder(Disk disk) {
        diskService.saveNewFolder(disk);
        List<Disk> diskList = diskService.findDiskByPid(disk.getpId());
        return AjaxResult.success(diskList);
    }
}
