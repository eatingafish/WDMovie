package com.bw.movie.Dao;


import android.content.Context;

import com.bw.movie.bean.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class UserDao {

    private Dao<User,String> dao;

    public UserDao(Context context) throws SQLException {
        MySql mySql = new MySql(context);
        dao = mySql.getDao(User.class);
    }

    // 创建数据
    public void insertStudent(User student) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.createOrUpdate(student);
    }

    public void batchInsert(List<User> students) throws SQLException {
        dao.create(students);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<User> getStudent() throws SQLException {
        List<User> list = dao.queryForAll();
        return list;
    }


    /**
     * 删除数据
     *
     * @param student
     * @throws SQLException
     */
    public void deleteStudent(User student) throws SQLException {
        //只看id
        dao.delete(student);
    }


}
