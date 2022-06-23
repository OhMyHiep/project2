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
//        BugCommentDao bc = new BugCommentDao();
//        Integer test = bc.insert(new BugComment(0, 2, 1, "This is a test", new Date(System.currentTimeMillis())));
//        System.out.println(test);

        BugDaoImpl bugDao= new BugDaoImpl();

        bugDao.insert(Bug.builder()
                .creator_id(1)
                .issueDate(new Date())
                .build());
    }
}
