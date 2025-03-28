package red.mlz.module.module.banner.Service;


import org.springframework.stereotype.Service;
import red.mlz.module.module.banner.entity.Banner;
import red.mlz.module.module.banner.mapper.BannerMapper;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class BannerService {
    @Resource
    private BannerMapper bannerMapper;

    // 获取类目列表
    public List<Banner> getAll() {
        return bannerMapper.getAll();
    }

    // 根据ID查询操作
    public Banner getById(BigInteger id) {
        return bannerMapper.getById(id);
    }

    // 根据ID提取操作
    public Banner extractById(BigInteger id) {
        return bannerMapper.extractById(id);
    }

    // 插入操作
    public int insert(Banner banner) {
        return bannerMapper.insert(banner);
    }

    // 更新操作
    public int update(Banner banner) {
        return bannerMapper.update(banner);
    }

    // 删除操作
    public int delete(BigInteger id, Integer time) {
        return bannerMapper.delete(id, time);
    }

}
