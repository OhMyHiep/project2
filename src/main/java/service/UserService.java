package service;

import entity.User;
import entity.dto.UserDto;

public class UserService {

    public static UserDto userDtoMapper(User user){
        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .url("/placeholder/"+user.getUser_id())
                .build();
    }
}
