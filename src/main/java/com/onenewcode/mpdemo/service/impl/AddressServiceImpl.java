package com.onenewcode.mpdemo.service.impl;

import com.onenewcode.mpdemo.domain.po.Address;
import com.onenewcode.mpdemo.mapper.AddressMapper;
import com.onenewcode.mpdemo.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onenewcode
 * @since 2023-10-25
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
