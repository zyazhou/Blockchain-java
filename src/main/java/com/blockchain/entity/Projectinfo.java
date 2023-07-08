package com.blockchain.entity;


import com.blockchain.service.ProjectinfoService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class Projectinfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String projectid;

    private String title;

    private String context;

    private String expetationamount;

    private String info;

    private String url;
    private String donationamount;
    private Integer donationtimes;
}
