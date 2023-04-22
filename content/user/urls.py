from django.urls import path,include
from . import views


urlpatterns = [
    path('verify/', views.verify, name='verify'),
    path('login/', views.login, name='login'),
    path('googleLogin/', views.googleLogin, name='googleLogin'),
    path('subscription/', views.update_sub,name="update_subcription"),
]