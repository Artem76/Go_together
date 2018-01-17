package app.service.user;

import app.entity.CustomUser;
import app.entity.enums.UserRole;

import java.util.List;

public interface UserService {
    CustomUser getUserByLogin(String login);
    CustomUser getOne(long id);
    List<CustomUser> getUsersAll();
    List<CustomUser> getUsersAllSort();
    List<CustomUser> getUsersByRole(UserRole role);
    List<CustomUser> getUsersExceptRole(UserRole role);
    boolean addUser(CustomUser customUser);
    boolean updateUser(long id, String name, String login, String password, String confirmPassword, String phone, String email, UserRole role);
}
