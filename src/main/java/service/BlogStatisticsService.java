package service;

import model.Blog;

import java.util.List;

public class BlogStatisticsService {
    private List<Blog> blogs;

    public BlogStatisticsService(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public int getTotalBlogs() {
        return blogs.size();
    }

    public int getTotalComments() {
        return blogs.stream()
                .mapToInt(blog -> blog.getBlogCommentList().size())
                .sum();
    }

    public int getTotalViews() {
        return blogs.stream()
                .mapToInt(blog -> blog.getViewCount().intValue())
                .sum();
    }

    public void printStatistics() {
        System.out.println("Toplam Blog Sayısı: " + getTotalBlogs());
        System.out.println("Toplam Yorum Sayısı: " + getTotalComments());
        System.out.println("Toplam Görüntülenme Sayısı: " + getTotalViews());
    }
}
