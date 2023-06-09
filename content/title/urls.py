from django.urls import path
from . import views

urlpatterns = [
    path("title/",views.get_Title),
    path("add_title/", views.add_Title),
    path("add_genrelist/", views.add_genrelist),
    path("genre/title/", views.filter_title),
    path("update_title/", views.update_title),
    path("getfreetitle/",views.get_free_book),
    path("getnofreetitle/",views.get_freent_book),
    path("delete_title/",views.delete_title_byID)
]
