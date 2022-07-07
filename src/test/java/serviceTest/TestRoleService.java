package serviceTest;

import entity.Role;
import entity.dto.RoleDto;
import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.RoleService;

public class TestRoleService {
    @InjectMocks
    private RoleService roleService;


    private Role role;
    private RoleDto roleDto;

    @BeforeClass
    public void setUp(){
        role=new Role(1,"Programmer");
        roleDto=RoleDto.builder()
                .roleTitle(role.getRole_title()+"")
                .url("/placeholder/"+role.getRole_id())
                .build();
    }

    @Test
    public void test_RoleService(){
        Assert.assertEquals(roleService.roleDtoMapper(role),roleDto);
    }
}
