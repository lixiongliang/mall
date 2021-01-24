package com.macro.mall;

import cn.hutool.json.JSONUtil;
import com.macro.mall.dao.PmsMemberPriceDao;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.PmsMemberPrice;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.UmsAdminCacheService;
import com.macro.mall.service.UmsAdminService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PmsDaoTests {
    @Autowired
    private PmsMemberPriceDao memberPriceDao;
    @Autowired
    private PmsProductDao productDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsDaoTests.class);

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    UmsAdminCacheService umsAdminCacheService;

    public static void main(String[] args) {

        String hashpw = BCrypt.hashpw("123456", BCrypt.gensalt());
        System.out.println("hashpw="+hashpw);
    }

    @Test
    public void getAdminByUsername(){
        umsAdminCacheService.delAdmin(3L);
        UmsAdmin admin = umsAdminService.getAdminByUsername("admin");
        LOGGER.info(">>>>>>>>>{}", admin);
    }

    @Test
    @Transactional
    @Rollback
    public void testInsertBatch(){
        List<PmsMemberPrice> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            PmsMemberPrice memberPrice = new PmsMemberPrice();
            memberPrice.setProductId(1L);
            memberPrice.setMemberLevelId((long) (i+1));
            memberPrice.setMemberPrice(new BigDecimal("22"));
            list.add(memberPrice);
        }
        int count = memberPriceDao.insertList(list);
        Assert.assertEquals(5,count);
    }

    @Test
    public void  testGetProductUpdateInfo(){
        PmsProductResult productResult = productDao.getUpdateInfo(7L);
        String json = JSONUtil.parse(productResult).toString();
        LOGGER.info(json);
    }
}
