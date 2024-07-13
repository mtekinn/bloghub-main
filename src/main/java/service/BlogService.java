package service;

import model.Blog;
import model.BlogComment;
import model.User;
import model.enums.BlogStatus;
import repository.BlogRepository;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BlogService {
    private BlogRepository blogRepository;
    private UserService userService;

    public BlogService(UserService userService) {
        this.blogRepository = BlogRepository.getInstance();
        this.userService = userService;
    }

    public Blog createBlog(String title, String text, String email) {
        User foundUser = userService.getUserByEmail(email);
        Blog blog = new Blog(title, text, foundUser);
        blogRepository.save(blog);
        return blog;
    }

    public Blog getBlogByTitle(String title) {
        return blogRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public void addComment(String title, String comment, User user) {
        Blog foundBlog = getBlogByTitle(title);
        foundBlog.getBlogCommentList().add(new BlogComment(user, comment));
    }

    public List<Blog> getBlogsFilterByStatus(BlogStatus blogStatus, String email) {
        User foundUser = userService.getUserByEmail(email);
        return foundUser.getBlogList().stream()
                .filter(blog -> blogStatus.equals(blog.getBlogStatus()))
                .collect(Collectors.toList());
    }

    public void changeBlogStatus(BlogStatus blogStatus, String title) {
        Blog foundBlog = getBlogByTitle(title);
        if (foundBlog.getBlogStatus().equals(BlogStatus.PUBLISHED)) {
            throw new RuntimeException("Published blog cannot be deleted.");
        }
        foundBlog.setBlogStatus(blogStatus);
    }

    // Sıralama metotları
    public List<Blog> sortByCreationDate(boolean ascending) {
        return blogRepository.getAllBlogs().stream()
                .sorted(Comparator.comparing(Blog::getCreatedDate, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<Blog> sortByCommentCount(boolean ascending) {
        return blogRepository.getAllBlogs().stream()
                .sorted(Comparator.comparing(blog -> blog.getBlogCommentList().size()))
                .collect(Collectors.toList());
    }

    public List<Blog> sortByViewCount(boolean ascending) {
        return blogRepository.getAllBlogs().stream()
                .sorted(Comparator.comparing(Blog::getViewCount, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.getAllBlogs();
    }
}
