package com.jy.controller.system.company;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.company.Company;
import com.jy.entity.system.dict.DataDict;
import com.jy.entity.system.org.Role;
import com.jy.service.company.CompanyService;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.org.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author gsw on 16-7-19
 * @version 1.0
 */
@Controller
@RequestMapping("/backstage/company/")
public class CompanyController extends BaseController<Company> {

  @Autowired
  public CompanyService service;
  @Autowired
  public RoleService roleService;
  @Autowired
  private AccountService accountService;

  /**
   * 系统字典首页
   */
  @RequestMapping("index")
  public String index(Model model) {
    if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
      model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
      return "/system/company/list";
    }
    return Const.NO_AUTHORIZED_URL;
  }

  @RequestMapping(value = "getCompanySelect", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes getDictSelect(String ids) {
    AjaxRes ar = getAjaxRes();
    try {
      List<Company> obj = service.find(new Company());
      Map<String, List<Company>> map = new HashMap<String, List<Company>>();
      map.put(ids, obj);
      ar.setSucceed(map);
    } catch (Exception e) {
      logger.error(e.toString(), e);
      ar.setFailMsg(Const.DATA_FAIL);
    }
    return ar;
  }


  @RequestMapping(value = "addAdmin", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes addAdmin(Role o) {
    Role newRole=roleService.find(o).get(0);
    Account account=new Account();
    account.setLoginName("admin");
    account.setName("管理员");
    account.setIsValid(1);
    account.setCompany(newRole.getOrgId());
    account.setRoleId(newRole.getId());
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
      try {
        account.setAccountId(get32UUID());
        int res = accountService.insertAdminAccount(account);
        if (res == 1) ar.setSucceedMsg("admin账户生成成功!");
        else ar.setFailMsg("admin账户已存在");
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.SAVE_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value = "findByPage", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes findByPage(Page<Company> page, Company o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/company/index"))) {
      try {
        Page<Company> result = service.findByPage(o, page);
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
        p.put("list", result);
        ar.setSucceed(p);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value = "add", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes add(Company o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
      try {
        o.setId(get32UUID());
        service.insert(o);
        ar.setSucceedMsg(Const.SAVE_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.SAVE_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value = "find", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes find(Company o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
      try {
        List<Company> list = service.find(o);
        Company obj = list.get(0);
        ar.setSucceed(obj);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DATA_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value = "update", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes update(Company o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
      try {
//        o.setUpdateTime(new Date());
        service.update(o);
        ar.setSucceedMsg(Const.UPDATE_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.UPDATE_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value = "del", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes del(Company o) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
      try {
        service.delete(o);
        ar.setSucceedMsg(Const.DEL_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DEL_FAIL);
      }
    }
    return ar;
  }

  @RequestMapping(value = "delBatch", method = RequestMethod.POST)
  @ResponseBody
  public AjaxRes delBatch(String chks) {
    AjaxRes ar = getAjaxRes();
    if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
      try {
        String[] chk = chks.split(",");
        List<Company> list = new ArrayList<Company>();
        for (String s : chk) {
          Company sd = new Company();
          sd.setId(s);
          list.add(sd);
        }
        service.deleteBatch(list);
        ar.setSucceedMsg(Const.DEL_SUCCEED);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        ar.setFailMsg(Const.DEL_FAIL);
      }
    }
    return ar;
  }
}
