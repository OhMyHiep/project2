package service;

import entity.dto.RoleDto;
import entity.Role;

public class RoleService {
    public static RoleDto roleDtoMapper(Role role){
        return RoleDto.builder()
                .roleTitle(role.getRole_title()+"")
                .url("/placeholder/"+role.getRole_id())
                .build();
    }

}
