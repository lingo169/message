package com.lin.service.life;

import com.lin.common.constant.CommonConstant;
import com.lin.common.constant.CustomerServiceConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.utils.CustomFileUtils;
import com.lin.common.utils.DateUtils;
import com.lin.common.utils.RedisSerializableUtils;
import com.lin.controller.req.AddLifeReqMsg;
import com.lin.controller.req.LifeListReqMsg;
import com.lin.controller.res.LifePageResMsg;
import com.lin.dao.LifeMapper;
import com.lin.po.Life;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class LifeServiceImpl implements LifeService {
    private static Logger log = LoggerFactory.getLogger(LifeServiceImpl.class);
    @Autowired
    private LifeMapper lifeMapper;

    @Value("${cs.prefix-path}")
    private String prefixPath;

    @Override
    public Integer addrel(AddLifeReqMsg reqMsg) throws CustomRuntimeException {
        Life life = build(reqMsg);
        return lifeMapper.insertSelective(life);
    }

    @Override
    public LifePageResMsg lifelist(LifeListReqMsg reqMsg) {
        LifePageResMsg lpr = new LifePageResMsg();
        Map map = build(reqMsg);
        lpr.setRecords(lifeMapper.listlifes(map));
        lpr.setTotal(lifeMapper.listlifescount(map));
        return lpr;
    }

    @Override
    public Life lifedetail(Long id) throws CustomRuntimeException {
        return lifeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer del(Long id) throws CustomRuntimeException {
        return lifeMapper.deleteByPrimaryKey(id);
    }

    private Map<String, Object> build(LifeListReqMsg reqMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", reqMsg.getCustomerId());
        map.put("pageNo", reqMsg.getPageNo());
        map.put("pageSize", reqMsg.getPageSize());
        return map;
    }

    private Life build(AddLifeReqMsg reqMsg) throws CustomRuntimeException {
        Life l = new Life();
        l.setId(Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH)));
        l.setCustomerId(reqMsg.getCustomerId());
        l.setLables(reqMsg.getLables());
        l.setContents(reqMsg.getContents());
        //TODO BASE64 TO FILEURL;
        if (null != reqMsg.getFileUrl() && reqMsg.getFileUrl().size() > 0) {
            StringBuilder sb = new StringBuilder();
            reqMsg.getFileUrl().forEach(i -> {
                try {
                    sb.append(CustomFileUtils.buildFile(i, prefixPath + File.separator + CustomerServiceConstant.lifepath, RedisSerializableUtils.generateOrderNo(CommonConstant.DEFAULT_LENGTH)) + ";");
                } catch (Exception e) {
                    log.error("image base64 to file error", e);
                }
            });
            sb.deleteCharAt(sb.length() - 1);
            l.setFileUrl(sb.toString());
        }
        l.setProvince(reqMsg.getProvince());
        l.setCity(reqMsg.getCity());
        l.setCounty(reqMsg.getCounty());
        l.setForwardTimes(0);
        l.setForwardTimes(0);
        l.setThumbsupTimes(0);
        l.setReplyTimes(0);
        l.setCreateTime(DateUtils.fullDateFormat());
        l.setUpdateTime(DateUtils.fullDateFormat());
        return l;
    }
}
