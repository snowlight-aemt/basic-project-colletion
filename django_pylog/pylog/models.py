from django.db import models


class Blog(models.Model):
	blog_no = models.IntegerField(primary_key=True)
	title = models.CharField(max_length=100)
	email = models.EmailField()
	body = models.TextField()
	createdAt = models.DateTimeField(auto_now_add=True)
	updatedAt = models.DateTimeField(auto_now=True)


class Comments(models.Model):
	blog_no = models.ForeignKey(Blog, on_delete=models.CASCADE)
	userId = models.CharField(max_length=100)
	comment = models.TextField()
	createdAt = models.DateTimeField(auto_now_add=True)
	updatedAt = models.DateTimeField(auto_now=True)


class Tags(models.Model):
	tag_name = models.CharField(max_length=100).unique
	createdAt = models.DateTimeField(auto_now_add=True)
	updatedAt = models.DateTimeField(auto_now=True)
	

class BlogTag(models.Model):
	blog_no = models.ForeignKey(Blog, on_delete=models.CASCADE)
	tag_name = models.ForeignKey(Tags, on_delete=models.CASCADE)


