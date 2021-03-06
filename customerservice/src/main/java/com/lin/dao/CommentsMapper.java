package com.lin.dao;

import com.lin.po.Comments;

import java.util.List;
import java.util.Map;

public interface CommentsMapper {

    List<Comments> comments(Map<String,Object> map);


    Integer commentscount(Long lifeId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_comments
     *
     * @mbg.generated Wed Apr 20 20:36:41 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_comments
     *
     * @mbg.generated Wed Apr 20 20:36:41 CST 2022
     */
    int insert(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_comments
     *
     * @mbg.generated Wed Apr 20 20:36:41 CST 2022
     */
    int insertSelective(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_comments
     *
     * @mbg.generated Wed Apr 20 20:36:41 CST 2022
     */
    Comments selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_comments
     *
     * @mbg.generated Wed Apr 20 20:36:41 CST 2022
     */
    int updateByPrimaryKeySelective(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_comments
     *
     * @mbg.generated Wed Apr 20 20:36:41 CST 2022
     */
    int updateByPrimaryKey(Comments record);
}