package com.rpc.client.dao;

import java.util.List;

/**
 * @author yin.huang
 * @date 2018年1月24日 上午10:23:15
 */
public interface IBatchExecutor<T> {

  void execute(List<T> records);
}
