from django.contrib import admin
from .models import User,Genre,Liked,Chapter,GenreList,PaidSubcription,Subcription,Title,Viewed
# Register your models here.
admin.site.register([User,Genre,Liked,Chapter,GenreList,PaidSubcription,Subcription,Title,Viewed])
