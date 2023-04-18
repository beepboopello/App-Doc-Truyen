from django.urls import path
from . import views
urlpatterns = [
    path("mostlike/",views.most_like_by_title),
    path("mostread/",views.most_view_by_title),
]
