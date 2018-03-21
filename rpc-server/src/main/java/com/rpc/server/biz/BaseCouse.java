package com.rpc.server.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.domain.BaseXipRequest;
import com.rpc.common.tcp.domain.BaseXipResponse;
import com.rpc.common.tcp.domain.BusinessCourse;
import com.rpc.common.tcp.service.Sender;
import com.rpc.common.tcp.service.TransportUtil;

/**
 * 
 * @author yin.huang
 * @date 2018年1月23日 下午2:22:57
 *
 */
public class BaseCouse implements BusinessCourse {

  private static final Logger logger = LoggerFactory.getLogger(BaseCouse.class);

  public <E extends BaseXipRequest, T extends BaseXipResponse> void sendErrorResponse(E req, Class<T> respClass, int errorCode, String errorMessage) {
    T resp = BaseXipResponse.createRespForError(respClass, errorCode, errorMessage);
    resp.setIdentification(req.getIdentification());
    Sender sender = TransportUtil.getSenderOf(req);
    sender.send(resp);
    if (logger.isDebugEnabled()) {
      logger.debug("send {}=[{}]", respClass.getSimpleName(), resp.toString());
    }
  }

  public void sendBaseNormalResponse(BaseXipRequest req, BaseXipResponse resp) {
    resp.setIdentification(req.getIdentification());
    Sender sender = TransportUtil.getSenderOf(req);
    sender.send(resp);
    if (logger.isDebugEnabled()) {
      logger.debug("Send {}=[{}]", resp.getClass().getSimpleName(), resp.toString());
    }
  }

}
