package com.rpc.client.service.impl;

import com.rpc.client.service.ICommonTcpService;
import com.rpc.common.tcp.domain.GetInfoReq;
import com.rpc.common.tcp.domain.GetInfoResp;
import com.rpc.common.tcp.service.Sender;

/**
 * @author yin.huang
 * @date 2018年1月25日 上午11:11:32
 */
public class CommonTcpServiceImpl implements ICommonTcpService {

  private Sender tcpRouter;

  @Override
  public void getBscInfo() {

    for (int i = 0; i < 100; i++) {
      GetInfoReq req = new GetInfoReq();
      req.setName("test==>" + i);
      req.setMsg("i want to get msg==>" + i);
      GetInfoResp getInfoResp = (GetInfoResp) tcpRouter.sendAndWait(req);
      if (getInfoResp != null) {
        System.out.println(getInfoResp.getErrorCode() + "--" + getInfoResp.getErrorMessage() + "--" + getInfoResp.getReturnMsg());
      }
    }
  }

  public Sender getTcpRouter() {
    return tcpRouter;
  }

  public void setTcpRouter(Sender tcpRouter) {
    this.tcpRouter = tcpRouter;
  }

}
