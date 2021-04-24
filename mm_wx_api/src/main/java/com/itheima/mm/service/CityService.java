package com.itheima.mm.service;

import com.itheima.mm.dao.CityDao;
import com.itheima.mm.pojo.Dict;
import com.itheima.mm.utils.LocationUtil;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/23 21:20
 */
public class CityService {


    public Map findCityList(Map parameterMap) throws IOException {
        //获取经纬度
        String location = (String) parameterMap.get("location");
        //通过高德地图api获得城市名
        String cityName = LocationUtil.parseLocation(location);

//        通过城市名从数据库中查询出dict对象
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CityDao cityDao = sqlSession.getMapper(CityDao.class);
        Dict currentCity = cityDao.findCityByName(cityName);

        //获取fs,通过fs从数据库中查出城市列表
        String fs = (String) parameterMap.get("fs");
        List<Dict> dictList = cityDao.findCityListByFs(fs);

//      封装结果map
        Map resrultMap = new HashMap();
        resrultMap.put("location",currentCity);
        resrultMap.put("citys",dictList);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);

        return resrultMap;
    }
}
