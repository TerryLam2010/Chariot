package cn.terrylam.chariot.admin.controller.system;

import cn.terrylam.chariot.admin.controller.BaseController;
import cn.terrylam.chariot.admin.controller.system.form.ResourcePageForm;
import cn.terrylam.chariot.admin.service.ResourceService;
import cn.terrylam.chariot.base.entity.system.Resource;
import cn.terrylam.chariot.base.util.ZTreeObjectUtil;
import cn.terrylam.framework.enums.XAdminCodeEnum;
import cn.terrylam.framework.exception.ObjectNotFoundException;
import cn.terrylam.framework.util.Pager;
import cn.terrylam.framework.util.XAdmindResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseController {
	@Autowired
	private ResourceService resourceService;

    @RequestMapping("/index")
    public String index() {
        return "admin/resource/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public XAdmindResult list(ResourcePageForm resourcePageForm) {
        Pager<Resource> pager = resourceService.pageByName(resourcePageForm);
        return XAdmindResult.build(XAdminCodeEnum.SUCCESS, pager.getResult(), pager.getTotalRow());
    }

    @RequestMapping("form/{id}")
    public String form(@PathVariable Integer id,Model model){
        if(id!=null&&id!=0){
            Resource resource =resourceService.findById(id);
            model.addAttribute("formData",resource);
        }
        return "admin/resource/form";
    }

    @RequestMapping(value= {"/save"}, method = RequestMethod.POST)
	@ResponseBody
	public XAdmindResult edit(Resource resource, ModelMap map){
		try {
			resourceService.createOrUpdate(resource);
		} catch (Exception e) {
			return  XAdmindResult.build(XAdminCodeEnum.ERROR,e.getMessage());
		}
		return XAdmindResult.success();
	}
    @RequestMapping("treeResources")
    @ResponseBody
    public ZTreeObjectUtil.Ztree zTree(){
        ZTreeObjectUtil zTreeObjectUtil=ZTreeObjectUtil.getInstance();
        List<Resource> resources=resourceService.list();
        List<ZTreeObjectUtil.SimpleData> datas=new ArrayList<>();
        for(Resource r:resources){
            datas.add( zTreeObjectUtil.new SimpleData(String.valueOf(r.getId()),r.getName(),true,false,String.valueOf(r.getParentId())));
        }
        return zTreeObjectUtil.getByRoot(zTreeObjectUtil.new Ztree("0","无",true,false),datas);
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public XAdmindResult delete(@PathVariable Integer id,ModelMap map) {
        try {
            Resource resource = resourceService.findById(id);
            if(resource == null){
                throw new ObjectNotFoundException(resource.getClass());
            }
            resourceService.delete(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return XAdmindResult.build(XAdminCodeEnum.ERROR);
        }
        return XAdmindResult.build(XAdminCodeEnum.SUCCESS);
    }
    @RequestMapping("deleteBatch")
    @ResponseBody
    public XAdmindResult deleteBatch(@RequestParam(value = "ids[]")   String[] ids){
        try {
            resourceService.deleteBatch(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return XAdmindResult.build(XAdminCodeEnum.ERROR);
        }
        return XAdmindResult.build(XAdminCodeEnum.SUCCESS);
    }
    @RequestMapping("updateIsHide")
    @ResponseBody
    public XAdmindResult updateIsHide(long id ,boolean isHide){
        resourceService.updateIsHide(id,isHide);
            return XAdmindResult.build(XAdminCodeEnum.SUCCESS);
    }
}
