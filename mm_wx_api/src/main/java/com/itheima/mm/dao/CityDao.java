package com.itheima.mm.dao;

import com.itheima.mm.pojo.Dict;

import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/23 21:20
 */
public interface CityDao {
    Dict findCityByName(String cityName);

    List<Dict> findCityListByFs(String fs);
}
