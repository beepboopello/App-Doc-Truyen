# Generated by Django 4.2 on 2023-04-25 14:37

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='chapter',
            name='content',
            field=models.TextField(max_length=255),
        ),
    ]
