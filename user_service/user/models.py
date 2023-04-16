from django.db import models

# Create your models here.


class User(models.Model):
    id = models.AutoField(primary_key=True)
    username = models.CharField(max_length=30)
    password = models.CharField(max_length=255)
    admin = models.BooleanField()
    email = models.CharField(max_length=255)
    token = models.CharField(max_length=255,null=True)