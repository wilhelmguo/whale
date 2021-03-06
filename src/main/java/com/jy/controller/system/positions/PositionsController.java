package com.jy.controller.system.positions;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.common.utils.webpage.PageData;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.company.Company;
import com.jy.entity.system.org.Org;
import com.jy.entity.system.org.Role;
import com.jy.entity.system.resources.Resources;
import com.jy.service.company.CompanyService;
import com.jy.service.system.org.OrgService;
import com.jy.service.system.org.RoleService;
import com.jy.service.system.resources.ResourcesService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.*;

/*
 * 角色管理
 */
@Controller
@RequestMapping("/backstage/org/positions/")
public class PositionsController extends BaseController<Role> {

    @Autowired
    public OrgService orgService;

    @Autowired
    public RoleService roleService;
    @Autowired
    public ResourcesService resourcesService;

    @Autowired
    public CompanyService companyService;

    private static final String SECURITY_URL = "/backstage/org/role/index";

    /**
     * 角色首页
     */
    @RequestMapping("index")
    public String index(Model model) throws UnsupportedEncodingException {
        if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/org/positions/list";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    @RequestMapping(value = "findOneRole", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes findOneRole(Page<Role> page, Role o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                Page<Role> roles = roleService.findOneRole(o, page);
                Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
                p.put("list", roles);
                ar.setSucceed(p);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "findByPage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes findByPage(Page<Role> page, Role o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                o.setCompany(getCompany());
                Page<Role> roles = roleService.findByPage(o, page);
                List<Role> result = new ArrayList<Role>();
                for (Role r : roles.getResults()) {
                    if (StringUtils.isNotBlank(r.getOrgId())) {
                        Org org = new Org();
                        org.setId(r.getOrgId());
                        List<Org> los = orgService.find(org);
                        if (CollectionUtils.isNotEmpty(los)) {
                            r.setOrgName(los.get(0).getName());
                        }
                    }
                    if (StringUtils.isNotBlank(r.getPid())) {
                        Role role = new Role();
                        role.setId(r.getPid());
                        List<Role> los = roleService.find(role);
                        if (CollectionUtils.isNotEmpty(los)) {
                            r.setpName(los.get(0).getName());
                        }
                    }
                    result.add(r);
                }
                roles.setResults(result);
                Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
                p.put("list", roles);
                ar.setSucceed(p);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }


