# Generated by Django 4.2 on 2023-04-23 10:52

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('content_model', '0004_paidsubcription_paid_user_accountid'),
    ]

    operations = [
        migrations.AddField(
            model_name='paidsubcription',
            name='end_at',
            field=models.DateTimeField(default=datetime.datetime(2023, 4, 23, 10, 52, 15, 951375)),
            preserve_default=False,
        ),
    ]
