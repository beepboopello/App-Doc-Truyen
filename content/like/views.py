from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from content_model.models import User
from content_model.models import Title
from content_model.models import Liked
from .serializer import LikedSerializer
import math

# Create your views here.
@api_view(['POST','DELETE'])
def add_delete_like(request):
    
    userid = request.data.get('userid')
    titleId = request.data.get('titleid')
    token = request.data.get('token')
    
    if not (userid and titleId and token):
        return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
    try:
        User.objects.get(id=userid)
    except:
        return Response({"error":"User id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    try:
        User.objects.get(id=userid, token =token)
    except:
        return Response({"error":"Xác minh token thất bại."},status=status.HTTP_400_BAD_REQUEST)
    try:
        titleId = Title.objects.get(id=titleId)
    except:
        return Response({"error":"Title id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    
    data = {
            'userid' : userid,
            'titleId' : titleId
        }
    if request.method == "POST":
        try:
            Liked.objects.get(userid = userid, titleId = titleId)
            return Response({"error":"Lượt thích đã tồn tại."},status=status.HTTP_400_BAD_REQUEST)
        except:
            serializer = LikedSerializer(data=data)
            if serializer.is_valid():
                serializer.create(data)
                return Response({"message":" Thêm lượt thích thành công."}, status=status.HTTP_200_OK)
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
    if request.method == "DELETE":
        try:
            obj =Liked.objects.get(userid = userid,titleId=titleId)
            try:
                obj.delete()
                return Response({"message":"Xóa lượt thích thành công."}, status=status.HTTP_200_OK)
            except:
                return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        except:
            return Response({"error":"Lượt không tồn tại."},status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def total_like_by_title(request):
    
    titleId = request.query_params.get('titleid')
    
    if not titleId:
        return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
    try:
        titleId = Title.objects.get(id=titleId)
    except:
        return Response({"error":"Title id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    try:
        total_like = len(Liked.objects.filter(titleId=titleId))
        return Response({"like":total_like}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
@api_view(['GET'])
def love_story_by_user(request):
    try:
        
        userid = request.query_params.get('userid')
        
        if not (userid ):
            return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
        try:
            User.objects.get(id=userid)
        except:
            return Response({"error":"User id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)

        list_love_story = []
        for like in Liked.objects.filter(userid = userid):
            list_love_story.append({"id": like.titleId.id,"name": like.titleId.name})   
        list_love_story.append(list_love_story)  
        return Response({'data': list_love_story}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
    
    