import model.Blog;
import model.User;
import model.enums.BlogStatus;
import model.enums.StatusType;
import repository.BlogRepository;
import repository.UserRepository;
import service.BlogService;
import service.UserService;
import service.BlogStatisticsService;
import utils.PasswordUtil;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService(UserRepository.getInstance());
        BlogService blogService = new BlogService(userService);

        userService.saveUser("cem@gmail.com", PasswordUtil.hashPassword("password"));
        userService.saveUser("mehmet@gmail.com", PasswordUtil.hashPassword("123456"));
        userService.saveUser("toygun@gmail.com", PasswordUtil.hashPassword("123456"));

        userService.changeStatus("cem@gmail.com", StatusType.APPROVED);
        userService.changeStatus("mehmet@gmail.com", StatusType.APPROVED);
        userService.changeStatus("toygun@gmail.com", StatusType.APPROVED);

        userService.changeStatus(List.of("cem@gmail.com", "toygun@gmail.com"), StatusType.APPROVED);

        userService.getAllUsers().forEach(System.out::println);

        userService.getAllUsers()
                .stream()
                .peek(System.out::println)
                .filter(user -> user.getEmail().equals("cem@gmail.com"))
                .toList();

        List<String> emailList = userService.getAllUsers()
                .stream()
                .map(User::getEmail)
                .toList();

        List<Blog> allUsersBlogList = userService.getAllUsers()
                .stream()
                .flatMap(user -> user.getBlogList().stream())
                .toList();

        Map<String, User> emailUserMap = userService.getAllUsersMap();

        User cemUser = emailUserMap.get("cem@gmail.com");
        User foundUser = userService.getUserByEmail("cem@gmail.com");

        blogService.createBlog("başlık", "içerik", "cem@gmail.com");

        Blog foundBlog = blogService.getBlogByTitle("başlık");

        blogService.addComment("başlık", "çok kötü olmuş", foundUser);

        //ödev. kullanıcın takip ettiği tag'lere göre blog'lar gelmeli
        blogService.changeBlogStatus(BlogStatus.DELETED, "başlık");

        // Blogları sıralama
        List<Blog> sortedByCreationDate = blogService.sortByCreationDate(true);
        System.out.println("Oluşturulma tarihine göre sıralanmış bloglar (Artan): " + sortedByCreationDate);

        List<Blog> sortedByCommentCount = blogService.sortByCommentCount(false);
        System.out.println("Yorum sayısına göre sıralanmış bloglar (Azalan): " + sortedByCommentCount);

        List<Blog> sortedByViewCount = blogService.sortByViewCount(true);
        System.out.println("Okunma sayısına göre sıralanmış bloglar (Artan): " + sortedByViewCount);

        // Blog istatistikleri
        BlogStatisticsService statisticsService = new BlogStatisticsService(blogService.getAllBlogs());
        statisticsService.printStatistics();
    }
}
