from django.db import models

class User(models.Model):
    username=models.CharField(max_length=255)
    password=models.CharField(max_length=255)
    email=models.CharField(max_length=255)
    isAdmin=models.BooleanField()

class Title(models.Model):
    userid = models.BigIntegerField(default=0)
    name=models.CharField(max_length=255)
    author=models.CharField(max_length=255)
    description=models.CharField(max_length=255)
    fee=models.BooleanField()
    created_at=models.DateTimeField()
    updated_at=models.DateTimeField()
    totalViews=models.IntegerField()

class Chapter(models.Model):
    titleId=models.ForeignKey(Title, on_delete = models.CASCADE)
    name=models.CharField(max_length=255)
    number=models.IntegerField()
    views=models.IntegerField()
    content=models.CharField(max_length=255)
    created_at=models.DateTimeField()
    updated_at=models.DateTimeField()

class Genre(models.Model):
    userid = models.BigIntegerField(default=0)
    name=models.CharField(max_length=255)
    description=models.CharField(max_length=255)
    created_at=models.DateTimeField()
    updated_at=models.DateTimeField()

class GenreList(models.Model):
    titleId=models.ForeignKey(Title, on_delete = models.CASCADE)
    genreId=models.ForeignKey(Genre, on_delete = models.CASCADE)

class Liked(models.Model):
    userid = models.BigIntegerField(default=0)
    titleId=models.ForeignKey(Title, on_delete = models.CASCADE)

class Viewed(models.Model):
    userid = models.BigIntegerField(default=0)
    chapterId=models.models.ForeignKey(Chapter, on_delete = models.CASCADE)
    view_at=models.DateTimeField()

class Subcription(models.Model):
    price=models.IntegerField()
    months=models.IntegerField()

class PaidSubcription(models.Model):
    userid = models.BigIntegerField(default=0)
    start_at = models.DateTimeField()
    subcriptionId=models.ForeignKey(Subcription, on_delete = models.CASCADE)
