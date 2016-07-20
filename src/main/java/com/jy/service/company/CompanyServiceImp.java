package com.jy.service.company;

import com.jy.common.utils.base.UuidUtil;
import com.jy.entity.system.company.Company;
import com.jy.entity.system.org.Org;
import com.jy.entity.system.org.Role;
import com.jy.repository.system.company.CompanyDao;
import com.jy.repository.system.org.OrgDao;
import com.jy.repository.system.org.RoleDao;
import com.jy.service.base.BaseServiceImp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author gsw on 16-7-19
 * @version 1.0
 */
@Service("CompanyService")
public class CompanyServiceImp extends BaseServiceImp<Company> implements CompanyService {
  @Autowired
  protected CompanyDao companyDao;
  @Autowired
  protected OrgDao orgDao;
  @Autowired
  protected RoleDao roleDao;

  @Override
  public void insert(Company o) {
    companyDao.insert(o);
    Org org = new Org();
    String orgId = UuidUtil.get32UUID();
    org.setId(orgId);
    org.setpId("0");
    org.setpName(o.getName());
    org.setCreateTime(new Date());
    orgDao.insert(org);
    Role role = new Role();
    if (StringUtils.isNotBlank(orgId)) {
      List<Org> orgs = orgDao.find(org);
      if (orgs.size() > 0) {
        Org pOrg = orgs.get(0);
        String pId = pOrg.getpId();
        if (StringUtils.isNotBlank(pId)) {
          role.setId(UuidUtil.get32UUID());
          role.setCreateTime(new Date());
          role.setIsValid(1);
          role.setName("管理员");
          role.setDescription("系统自动创建管理员角色");
          roleDao.insert(role);
        }
      }
    }
  }
}
