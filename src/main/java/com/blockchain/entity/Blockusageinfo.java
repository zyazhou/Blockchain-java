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
public class Blockusageinfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("projectID")
    private String projectid;

    @TableField("userID")
    private String userid;

    @TableField("userName")
    private String username;

    @TableField("usedAmount")
    private String usedamount;

    @TableField("usedTime")
    private String usedtime;

    @TableField("usedInfo")
    private String usedinfo;


}
