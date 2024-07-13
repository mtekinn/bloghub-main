package repository;

import model.Blog;
import model.enums.BlogStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlogRepository {

    private List<Blog> blogList = new ArrayList<>();
    private static BlogRepository instance;

    private BlogRepository() {}

    public void save(Blog blog) {
        blogList.add(blog);
    }

    public List<Blog> getAllBlogs() {
        return blogList;
    }

    public static BlogRepository getInstance() {
        if (instance == null) {
            instance = new BlogRepository();
        }
        return instance;
    }

    public Optional<Blog> findByTitle(String title) {
        return blogList.stream()
                .filter(blog -> blog.getTitle().equals(title))
                .filter(blog -> !blog.getBlogStatus().equals(BlogStatus.DELETED))
                .findFirst();
    }
}
