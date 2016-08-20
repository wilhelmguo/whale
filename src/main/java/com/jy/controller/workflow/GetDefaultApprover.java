package com.jy.controller.workflow;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.oa.bpm.BpmConf;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Role;
import com.jy.service.oa.bpm.BpmConfService;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.org.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsw on 16-8-16
 * @version 1.0
 */
@Controller
@RequestMapping("/backstage/workflow/approver/")
public class GetDefaultApprover extends BaseController<Object> {
    @Autowired
    public RoleService roleService;
    @Autowired
    public AccountService accountService;
    @Autowired
    public BpmConfService bpmConfService;

    @RequestMapping(value = "findAllApprover", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes find(String key) {
        AjaxRes ar = getAjaxRes();
        try {
            List<Account> accounts = new ArrayList<Account>();
            BpmConf bpmConf = new BpmConf();
            bpmConf.setKey(key);
            List<BpmConf> blist = bpmConfService.find(bpmConf);
            if (CollectionUtils.isNotEmpty(blist)) {
                for (int i = 0; i < blist.size(); i++) {
                    BpmConf b = blist.get(i);
                    if (i == 0 && StringUtils.isBlank(b.getUsers())) {
                        Account rAc = getDirectlyLeader();
                        if (rAc == null) {
                            ar.setFailMsg("查找直属领导失败,请先设置角色层级关系!");
                            return ar;
                        } else {
                            accounts.add(rAc);
                        }
                    } else {
                        if (b.getUsertype() == null) {
                            ar.setFailMsg("查找用户失败,请先为流程"+b.getPname()+"分配用户!");
                            return ar;
                        }
                        if (b.getUsertype() == 0) {
                            Account account = new Account();
                            account.setRoleId(b.getUsers());
                            List<Account> acs = accountService.find(account);
                            if (CollectionUtils.isNotEmpty(acs)) {
                                accounts.add(acs.get(0));
                            } else {
                                ar.setFailMsg("查找用户失败,请先为流程"+b.getPname()+"分配用户!");
                                return ar;
                            }
                        } else {
                            Account ac = new Account();
                            ac.setAccountId(b.getUsers());
                            ac.setName(b.getUsername());
                            accounts.add(ac);
                        }

                    }
                }
            }

            ar.setSucceed(accounts);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg(Const.DATA_FAIL);
        }
        return ar;
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes findDirectlyLeader() {
        AjaxRes ar = getAjaxRes();
        try {
            Account rAc = getDirectlyLeader();
            if (rAc == null) {
                ar.setFailMsg("查找直属领导失败,请先设置角色层级关系!");
                return ar;
            } else {
                ar.setSucceed(rAc);
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg(Const.DATA_FAIL);
        }
        return ar;
    }

    private Account getDirectlyLeader() {
        Account ac = AccountShiroUtil.getCurrentUser();
        String role = ac.getRoleId();
        Role r = new Role();
        r.setId(role);
        List<Role> roles = roleService.find(r);
        if (CollectionUtils.isNotEmpty(roles)) {
            String pid = roles.get(0).getPid();
            Account account = new Account();
            account.setRoleId(pid);
            if (StringUtils.isBlank(pid)){
                return null;
            }
            List<Account> acs = accountService.find(account);
            if (CollectionUtils.isNotEmpty(acs)) {
                return acs.get(0);
            }
        }
        return null;
    }
}
