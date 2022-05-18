package spring.demo.service;
import spring.demo.entity.User;
import spring.demo.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);

    public void save(CrmUser crmUser);
}