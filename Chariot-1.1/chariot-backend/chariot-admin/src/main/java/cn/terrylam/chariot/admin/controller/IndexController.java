package cn.terrylam.chariot.admin.controller;

import cn.terrylam.chariot.admin.service.ResourceService;
import cn.terrylam.chariot.base.entity.system.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class IndexController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = {"/admin/", "/admin/index"})
    public String index(Model model) {

        List<Resource> allResource = resourceService.getAllResource();
        model.addAttribute("allResource", allResource);
        return "admin/index";
    }

    @RequestMapping(value = {"/admin/welcome"})
    public String welcome(Model model) {

        return "admin/welcome";
    }

    @RequestMapping(value = {"/admin/permission/died"})
    public String noPrivilege(Model model) {

        return "admin/error403";
    }
}
