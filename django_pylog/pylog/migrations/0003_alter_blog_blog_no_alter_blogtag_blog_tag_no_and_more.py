# Generated by Django 5.1 on 2024-08-15 11:29

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("pylog", "0002_alter_blog_blog_no_alter_blogtag_blog_tag_no_and_more"),
    ]

    operations = [
        migrations.AlterField(
            model_name="blog",
            name="blog_no",
            field=models.AutoField(
                auto_created=True,
                default=0,
                primary_key=True,
                serialize=False,
                unique=True,
            ),
        ),
        migrations.AlterField(
            model_name="blogtag",
            name="blog_tag_no",
            field=models.AutoField(
                auto_created=True,
                default=0,
                primary_key=True,
                serialize=False,
                unique=True,
            ),
        ),
        migrations.AlterField(
            model_name="comments",
            name="comment_no",
            field=models.AutoField(
                auto_created=True,
                default=0,
                primary_key=True,
                serialize=False,
                unique=True,
            ),
        ),
        migrations.AlterField(
            model_name="tags",
            name="tag_no",
            field=models.AutoField(
                auto_created=True,
                default=0,
                primary_key=True,
                serialize=False,
                unique=True,
            ),
        ),
    ]