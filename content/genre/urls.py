from django.urls import path
from . import views

urlpatterns = [
    path("genre/",views.get_all_genre),
    path("add_genre/", views.add_genre),
    path("update_genre/",views.update_genre),
    path("delete_genre/",views.delete_genre_byID),
    path("getid_genre_by_name/",views.get_genre_by_name),
]