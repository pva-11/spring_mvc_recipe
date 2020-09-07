package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.model.MyAppUser;

public interface MyAppUserService {
    MyAppUser getMyAppUserByUsername(String username);
}
