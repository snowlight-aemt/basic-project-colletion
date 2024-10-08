# Generated by Django 5.1 on 2024-08-28 12:17

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("users", "0003_user_avatar_user_gender"),
    ]

    operations = [
        migrations.AddField(
            model_name="user",
            name="birthdate",
            field=models.DateField(null=True),
        ),
        migrations.AddField(
            model_name="user",
            name="currency",
            field=models.CharField(
                blank=True,
                choices=[("usd", "USD"), ("KRW", "KRW")],
                max_length=3,
                null=True,
            ),
        ),
        migrations.AddField(
            model_name="user",
            name="language",
            field=models.CharField(
                blank=True,
                choices=[("en", "English"), ("kr", "Korean")],
                max_length=2,
                null=True,
            ),
        ),
        migrations.AddField(
            model_name="user",
            name="superhost",
            field=models.BooleanField(default=False),
        ),
    ]
