package com.itheima.mm.dao;

import com.itheima.mm.pojo.WxMember;

import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/23 22:48
 */
public interface WxMemberDao {
    WxMember findByNickname(String nickName);

    void addWxMember(WxMember wxMember);

    WxMember findById(Integer id);

    void update(WxMember wxMember);

    String findCityName(Integer cityId);

    Integer findAnswerCount(int id);

    Map findLastAnswer(int id);

    String findCategoryTitle(Map lastAnswer);
}
