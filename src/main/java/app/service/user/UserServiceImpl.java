package app.service.user;

import app.entity.CustomUser;
import app.entity.enums.UserRole;
import app.repository.UserRepository;
import app.service.coder.CoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoderService coderService;

    @Override
    @Transactional(readOnly = true)
    public CustomUser getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser getOne(long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> getUsersAll() {
        return userRepository.findAll();
    }

    @Override
    public List<CustomUser> getUsersAllSort() {
        return userRepository.findAllSort();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUser> getUsersExceptRole(UserRole role) {
        return userRepository.findExceptRole(role);
    }

    @Override
    @Transactional
    public boolean addUser(CustomUser customUser) {
        if (customUser.getLogin().equals("") || userRepository.findByLogin(customUser.getLogin()) != null) {
            return false;
        }
        userRepository.save(customUser);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(long id, String name, String login, String password, String confirmPassword, String phone, String email, UserRole role) {
        if (!login.equals("") && userRepository.findByLoginExceptId(login, id).size() == 0){
            CustomUser customUser = userRepository.findOne(id);
            if (!(password == null && confirmPassword == null)){
                if ((password == null && confirmPassword != null) || (confirmPassword == null && password != null) || !password.equals(confirmPassword)){
                    return false;
                }
                if (!password.equals("")){
                    customUser.setPassword(coderService.coderSHA1(password));
                }
            }

            customUser.setName(name);
            customUser.setLogin(login);
            customUser.setPhone(phone);
            customUser.setEmail(email);
            customUser.setRole(role);
            userRepository.save(customUser);
            return true;
        }
        return false;
    }
}
