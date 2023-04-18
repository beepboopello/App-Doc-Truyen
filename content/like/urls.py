from django.urls import path
from . import views
urlpatterns = [
    path("like/",views.add_delete_like),
    path("totallike/",views.total_like_by_title),
    path("lovestory/",views.love_story_by_user)
]
