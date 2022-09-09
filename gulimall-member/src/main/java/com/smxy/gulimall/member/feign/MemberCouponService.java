package com.smxy.gulimall.member.feign;

import com.smxy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * member服务远程调用coupon
 */

@FeignClient("gulimall-coupon")
public interface MemberCouponService {

    @RequestMapping("/coupon/coupon/coupons")
    R memberCoupons();
}
