package com.onenewcode.mpdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.onenewcode.mpdemo.domain.enums.UserStatus;
import com.onenewcode.mpdemo.domain.po.User;
import com.onenewcode.mpdemo.domain.po.Address;
import com.onenewcode.mpdemo.domain.vo.UserVO;
import com.onenewcode.mpdemo.mapper.UserMapper;
import com.onenewcode.mpdemo.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    @Transactional
    public void deductBalance(Long id, Integer money) {
        // 1.查询用户
        User user = getById(id);
        // 2.校验用户状态
        if (user == null || user.getStatus()== UserStatus.NORMAL) {
            throw new RuntimeException("用户状态异常！");
        }
        // 3.校验余额是否充足
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足！");
        }
        // 4.扣减余额 update tb_user set balance = balance - ?
        int remainBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(User::getBalance, remainBalance) // 更新余额
                .set(remainBalance == 0, User::getStatus, 2) // 动态判断，是否更新status
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance()) // 乐观锁
                .update();
    }
    @Override
    public UserVO queryUserAndAddressById(Long userId) {
        // 1.查询用户
        User user = getById(userId);
        if (user == null) {
            // 2.查询收货地址
            List<Address> addresses = Db.lambdaQuery(Address.class)
                    .eq(Address::getUserId, userId)
                    .list();
//        todo
            // 3.处理vo
//        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
//        userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
//        return userVO;
            return null;
        }
        return null;
    }

    @Override
    public void deduct(Long id, Integer money) {

    }

//        @Override
//        public void deduct (Long id, Integer money){
//
//        }

}
