package cn.terrylam.chariot.admin.controller.system;


import cn.terrylam.chariot.admin.controller.BaseController;
import cn.terrylam.chariot.admin.controller.system.form.RolePageForm;
import cn.terrylam.chariot.admin.service.ResourceService;
import cn.terrylam.chariot.admin.service.RoleService;
import cn.terrylam.chariot.base.entity.system.Resource;
import cn.terrylam.chariot.base.entity.system.Role;
import cn.terrylam.chariot.base.util.IgnorePropertiesUtil;
import cn.terrylam.framework.enums.ResponseCodeEnum;
import cn.terrylam.framework.enums.XAdminCodeEnum;
import cn.terrylam.framework.exception.AppException;
import cn.terrylam.framework.exception.ObjectNotFoundException;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.framework.util.ResponseResult;
import cn.terrylam.framework.util.XAdmindResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;


    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "admin/role/index";
    }

    @RequestMapping(value = {"/list"})
    @ResponseBody
    public XAdmindResult list(RolePageForm rolePageForm) {
        Pager<Role> pager = roleService.pager(rolePageForm);
        return XAdmindResult.build(XAdminCodeEnum.SUCCESS, pager.getResult(), pager.getTotalRow());
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "admin/role/add";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        return "admin/role/edit";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public XAdmindResult add(Role role) {
        roleService.create(role);
        return XAdmindResult.build(XAdminCodeEnum.SUCCESS);
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public XAdmindResult doEdit(Role role) throws AppException {
        role.setUpdateTime(new Date());
        Role roleDO = roleService.findById(role.getId());
        BeanUtils.copyProperties(role, roleDO, IgnorePropertiesUtil.getNullPropertyNames(role));
        roleService.update(roleDO);
        return XAdmindResult.build(XAdminCodeEnum.SUCCESS);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public XAdmindResult delete(@PathVariable Integer id, ModelMap map) {
        try {
            Role role = roleService.findById(id);
            if (role == null) {
                throw new ObjectNotFoundException(role.getClass());
            }
            roleService.delete(role);
        } catch (Exception e) {
            e.printStackTrace();
            return XAdmindResult.build(XAdminCodeEnum.SUCCESS);
        }
        return XAdmindResult.build(XAdminCodeEnum.SUCCESS);
    }

    @RequestMapping(value = "/resourceByRole/{id}", method = RequestMethod.GET)
    public String resourceByRole(@PathVariable Long id, Model model) {
        List<Resource> allResource = resourceService.getAllResource();
        Role role = roleService.findById(id);
        Set<Resource> resources = role.getResources();
        List<Long> resourceIds = new ArrayList<>();
        for (Resource resource : resources) {
            resourceIds.add(resource.getId());
        }
        List<JSONObject> jsonObjects = resourceService.treeResource(allResource, resourceIds);
        model.addAttribute("data", JSON.toJSONString(jsonObjects));
        model.addAttribute("roleId", id);
        return "admin/role/roleResource";
    }

    @RequestMapping(value = "/roleResouceEdit", method = RequestMethod.POST)
    @ResponseBody
    public XAdmindResult roleResouceEdit(@RequestParam(value = "roleId", defaultValue = "0") Long roleId,
                                         @RequestParam(value = "resourceIds", defaultValue = "0") String resourceIds
            , HttpServletRequest request) {
        resourceService.modifyRoleResource(roleId, resourceIds);
        return XAdmindResult.success();
    }

    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateStatus(@PathVariable long id, @RequestParam(value = "status", required = false) Boolean statusBool
            , HttpServletRequest request) {
        Role role = roleService.findById(id);
        if (statusBool != null) {
            int status = statusBool ? 1 : 0;
            role.setStatus(status);
        }
        role.setUpdateTime(new Date());
        try {
            roleService.update(role);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.build(ResponseCodeEnum.ERROR, e.getMessage());
        }
        return ResponseResult.success();
    }
}
