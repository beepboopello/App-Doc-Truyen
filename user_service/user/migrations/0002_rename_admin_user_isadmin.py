# Generated by Django 4.2 on 2023-04-15 10:47

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='user',
            old_name='admin',
            new_name='isAdmin',
        ),
    ]