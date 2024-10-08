from django.contrib import admin

from users import models


@admin.register(models.User)
class CustomUserAdmin(admin.ModelAdmin):
    list_display = ("username", "email", "gender", "language", "currency", "superhost")
    list_filter = ("language", "currency", "superhost")
