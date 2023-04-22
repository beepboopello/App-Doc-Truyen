from django.urls import path
from . import views

urlpatterns = [
    path('api/chapter/get_chapter_info/',views.get_chapter_info,name='get_chapter_info'),
    path('api/read/add_read/',views.add_read,name='add_read'),
    path('api/read/get_read/',views.get_read,name='get_read'),
    path('api/totalviews/',views.totalviews,name='totalviews'),
    path('api/chapter/add_chapter/',views.add_chapter,name='add_chapter'),
    path('api/chapter/update_chapter/',views.update_chapter,name='update_chapter'),
]