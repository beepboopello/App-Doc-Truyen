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
        
        page = request.query_params.get('page')
        
        if not page:
            return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
        
        pages = []
        list_most_like = []
        
        for title in Title.objects.all():
            total_like = len(Liked.objects.filter(titleId=title))
            list_most_like.append({"id": title.id,"name":title.name,"like":total_like})
            
        list_most_like = sorted(list_most_like, key=lambda x: x["like"], reverse=True)
        
        index = 1
        dem = 0
        list_in_page = []
        for like in list_most_like:
            if dem > 11:
                pages.append(list_in_page)
                dem = 0 
                index += 1
                list_in_page = []
            list_in_page.append(like)
            dem+=1     
        pages.append(list_in_page) 
        
        return Response({"data":pages[int(page)-1]}, status=status.HTTP_200_OK)
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
        page = request.query_params.get('page')
        
        if not page:
            return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
        
        pages = []
        
        view_by_title = []
        
        for title in Title.objects.all():
            total_views = 0
            for chapter in Chapter.objects.filter(titleId = title):
                for view in Viewed.objects.filter(chapterId=chapter):
                    total_views += view.views
            view_by_title.append({"id":title.id,"name":title.name,"views":total_views})
        
            
        view_by_title = sorted(view_by_title, key=lambda x: x["views"], reverse=True)
        
        index = 1
        dem = 0
        list_in_page = []
        for like in view_by_title:
            if dem > 11:
                pages.append(list_in_page)
                dem = 0 
                index += 1
                list_in_page = []
            list_in_page.append(like)
            dem+=1     
        pages.append(list_in_page) 
        
        return Response({"data":pages[int(page)-1]}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)