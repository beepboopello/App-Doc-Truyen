# Generated by Django 4.2 on 2023-04-17 11:19

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0004_user_token'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='accountID',
            field=models.CharField(max_length=255, null=True),
        ),
    ]