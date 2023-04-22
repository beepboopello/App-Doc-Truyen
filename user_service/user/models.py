from django.db import models

# Create your models here.


class User(models.Model):
    id = models.AutoField(primary_key=True)
    username = models.CharField(max_length=30)
    password = models.CharField(max_length=255)
    admin = models.BooleanField()
    email = models.CharField(max_length=255)
    token = models.CharField(max_length=255,null=True)
    accountID = models.CharField(max_length=255,null=True)

class Subscription(models.Model):
    id = models.AutoField(primary_key=True)
    price = models.IntegerField(default=100000)
    months = models.IntegerField(default=1)

class PaidSubscription(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    subscription = models.ForeignKey(Subscription, on_delete=models.CASCADE)
    started_at = models.DateTimeField()
    paid = models.BooleanField()