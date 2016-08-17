package com.jy.controller.moblie.cms;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.echarts.series.Map;
import com.jy.common.utils.echarts.series.MarkPoint;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.cms.SysNews;
import com.jy.service.system.cms.SysNewsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

@Controller
@RequestMapping("/moblie/api/v1/news")
public class MSysNewsController extends BaseController<SysNews> {

    @Autowired
    private SysNewsService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes findByPage() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/SysNews/index"))) {
            try {
                Page<SysNews> page = new Page<SysNews>();
                page.setPageNum(1);
                page.setPageSize(100);
                SysNews o = new SysNews();
                o.setIsValid(1);
                o.setCompany(getCompany());
                Page<SysNews> news = service.findByPage(o, page);
                ar.setSucceed(getTimeList(news.getResults()));
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    private TreeMap<String, List<SysNews>> getTimeList(List<SysNews> list) {
        TreeMap<String, List<SysNews>> result = new TreeMap<String, List<SysNews>>();
        for (SysNews s : list) {
            String time = DateUtils.formatDate(s.getAddtime(), "yyyy-MM-dd");
            List<SysNews> sysNews = result.get(time);
            if (CollectionUtils.isEmpty(sysNews)) {
                sysNews = new ArrayList<SysNews>();
            }
            sysNews.add(s);
            result.put(time, sysNews);
        }
        return result;
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes find(String id) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {
            try {
                SysNews o = new SysNews();
                o.setId(id);
                o.setCompany(o.getCompany());
                List<SysNews> list = service.find(o);
                if (list != null && list.size() != 0) {
                    SysNews obj = list.get(0);
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