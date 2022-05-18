package spring.demo.dao;

import spring.demo.entity.User;

public interface UserDao {

    public User findByUserName(String userName);

    public void save(User user);

}