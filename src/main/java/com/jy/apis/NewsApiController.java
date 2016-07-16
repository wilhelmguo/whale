package com.jy.apis;
//
//import com.jy.common.ajax.AjaxRes;
//import com.jy.common.mybatis.Page;
//import com.jy.common.utils.base.Const;
//import com.jy.controller.base.BaseController;
//import com.jy.entity.system.cms.SysNews;
//import com.jy.service.system.cms.SysNewsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author gsw on 16-7-15
// * @version 1.0
// */
//@RestController
//@RequestMapping("/api/v1/cms")
//public class NewsApiController extends BaseController<SysNews> {
//  @Autowired
//  private SysNewsService service;
//  @RequestMapping(value = "findByPage", method = RequestMethod.POST)
//  @ResponseBody
//  public AjaxRes findByPage(@RequestBody Page<SysNews> page, SysNews o) {
//    AjaxRes ar = getAjaxRes();
//      try {
//        Page<SysNews> news = service.findByPage(o, page);
//        Map<String, Object> p = new HashMap<String, Object>();
//        p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
//        p.put("list", news);
//        ar.setSucceed(p);
//      } catch (Exception e) {
//        logger.error(e.toString(), e);
//        ar.setFailMsg(Const.DATA_FAIL);
//      }
//    return ar;
//  }
//}
