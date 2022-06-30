import dao.UserDao;
import service.BugService;

public class SecondMain {

    static UserDao userDao=new UserDao();
    static BugService bugService=new BugService();
    public static void main(String[] args) {
//        System.out.println(userDao.getById(null));
        System.out.println(bugService.descriptionIsValidForInsert("this is more than fifty characters for the description"));

    }
}
