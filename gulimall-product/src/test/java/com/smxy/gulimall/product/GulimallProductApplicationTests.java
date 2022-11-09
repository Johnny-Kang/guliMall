package com.smxy.gulimall.product;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smxy.gulimall.product.dao.BrandDao;
import com.smxy.gulimall.product.entity.BrandEntity;
import com.smxy.gulimall.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class GulimallProductApplicationTests {

    private final BrandService brandService;
//
//    private final OSSClient ossClient;
//
//    @Test
//    public void testUpload(){
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream("E:\\28363\\Pictures\\Saved Pictures\\wallhaven-l3xk6q.jpg");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        // 创建PutObject请求。
//        ossClient.putObject("alibaba-gulimall", "wallhaven-l3xk6q.jpg", inputStream);
//
//        ossClient.shutdown();
//    }

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
