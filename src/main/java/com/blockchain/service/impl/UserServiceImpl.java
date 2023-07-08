package com.blockchain.service.impl;

import com.blockchain.entity.User;
import com.blockchain.mapper.UserMapper;
import com.blockchain.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
