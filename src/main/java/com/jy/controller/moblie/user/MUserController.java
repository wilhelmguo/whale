package com.jy.controller.moblie.user;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.common.utils.tree.MenuTreeUtil;
import com.jy.common.utils.webpage.PageData;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.resources.Resources;
import com.jy.service.system.resources.ResourcesService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/moblie/api/v1/users")
public class MUserController extends BaseController<Object> {

    @RequestMapping(value = "current", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes getMenu() {
        AjaxRes ar = getAjaxRes();
        Account ac = AccountShiroUtil.getCurrentUser();
        try {
            ar.setSucceed(ac);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg("获取用户失败");
        }
        return ar;
    }

}
