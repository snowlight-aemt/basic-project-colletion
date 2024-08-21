from django import forms
from pylog.models import Blog


class BlogForm(forms.ModelForm):
    class Meta:
        model = Blog
        fields = "__all__"
        exclude = ["blog_no"]
