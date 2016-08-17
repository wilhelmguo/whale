package com.jy.controller.moblie.cms;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.cms.Ads;
import com.jy.service.system.cms.AdsService;
import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/moblie/api/v1/ads")
public class MAdsController extends BaseController<Ads> {

    @Autowired
    private AdsService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes findByPage() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/ads/index"))) {
            try {
                Page<Ads> page = new Page<Ads>();
                page.setPageNum(1);
                page.setPageSize(3);
                Ads o = new Ads();
                o.setIsValid(1);
                o.setCompany(getCompany());
                Page<Ads> news = service.findByPage(o, page);
                ar.setSucceed(news);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes find(String id) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                Ads o = new Ads();
                o.setId(id);
                o.setCompany(o.getCompany());
                List<Ads> list = service.find(o);
                if (list != null && list.size() != 0) {
                    Ads obj = list.get(0);
                    ar.setSucceed(obj);
                } else {
                    ar.setFailMsg(Const.DATA_FAIL);
                }
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

}