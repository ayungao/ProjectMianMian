package com.itheima.mm.service;

import com.itheima.mm.dao.CatalogDao;
import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.TagDao;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Catalog;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/3/15 21:15
 */
public class CourseService {
    public void add(Course course) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //调dao层方法添加
        courseDao.add(course);
        System.out.println(course);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public PageResult findByPage(QueryPageBean queryPageBean) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //调dao层方法查询course总数
        Long total = courseDao.findTotalCount(queryPageBean);
        //调dao层方法查询course列表
        List<Course> rows = courseDao.findPageList(queryPageBean);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return new PageResult(total,rows);
    }

    public void update(Course course) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        //调dao层方法更新
        courseDao.update(course);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public void deleteById(Integer id) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        //1. 判断当前要删除的学科是否有关联的二级目录
        CatalogDao catalogDao = sqlSession.getMapper(CatalogDao.class);
        //根据courseId到t_catalog表中查询数据条数
        Long catalogCount = catalogDao.findCountByCourseId(id);
        if (catalogCount > 0) {
            //有关联的二级目录，不能删除
            throw new RuntimeException("有关联的二级目录，不能删除");
        }

        //2. 判断当前要删除的学科是否有关联的标签
        TagDao tagDao = sqlSession.getMapper(TagDao.class);
        Long tagCount = tagDao.findCountByCourseId(id);
        if (tagCount > 0) {
            //有关联的标签，不能删除
            throw new RuntimeException("有关联的标签，不能删除");
        }

        //3. 判断当前要删除的学科是否有关联的题目
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        Long questionCount = questionDao.findCountByCourseId(id);
        if (questionCount > 0) {
            //有关联的题目，不能删除
            throw new RuntimeException("有关联的题目，不能删除");
        }

        //4. 如果上述三个关联都没有，则调用dao层的方法进行根据id删除
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        courseDao.deleteById(id);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);


    }

    public List<Course> findAll(String status) throws IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);

        List<Course> courseList = courseDao.findAll(status);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return courseList;

    }
}
