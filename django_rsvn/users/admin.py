from django.contrib import admin

from users import models


@admin.register(models.User)
class CustomUserAdmin(admin.ModelAdmin):
    pass
