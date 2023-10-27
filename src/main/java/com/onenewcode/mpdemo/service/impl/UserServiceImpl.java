package com.onenewcode.mpdemo.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.onenewcode.mpdemo.domain.dto.PageDTO;
import com.onenewcode.mpdemo.domain.enums.UserStatus;
import com.onenewcode.mpdemo.domain.po.User;
import com.onenewcode.mpdemo.domain.po.Address;
import com.onenewcode.mpdemo.domain.query.PageQuery;
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

    @Override
    public PageDTO<UserVO> queryUsersPage(PageQuery query) {
        // 1.构建条件
        // 1.1.分页条件
        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
        // 1.2.排序条件
        if (query.getSortBy() != null) {
            page.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
        }else{
            // 默认按照更新时间排序
            page.addOrder(new OrderItem("update_time", false));
        }
        // 2.查询
        page(page);
        // 3.数据非空校验
        List<User> records = page.getRecords();
//        todo
//        if (records == null || records.size() <= 0) {
//            // 无数据，返回空结果
//            return new PageDTO<>(page.getTotal(), page.getPages(), Collections.emptyList());
//        }
//        // 4.有数据，转换
//        List<UserVO> list = BeanUtil.copyToList(records, UserVO.class);
//        // 5.封装返回
//        return new PageDTO<UserVO>(page.getTotal(), page.getPages(), list);
        return null;
    }

}
