package com.haircut.dao;

import com.haircut.dataobject.UserPasswordDO;
import com.haircut.dataobject.UserPasswordDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPasswordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    long countByExample(UserPasswordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int deleteByExample(UserPasswordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int insert(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int insertSelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    List<UserPasswordDO> selectByExample(UserPasswordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    UserPasswordDO selectByPrimaryKey(Integer id);

    UserPasswordDO selectByUserId(Integer userId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int updateByExampleSelective(@Param("record") UserPasswordDO record, @Param("example") UserPasswordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int updateByExample(@Param("record") UserPasswordDO record, @Param("example") UserPasswordDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int updateByPrimaryKeySelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 05 22:53:36 CST 2022
     */
    int updateByPrimaryKey(UserPasswordDO record);
}