    @RequestMapping(value = "companyAdd", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes companyAdd(Role o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
            try {
                Org org = new Org();
                if (StringUtils.isNotBlank(o.getOrgId())) {
                    org.setId(o.getOrgId());
                    List<Org> orgs = orgService.find(org);
                    if (orgs.size() > 0) {
                        Org pOrg = orgs.get(0);
                        String pId = pOrg.getpId();
                        if (StringUtils.isNotBlank(pId)) {
                            o.setId(get32UUID());
                            o.setCreateTime(new Date());
                            o.setCompany(o.getOrgId());
                            roleService.insert(o);
                            ar.setSucceedMsg(Const.SAVE_SUCCEED);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes add(Role o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
            try {
                if (StringUtils.isBlank(o.getPid())) {
                    o.setPid("0");
                }
                o.setId(get32UUID());
                o.setCreateTime(new Date());
                o.setCompany(getCompany());
                roleService.insert(o);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.SAVE_FAIL);
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
                List<Role> list = new ArrayList<Role>();
                for (String s : chk) {
                    Role sd = new Role();
                    sd.setId(s);
                    list.add(sd);
                }
                roleService.deleteBatch(list);
                ar.setSucceedMsg(Const.DEL_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DEL_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "find", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes find(Role o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                List<Role> list = roleService.find(o);
                if (CollectionUtils.isNotEmpty(list)) {
                    Role r = list.get(0);
                    if (StringUtils.isNotBlank(r.getOrgId())) {
                        Org org = new Org();
                        org.setId(r.getOrgId());
                        List<Org> los = orgService.find(org);
                        if (CollectionUtils.isNotEmpty(los)) {
                            r.setOrgName(los.get(0).getName());
                        }
                    }
                    if (StringUtils.isNotBlank(r.getPid())) {
                        Role role = new Role();
                        role.setId(r.getPid());
                        List<Role> los = roleService.find(role);
                        if (CollectionUtils.isNotEmpty(los)) {
                            r.setpName(los.get(0).getName());
                        }
                    }
                    ar.setSucceed(r);
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes update(Role o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                o.setUpdateTime(new Date());
                roleService.update(o);
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
    public AjaxRes del(Role o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                roleService.delete(o);
                ar.setSucceedMsg(Const.DEL_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DEL_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "listCompanyAuthorized", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes listCompanyAuthorized() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                PageData pd = this.getPageData();
                String roleId = pd.getString("id");
                String layer = pd.getString("layer");
                List<ZNodes> r = roleService.listCompanyAuthorized(roleId, layer);
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "listAuthorized", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes listAuthorized() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                PageData pd = this.getPageData();
                String roleId = pd.getString("id");
                String layer = pd.getString("layer");
                List<ZNodes> r = roleService.listAuthorized(roleId, layer);
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "saveAuthorized", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes saveAuthorized() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON, "/backstage/org/role/listCompanyAuthorized"))) {
            try {
                PageData pd = this.getPageData();
                String roleId = pd.getString("id");
                String aus = pd.getString("aus");
                String layer = pd.getString("layer");
                roleService.saveAuthorized(roleId, aus, layer);
                ar.setSucceedMsg(Const.UPDATE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.UPDATE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "listPositionAuthorized", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes listPositionAuthorized() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                PageData pd = this.getPageData();
                String roleId = pd.getString("id");
                List<ZNodes> all=resourcesService.findAllBaseResource(new Resources());
                List<ZNodes> r = roleService.listPositionAuthorized(roleId);
                List<ZNodes> result = new ArrayList<ZNodes>();
                for (ZNodes zNode:all){
                    for (ZNodes z:r){
                        if (z.getId().equals(zNode.getId())){
                            zNode.setChecked("true");
                        }
                    }
                    result.add(zNode);
                }
                ar.setSucceed(result);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "getSuperUserOrgTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes getSuperUserOrgTree() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {

                String userName = AccountShiroUtil.getCurrentUser().getLoginName();
                List<ZNodes> superuserResult = new ArrayList<ZNodes>();
                List<ZNodes> result = orgService.getOrgTree();
                List<ZNodes> normalResult = new ArrayList<ZNodes>();
                //过滤超级组织
                for (ZNodes re : result) {
                    if ("-1".equals(re.getId())) {
                        continue;
                    }
                    normalResult.add(re);
                }

                //超级用户
                if (Const.SUPERUSER.equals(userName)) {
                    for (ZNodes re : normalResult) {

                        if ("0".equals(re.getpId())) {
                            superuserResult.add(re);
                        }
                    }
                    ar.setSucceed(superuserResult);
                } else {
                    ar.setSucceed(normalResult);
                }

            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "getOrgTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes getOrgTree() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {

                String userName = AccountShiroUtil.getCurrentUser().getLoginName();
                List<ZNodes> superuserResult = new ArrayList<ZNodes>();
                List<ZNodes> result = orgService.getOrgTree(getCompany());
                List<ZNodes> normalResult = new ArrayList<ZNodes>();
                //过滤超级组织
                for (ZNodes re : result) {
                    if ("-1".equals(re.getId())) {
                        continue;
                    }
                    normalResult.add(re);
                }

                //超级用户
                if (Const.SUPERUSER.equals(userName)) {
                    for (ZNodes re : normalResult) {

                        if ("0".equals(re.getpId())) {
                            superuserResult.add(re);
                        }
                    }
                    ar.setSucceed(superuserResult);
                } else {
                    ar.setSucceed(normalResult);
                }

            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "getPreOrgTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes getPreOrgTree() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                List<ZNodes> r = orgService.getPreOrgTree();
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "getCompanyPreOrgTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes getCompanyPreOrgTree() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                List<ZNodes> r = orgService.getCompanyPreOrgTree(getCompany());
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "getCompanyUserPreOrgTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes getCompanyUserPreOrgTree() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                List<ZNodes> r = orgService.getCompanyUserPreOrgTree(getCompany());
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "addCompany", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes addCompany(String chks, Org o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                String uid = get32UUID();
                o.setId(uid);
                o.setCreateTime(new Date());
                o.setCompany(uid);
                if (StringUtils.isBlank(o.getpId())) {
                    o.setpId("0");
                }
                orgService.insert(o);
                Company cm = new Company();
                cm.setId(uid);
                cm.setName(o.getName());
                companyService.insert(cm);
                orgService.saveAuthorized(uid, Const.COMPANY_AUTH, "1");
                addDepAndRole(chks, uid);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }

    private void addDepAndRole(String chks, String pId) {
        if (StringUtils.isBlank(chks)) {
            return;
        }
        String[] deps = chks.split(",");
        for (String dep : deps) {
            String orgId = get32UUID();
            Org org = new Org();
            org.setId(orgId);
            org.setpId(pId);
            org.setName(dep);
            org.setIsValid(1);
            org.setDescription("系统自动创建");
            org.setCreateTime(new Date());
            org.setCompany(pId);
            orgService.insert(org);
            orgService.saveAuthorized(orgId, Const.COMPANY_AUTH, "1");
            for (String roleName : Const.DEFAULT_ROLES) {
                String roleId = get32UUID();
                Role role = new Role();
                role.setName(roleName);
                role.setOrgId(orgId);
                role.setDescription("系统自动创建");
                role.setId(roleId);
                role.setIsValid(1);
                role.setCreateTime(new Date());
                role.setCompany(pId);
                roleService.insert(role);
                String aus;
                if ("总经办".equals(dep) && Const.MANAGE.equals(roleName)) {
                    aus = Const.DEFAULT_ZJB_MANAGER;
                } else if ("人力资源部".equals(dep) && Const.MANAGE.equals(roleName)) {
                    aus = Const.DEFAULT_HR_MANAGER;
                } else if ("市场部".equals(dep) && Const.MANAGE.equals(roleName)) {
                    aus = Const.DEFAULT_SCB_MANAGER;
                } else if (Const.MANAGE.equals(roleName)) {
                    aus = Const.DEFAULT_NOMAL_MANAGER;
                } else {
                    aus = Const.DEFAULT_NOMAL_EMPLOYEE;
                }
                String layer = "1";
                roleService.saveAuthorized(roleId, aus, layer);
            }

        }
    }

    @RequestMapping(value = "addOrg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes addOrg(Org o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                if ("0".equals(o.getpId())) {
                    ar.setFailMsg("您没有权限增加公司!");
                    return ar;
                }
                String uid = get32UUID();
                o.setId(uid);
                o.setCompany(getCompany());
                o.setCreateTime(new Date());
                orgService.insert(o);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "updateCompany", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes updateCompany(Org o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                o.setUpdateTime(new Date());
                orgService.update(o);
                Company com = new Company();
                com.setId(o.getId());
                com.setName(o.getName());
                companyService.update(com);
                ar.setSucceedMsg(Const.UPDATE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.UPDATE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "updateOrg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes updateOrg(Org o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                o.setUpdateTime(new Date());
                orgService.update(o);
                ar.setSucceedMsg(Const.UPDATE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.UPDATE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "findOrg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes findOrg(Org o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                List<Org> list = orgService.find(o);
                Org org = list.get(0);
                ar.setSucceed(org);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "delCompany", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes delCompany(Org o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                int res = orgService.delOrg(o);
                if (res == 1) {
                    ar.setSucceedMsg(Const.DEL_SUCCEED);
                    Company com = new Company();
                    com.setId(o.getId());
                    companyService.delete(com);
                } else {
                    ar.setFailMsg("请先删除所有其子组织或子角色");
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DEL_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "delOrg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes delOrg(Org o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                int res = orgService.delOrg(o);
                if (res == 1) ar.setSucceedMsg(Const.DEL_SUCCEED);
                else ar.setFailMsg("请先删除所有其子组织或子角色");
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DEL_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "orglistCompanyAuthorized", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes orglistCompanyAuthorized() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                PageData pd = this.getPageData();
                String orgId = pd.getString("id");
                String layer = pd.getString("layer");
                List<ZNodes> r = orgService.listCompanyAuthorized(orgId, layer);
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "orglistAuthorized", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes orglistAuthorized() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                PageData pd = this.getPageData();
                String orgId = pd.getString("id");
                String layer = pd.getString("layer");
                List<ZNodes> r = orgService.listAuthorized(orgId, layer);
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "saveOrgAuthorized", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes saveOrgAuthorized() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                PageData pd = this.getPageData();
                String orgId = pd.getString("id");
                String aus = pd.getString("aus");
                String layer = pd.getString("layer");
                orgService.saveAuthorized(orgId, aus, layer);
                ar.setSucceedMsg(Const.UPDATE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.UPDATE_FAIL);
            }
        }
        return ar;
    }
}
