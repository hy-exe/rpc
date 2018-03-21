package com.rpc.server.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.annotion.BizMethod;
import com.rpc.common.tcp.domain.GetInfoReq;
import com.rpc.common.tcp.domain.GetInfoResp;

/**
 * 
 * @author yin.huang
 * @date 2018年1月25日 下午5:28:30
 *
 */
public class GetInfoCourse extends BaseCouse {

  private Logger logger = LoggerFactory.getLogger(GetInfoCourse.class);

  @BizMethod
  public void onGetInfoReq(GetInfoReq req) {
    if (logger.isDebugEnabled()) {
      logger.debug("==>>receive getInfoReq,req=[{}]", req.toString());
    }

    String name = req.getName();
    String msg = req.getMsg();

    logger.info("receive msg,name=[{}],msg=[{}]", new Object[] { name, msg });

    GetInfoResp resp = new GetInfoResp();
    resp.setErrorCode(0);
    resp.setErrorMessage("成功");
    resp.setReturnMsg("0000000");
    this.sendBaseNormalResponse(req, resp);
  }

}
