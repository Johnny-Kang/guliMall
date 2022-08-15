package com.smxy.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smxy.gulimall.product.dao.BrandDao;
import com.smxy.gulimall.product.entity.BrandEntity;
import com.smxy.gulimall.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class GulimallProductApplicationTests {

    private final BrandService brandService;

    @Test
    void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("华为");
//        brandService.save(brandEntity);

        List<BrandEntity> brandEntityList = brandService.list(new QueryWrapper<BrandEntity>().lambda().eq(BrandEntity::getBrandId, 1L));
        brandEntityList.forEach(item ->{
            System.out.println(item);
        });
    }

}
