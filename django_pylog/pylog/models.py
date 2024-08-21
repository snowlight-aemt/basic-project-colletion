from django.db import models


class Blog(models.Model):
    blog_no = models.BigAutoField(primary_key=True)
    title = models.CharField(max_length=100)
    email = models.EmailField()
    content = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.title


class Comments(models.Model):
    comment_no = models.BigAutoField(primary_key=True)
    blog_no = models.ForeignKey(Blog, on_delete=models.CASCADE)
    userId = models.CharField(max_length=100)
    comment = models.TextField()
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class Tags(models.Model):
    tag_no = models.BigAutoField(primary_key=True)
    tag_name = models.CharField(max_length=100, unique=True, null=False, blank=False)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


class BlogTag(models.Model):
    blog_tag_no = models.BigAutoField(primary_key=True)
    blog_no = models.ForeignKey(Blog, on_delete=models.CASCADE)
    tag_no = models.ForeignKey(Tags, on_delete=models.CASCADE)
