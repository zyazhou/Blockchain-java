package com.blockchain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String email;

    private String personId;

    private String name;
    private String phone;

    private String password;
    private String totalmoney;

    @TableField(exist = false)
    private String checkpass;

}
