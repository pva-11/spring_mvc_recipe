package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.model.MyAppUser;
import mvc.spring.example.recipe.repositories.MyAppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MyAppUserServiceImpl implements MyAppUserService {

    MyAppUserRepository myAppUserRepository;


    public MyAppUserServiceImpl(MyAppUserRepository myAppUserRepository) {
        this.myAppUserRepository = myAppUserRepository;
    }

    @Override
    @Transactional
    public MyAppUser getMyAppUserByUsername(String username) throws NoSuchElementException {
        Optional<MyAppUser> myAppUser = myAppUserRepository.findByUsername(username);
        if (!myAppUser.isPresent()) {
            return null;
        }
        return myAppUser.get();
    }
}
