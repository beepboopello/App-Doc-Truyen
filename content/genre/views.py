from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from content_model.models import Genre
from content_model.models import User
from .serializer import GenreSerializer, UpdateGenreSerializer
from datetime import date, datetime
# Create your views here.

@api_view(['GET'])
def get_all_genre(request):
    # lấy danh sách genre
    try:
        genre=Genre.objects.all()
        serializer=GenreSerializer(genre, many=True)
        return Response(serializer.data)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."},status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['POST'])
def add_genre(request):
    # thêm genre
    userid = request.data.get('userid')
    name=request.data.get('name')
    description=request.data.get('description')
    token = request.data.get('token')

    if not (userid and name and token and description):
        return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
    try:
        User.objects.get(id=userid)
    except:
        return Response({"error":"User id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    try:
        User.objects.get(id=userid, token =token)
    except:
        return Response({"error":"Xác minh token thất bại."},status=status.HTTP_400_BAD_REQUEST)
    
    data={
        'userid':userid,
        'name':name,
        'description':description,
        'created_at': datetime.now(),
        'updated_at': datetime.now(),
    }
    try:
        Genre.objects.get(userid = userid, name=name)
        return Response({"error":"Loại sách đã tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    except:
        serializer = GenreSerializer(data=data)
        print(serializer)
        if serializer.is_valid():
            serializer.create(data)
            return Response({"message":" Thêm loại sách thành công."}, status=status.HTTP_200_OK)
    return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)



@api_view(['PUT'])
def update_genre(request):
    # sửa genre
    userid = request.data.get('userid')
    genreid=request.data.get('genreid')
    name=request.data.get('name')
    description=request.data.get('description')
    token = request.data.get('token')

    if not (userid and genreid and name and token and description):
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
        tmp=Genre.objects.get(id=genreid)
    except:
        return Response({"error":"Loại sách không tồn tại"},status=status.HTTP_400_BAD_REQUEST)
    
    data={
        'userid':userid,
        'name':name,
        'description':description,
        # 'created_at': Genre.objects.get("created_at"),
        'updated_at': datetime.now(),
    }
    try:
        Genre.objects.get(userid = userid, id=genreid)
        serializer = UpdateGenreSerializer(tmp, data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
    except:
        return Response({"error":"Loại sách không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

