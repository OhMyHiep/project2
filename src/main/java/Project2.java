import dao.BugCommentDao;
import entity.BugComment;
import entity.User;

public class Project2 {
    public static void main(String[] args) {
//        User user=User.builder().
//                firstname("Hiep").
//                lastname("Huynh").
//                build();
//        System.out.println(user.getFirstname());
        BugCommentDao bc = new BugCommentDao();
        BugComment test = bc.getById(1);
        System.out.println(test);
    }
}
