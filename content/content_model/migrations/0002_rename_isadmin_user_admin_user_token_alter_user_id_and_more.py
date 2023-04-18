# Generated by Django 4.1.6 on 2023-04-18 03:15

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("content_model", "0001_initial"),
    ]

    operations = [
        migrations.RenameField(
            model_name="user", old_name="isAdmin", new_name="admin",
        ),
        migrations.AddField(
            model_name="user",
            name="token",
            field=models.CharField(max_length=255, null=True),
        ),
        migrations.AlterField(
            model_name="user",
            name="id",
            field=models.AutoField(primary_key=True, serialize=False),
        ),
        migrations.AlterField(
            model_name="user", name="username", field=models.CharField(max_length=30),
        ),
    ]
