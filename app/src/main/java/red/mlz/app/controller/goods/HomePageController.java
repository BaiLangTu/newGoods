package red.mlz.app.controller.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.app.domain.BaseListVo;
import red.mlz.app.domain.HomePageVo;
import red.mlz.app.domain.banner.BannerItemVo;
import red.mlz.app.domain.banner.BannerVo;
import red.mlz.app.domain.channel.ChannelItemVo;
import red.mlz.app.domain.channel.ChannelVo;
import red.mlz.app.domain.event.EventItemVo;
import red.mlz.app.domain.event.EventVo;
import red.mlz.app.domain.goods.GoodsListVo;
import red.mlz.module.module.banner.entity.Banner;
import red.mlz.module.module.banner.mapper.BannerMapper;
import red.mlz.module.module.channel.entity.Channel;
import red.mlz.module.module.channel.mapper.ChannelMapper;
import red.mlz.module.module.event.entity.Event;
import red.mlz.module.module.event.mapper.EventMapper;
import red.mlz.module.module.goods.entity.Goods;
import red.mlz.module.module.goods.service.GoodsService;
import red.mlz.module.utils.ImageInfo;
import red.mlz.module.utils.ImageUtils;
import red.mlz.module.utils.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HomePageController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private EventMapper eventMapper;

    @RequestMapping("/home")
    public Response HomePage(){

        ExecutorService executor = Executors.newFixedThreadPool(4);

        CountDownLatch countDownLatch = new CountDownLatch(4);

        // 存储各个线程的结果，将它们放入HomePageVo
        final BannerVo[] bannerVo = {new BannerVo()};
        final ChannelVo[] channelVo = {new ChannelVo()};
        final EventVo[] eventVo = {new EventVo()};
        final BaseListVo[] result = {new BaseListVo()};

        // 获取Banner数据
        executor.submit(() -> {
            List<Banner> bannerList = bannerMapper.getAll();
            List<BannerItemVo> bannerVoList = new ArrayList<>();
            for (Banner banner : bannerList) {
                BannerItemVo bannerItemVo = new BannerItemVo();
                bannerItemVo.setId(banner.getId());
                bannerItemVo.setImage(banner.getImage());
                bannerItemVo.setCreatedTime(banner.getCreatedTime());
                bannerVoList.add(bannerItemVo);
            }
            bannerVo[0] = new BannerVo();
            bannerVo[0].setBannerList(bannerVoList);
            countDownLatch.countDown();
        });
        // 获取Channel数据
        executor.submit(() -> {
            List<Channel> channelList = channelMapper.getAll();
            List<ChannelItemVo> channelVoList = new ArrayList<>();
            for (Channel channel : channelList) {
                ChannelItemVo channelItemVo = new ChannelItemVo();
                channelItemVo.setId(channel.getId());
                channelItemVo.setCreatedTime(channel.getCreatedTime());
                channelItemVo.setContent(channel.getContent());
                channelVoList.add(channelItemVo);
            }
            channelVo[0] = new ChannelVo();
            channelVo[0].setChannelItemVos(channelVoList);
            countDownLatch.countDown();
        });
        // 获取Event数据
        executor.submit(() -> {
            List<Event> eventList = eventMapper.getAll();
            List<EventItemVo> eventVoList = new ArrayList<>();
            for (Event event : eventList) {
                EventItemVo eventItemVo = new EventItemVo();
                eventItemVo.setContent(event.getContent());
                eventItemVo.setCreatedTime(event.getCreatedTime());
                eventItemVo.setId(eventItemVo.getId());
                eventVoList.add(eventItemVo);
            }
            eventVo[0] = new EventVo();
            eventVo[0].setEventListVo(eventVoList);
            countDownLatch.countDown();
        });

        // 获取第一页商品数据作为推荐内容
        executor.submit(() -> {
            List<Goods> goodsList = goodsService.getAllGoodsInfo(null, 1, 10);
            List<GoodsListVo> goodsListVoList = new ArrayList<>();
            for (Goods goods : goodsList) {
                GoodsListVo goodsListVo = new GoodsListVo();
                goodsListVo.setId(goods.getId());
                // 将轮播图图片用 “ $ ” 连接
                String[] images = goods.getGoodsImages().split("\\$");
                // 获取图片信息，包含 AR 和 URL
                ImageInfo imageInfo = ImageUtils.getImageInfo(images[0]);
                goodsListVo.setGoodsImage(imageInfo);
                goodsListVo.setTitle(goods.getGoodsName());
                goodsListVo.setSales(goods.getSales());
                goodsListVo.setPrice(goods.getPrice());
                goodsListVoList.add(goodsListVo);
            }
            result[0] = new BaseListVo();
            result[0].setList(goodsListVoList);
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        HomePageVo homePageVo = new HomePageVo();
        homePageVo.setBanner(bannerVo[0]);
        homePageVo.setChannel(channelVo[0]);
        homePageVo.setEvent(eventVo[0]);
        homePageVo.setGoodsList(result[0]);
        return new Response<>(1001, homePageVo);
    }



}
