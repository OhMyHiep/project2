package daoTest;

import dao.RoleDao;
import entity.Role;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class TestRoleDao {

    private RoleDao roleDao;
    private Role validRole,actualProgrammer,revertToProgrammer,validTechLead;


    @BeforeClass
    public void setUp(){
        roleDao=new RoleDao();

        validRole=new Role(1000,"Programmer");
        validTechLead=new Role(1001,"TechLead");
        actualProgrammer=new Role(1,"this was changed to test");
        revertToProgrammer=new Role(1,"Programmer");
        roleDao.insert(validRole);

    }

    @Test
    public void testGetById(){
        assertTrue(roleDao.getById(1)!=null);
    }

    @Test
    public void testGetByTitle(){
        assertTrue(roleDao.getByTitle("Programmer")!=null);
    }

    @Test
    public void testGetAll(){
        List<Role> out=roleDao.getAll();
        assertTrue(out.size()>0);
    }

    @Test
    public void testDeleteById(){
        assertTrue(roleDao.deleteById(1000)==1000);
    }

    @Test
    public void testUpdate(){
        roleDao.update(actualProgrammer);
        assertTrue(roleDao.getByTitle("null").getRole_title().equals("null"));
        roleDao.update(revertToProgrammer);
    }

    @Test
    public void testInsert(){
        roleDao.insert(validTechLead);
        Assert.assertEquals(roleDao.deleteById(1001),1001);
    }

    @AfterClass
    public void tearDown(){
        roleDao.deleteById(1000);
        roleDao.deleteById(1001);
    }

}
