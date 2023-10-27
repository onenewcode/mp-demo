package com.onenewcode.mpdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onenewcode.mpdemo.domain.po.User;
import com.onenewcode.mpdemo.domain.vo.UserVO;

public interface UserService extends IService<User> {
    // 拓展自定义方法
    void deductBalance(Long id, Integer money);
    UserVO queryUserAndAddressById(Long userId);
    void deduct(Long id, Integer money);
}