# Generated by Django 4.2 on 2023-04-22 17:31

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('content_model', '0003_viewed_views'),
    ]

    operations = [
        migrations.AddField(
            model_name='paidsubcription',
            name='paid',
            field=models.BooleanField(default=False),
        ),
        migrations.AddField(
            model_name='user',
            name='accountID',
            field=models.CharField(max_length=255, null=True),
        ),
    ]
