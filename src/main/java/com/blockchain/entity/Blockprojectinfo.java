package com.blockchain.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class Blockprojectinfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("projectID")
    private String projectid;

    @TableField("projectName")
    private String projectname;

    @TableField("userID")
    private String userid;

    @TableField("userName")
    private String username;

    @TableField("expetationAmount")
    private String expetationamount;

    @TableField("projectInfo")
    private String projectinfo;

    @TableField("createTime")
    private String createtime;

    @TableField("donationAmount")
    private String donationamount;

    @TableField("donationTimes")
    private String donationtimes;

    @TableField(exist = false)
    private String info;

    @TableField(exist = false)
    private String url;


}
