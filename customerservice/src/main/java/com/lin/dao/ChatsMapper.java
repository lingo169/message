package com.lin.dao;

import com.lin.po.Chats;

import java.util.List;
import java.util.Map;

public interface ChatsMapper {

    List<Chats> listchats(Map<String,Object> map);

    Integer listchatscount(Map<String,Object> map);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_chats
     *
     * @mbg.generated Sat Mar 19 07:10:00 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_chats
     *
     * @mbg.generated Sat Mar 19 07:10:00 CST 2022
     */
    int insert(Chats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_chats
     *
     * @mbg.generated Sat Mar 19 07:10:00 CST 2022
     */
    int insertSelective(Chats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_chats
     *
     * @mbg.generated Sat Mar 19 07:10:00 CST 2022
     */
    Chats selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_chats
     *
     * @mbg.generated Sat Mar 19 07:10:00 CST 2022
     */
    int updateByPrimaryKeySelective(Chats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_chats
     *
     * @mbg.generated Sat Mar 19 07:10:00 CST 2022
     */
    int updateByPrimaryKey(Chats record);
}