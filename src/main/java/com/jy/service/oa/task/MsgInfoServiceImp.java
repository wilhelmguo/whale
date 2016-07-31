package com.jy.service.oa.task;

import com.jy.entity.oa.task.MsgInfo;
import com.jy.entity.system.dict.DataDict;
import com.jy.entity.system.dict.DataDictItem;
import com.jy.repository.system.dict.DataDictDao;
import com.jy.service.base.BaseServiceImp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("MsgInfoService")
public class MsgInfoServiceImp extends BaseServiceImp<MsgInfo> implements MsgInfoService {

}
