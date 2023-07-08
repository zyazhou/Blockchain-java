package com.blockchain.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blockrecordinfo")
public class Blockrecordinfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("userID")
    private String userid;

    @TableField("projectID")
    private String projectid;

    @TableField("donationAmount")
    private String donationamount;

    @TableField("donationTime")
    private String donationtime;

    @TableField("message")
    private String message;


}
