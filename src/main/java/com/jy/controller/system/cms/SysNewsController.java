package com.jy.controller.system.cms;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.IPUtil;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.cms.SysNews;
import com.jy.entity.system.dict.SysDict;
import com.jy.service.system.cms.SysNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/backstage/cms/")
public class SysNewsController extends BaseController<SysNews> {

  @Autowired
  private SysNewsService service;

  @RequestMapping("index")
  public String index(Model model){
    if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/cms/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "findByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findByPage(Page<SysNews> page, SysNews o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/cms/index"))) {
      try {
        Page<SysNews> news = service.findByPage(o, page);
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
        p.put("list", news);
        ar.setSucceed(p);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }
  @RequestMapping(value="find", method=RequestMethod.POST)
  @ResponseBody
  public AjaxRes find(SysNews o){
    AjaxRes ar=getAjaxRes();
    if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
      try {
        List<SysNews> list= service.find(o);
        SysNews obj=list.get(0);
        ar.setSucceed(obj);
      } catch (Exception e) {
        logger.error(e.toString(),e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value="add", method=RequestMethod.POST)
  @ResponseBody
  public AjaxRes add(SysNews o){
    AjaxRes ar=getAjaxRes();
    if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
      try {
        String userId= AccountShiroUtil.getCurrentUser().getName();
        o.setId(get32UUID());
        o.setAddtime(new Date());
        o.setUptime(new Date());
        o.setPublisher(userId);
        service.insert(o);
        ar.setSucceedMsg(Const.SAVE_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(),e);
        ar.setFailMsg(Const.SAVE_FAIL);
      }
    }
    return ar;
  }
  @RequestMapping(value="update", method=RequestMethod.POST)
  @ResponseBody
  public AjaxRes update(SysNews o){
    AjaxRes ar=getAjaxRes();
    if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
      try {
        o.setUptime(new Date());
        service.update(o);
        ar.setSucceedMsg(Const.UPDATE_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(),e);
        ar.setFailMsg(Const.UPDATE_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value="del", method=RequestMethod.POST)
  @ResponseBody
  public AjaxRes del(SysNews o){
    AjaxRes ar=getAjaxRes();
    if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
      try {
        service.delete(o);
        ar.setSucceedMsg(Const.DEL_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(),e);
        ar.setFailMsg(Const.DEL_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value="delBatch", method=RequestMethod.POST)
  @ResponseBody
  public AjaxRes delBatch(String chks){
    AjaxRes ar=getAjaxRes();
    if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
      try {
        String[] chk =chks.split(",");
        List<SysNews> list=new ArrayList<SysNews>();
        for(String s:chk){
          SysNews sd=new SysNews();
          sd.setId(s);
          list.add(sd);
        }
        service.deleteBatch(list);
        ar.setSucceedMsg(Const.DEL_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(),e);
        ar.setFailMsg(Const.DEL_FAIL);
      }
    }
    return ar;
  }
}