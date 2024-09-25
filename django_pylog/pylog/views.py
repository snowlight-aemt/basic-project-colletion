from django.forms import model_to_dict
from django.http import HttpResponse, HttpRequest
from django.shortcuts import render, get_object_or_404
from django.views.generic import CreateView

from pylog.forms import BlogForm
from pylog.models import Blog


def blog(request: HttpRequest) -> HttpResponse:
    if request.method == "GET":
        title = request.GET.get("title")

        if title is not None:
            new_blog_list = Blog.objects.filter(title=title)
        else:
            new_blog_list = Blog.objects.all()

        context = {"post_list": new_blog_list}

        return render(request, "pylog/blog.html", context)
    elif request.method == "POST":
        blog = Blog.objects.create(
            title=request.POST.get("title"),
            content=request.POST.get("content"),
            email=request.POST.get("email"),
        )

        return HttpResponse(content_type="application/json", status=200, content=blog)


def blog_detail(request: HttpRequest, blog_no: int) -> HttpResponse:
    blog = get_object_or_404(Blog, pk=blog_no)

    return render(request, "pylog/blog_detail.html", {"post": blog})


blog_new = CreateView.as_view(model=Blog, form_class=BlogForm, success_url="/success")
