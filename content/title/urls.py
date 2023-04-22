from django.urls import path
from . import views

urlpatterns = [
    path("title/",views.get_Title),
    path("add_title/", views.add_Title),
    path("add_genrelist/", views.add_genrelist),
    path("genre/tittle/", views.filter_title),
    path("update_title/", views.update_title)
]