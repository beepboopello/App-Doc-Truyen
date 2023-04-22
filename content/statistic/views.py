from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from content_model.models import Title,Viewed,Chapter
from content_model.models import Liked

# Create your views here.

@api_view(['GET'])
def most_like_by_title(request):
    try:
        list_most_like = []
        
        for title in Title.objects.all():
            total_like = len(Liked.objects.filter(titleId=title))
            list_most_like.append({"id": title.id,"name":title.name,"like":total_like})
            
        list_most_like = sorted(list_most_like, key=lambda x: x["like"], reverse=True)
        return Response({"data":list_most_like}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
@api_view(['GET'])
def most_view_by_chapter(request):
    try:
        view_by_chapter = []
        
        for chapter in Chapter.objects.all():
            total_views = 0
            for view in Viewed.objects.filter(chapterId=chapter):
                total_views += view.views
            view_by_chapter.append({"chapterId":chapter.id,"name":chapter.name,"views":total_views})
            
        view_by_chapter = sorted(view_by_chapter, key=lambda x: x["views"], reverse=True)
        
        return Response({"data":view_by_chapter}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
@api_view(['GET'])
def most_view_by_title(request):
    try:
        
        view_by_title = []
        
        for title in Title.objects.all():
            total_views = 0
            for chapter in Chapter.objects.filter(titleId = title):
                for view in Viewed.objects.filter(chapterId=chapter):
                    total_views += view.views
            view_by_title.append({"id":title.id,"name":title.name,"views":total_views})
        
            
        view_by_title = sorted(view_by_title, key=lambda x: x["views"], reverse=True)
    
        
        return Response({"data":view_by_title}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)