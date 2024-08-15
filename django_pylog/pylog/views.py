from tempfile import template

from django.http import HttpResponse, HttpRequest
from django.shortcuts import render, get_object_or_404
from django.views.generic import CreateView

from pylog.forms import BlogForm
from pylog.models import Blog


def blog(request: HttpRequest) -> HttpResponse:
    if request.GET:
        blog_list = Blog.objects.all()

        return render(request, "pylog/blog.html", {"post_list": blog_list})
    elif request.POST:
        # print(request.POST.get("content"))

        Blog.objects.create(
            title=request.POST["title"],
            content=request.POST["content"],
            email=request.POST["email"],
        )

    return render(request, "pylog/blog.html", {})


def blog_detail(request: HttpRequest, blog_no: int) -> HttpResponse:
    blog = get_object_or_404(Blog, pk=blog_no)

    return render(request, "pylog/blog_detail.html", {"post": blog})


blog_new = CreateView.as_view(model=Blog, form_class=BlogForm, success_url="/success")
