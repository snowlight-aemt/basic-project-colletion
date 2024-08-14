from datetime import timezone
from email.policy import default

from django.db import models


class Blog(models.Model):
	blog_no = models.IntegerField(primary_key=True, unique=True, null=False, blank=False, default=0)
	title = models.CharField(max_length=100)
	email = models.EmailField()
	content = models.TextField()
	createdAt = models.DateTimeField(auto_now_add=True)
	updatedAt = models.DateTimeField(auto_now=True)


class Comments(models.Model):
	comment_no = models.IntegerField(primary_key=True, unique=True, null=False, blank=False, default=0)
	blog_no = models.ForeignKey(Blog, on_delete=models.CASCADE)
	userId = models.CharField(max_length=100)
	comment = models.TextField()
	createdAt = models.DateTimeField(auto_now_add=True)
	updatedAt = models.DateTimeField(auto_now=True)


class Tags(models.Model):
	tag_no = models.IntegerField(primary_key=True, unique=True, null=False, blank=False, default=0)
	tag_name = models.CharField(max_length=100, unique=True, null=False, blank=False, default=0)
	createdAt = models.DateTimeField(auto_now_add=True)
	updatedAt = models.DateTimeField(auto_now=True)


class BlogTag(models.Model):
	blog_tag_no = models.IntegerField(primary_key=True, unique=True, null=False, blank=False, default=0)
	blog_no = models.ForeignKey(Blog, on_delete=models.CASCADE)
	tag_no = models.ForeignKey(Tags, on_delete=models.CASCADE)
