package com.rpc.common.tcp.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午4:01:18
 */
public class HeartbeatResp extends AbstractSignal {

  /**
   * 
   */
  private static final long         serialVersionUID = 5358684049803614156L;

  private String                    groupName;

  private ArrayList<ServerSnapshot> candidates       = new ArrayList<ServerSnapshot>();

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public List<ServerSnapshot> getCandidates() {
    return candidates;
  }

  public void setCandidates(ArrayList<ServerSnapshot> candidates) {
    this.candidates = candidates;
  }

  public void addServerGroup(ServerSnapshot group) {
    candidates.add(group);
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
