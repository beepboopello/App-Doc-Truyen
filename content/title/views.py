from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from content_model.models import Title, User, Genre, GenreList
from .serializer import TitleSerializer, GenreListSerializer,UpdateTitleSerializer
from datetime import date, datetime
import json
import requests
# Create your views here.

@api_view(['GET'])
# lấy thông tin truyện
def get_Title(request):
    try:
        titleid = request.query_params.get('titleid')
        titles=Title.objects.get(id=titleid)
        serializer=TitleSerializer(titles, many=False)
        res = serializer.data
        genreList = GenreList.objects.filter(id = titleid)
        genres = ''
        
        for genre in list(genreList):
            genres += genre.genreId.name + ' '
            genreID = genre.genreId.id
        print(genres)
        res['genre'] = genres
        res['genreID'] = genreID

        return Response(res)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)



@api_view(['POST'])
def add_genrelist(request):
    # thêm vào bảng trung gian genre list
    titleId = request.data.get('titleId')
    genreId = request.data.get('genreId')

    if not (titleId and genreId):
        return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
    try:
        titleId=Title.objects.get(id=titleId)
    except:
        return Response({"error":"Title id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    try:
        genreId=Genre.objects.get(id=genreId)
    except:
        return Response({"error":"Genre id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    
    data={
        'titleId':titleId,
        'genreId':genreId
    }
    try:
        GenreList.objects.get(titleId = titleId, genreId=genreId)
        return Response({"error":"Sách đã tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    except:
        serializer = GenreListSerializer(data=data)
        if serializer.is_valid():
            serializer.create(data)
            return Response({"message":" Thêm  thành công."}, status=status.HTTP_200_OK)
    return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['GET'])
# lấy danh sách truyện thuộc thể loại
def filter_title(request):
    try:
        genreID=request.query_params.get('genreID')
        page=request.query_params.get('page')
        
        if not (genreID and page):
            return Response ({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
        try:
            Genre.objects.get(id=genreID)
        except:
            return Response({"error":"Genre id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
        
        tmp=GenreList.objects.filter(genreId=genreID)
        list=[]
        for title in tmp:
            list.append({"id":title.titleId.id,
                        "name":title.titleId.name,
                        "author":title.titleId.author,
                        "description":title.titleId.description,
                        "fee":title.titleId.fee})
            
        
        print(list)
        index = 1
        dem = 0
        pages=[]
        list_in_page = []
        for like in list:
            if dem > 11:
                pages.append(list_in_page)
                dem = 0 
                index += 1
                list_in_page = []
            list_in_page.append(like)
            dem+=1     
        pages.append(list_in_page)
        return Response(pages[int(page)-1], status=status.HTTP_200_OK) 
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
@api_view(['GET'])
def get_free_book(request):
    try:
        tmp=Title.objects.filter(fee=True)
        list=[]
        for title in tmp:
            print(title)
            list.append({"id":title.id,
                        "name":title.name,
                        "author":title.author,
                        "description":title.description,
                        "fee":title.fee})
        print(list)
        return Response(list)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['GET'])
def get_freent_book(request):
    try:
        tmp=Title.objects.filter(fee=False)
        list=[]
        for title in tmp:
            print(title)
            list.append({"id":title.id,
                        "name":title.name,
                        "author":title.author,
                        "description":title.description,
                        "fee":title.fee})
        print(list)
        return Response(list)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['POST'])
def add_Title(request):
    # thêm đầu truyện
    userid = request.data.get('userid')
    name=request.data.get('name')
    author=request.data.get('author')
    genreid=request.data.get('genreid')
    fee=request.data.get('fee')
    description=request.data.get('description')
    token = request.data.get('token')


    if not (userid and name and token and description and author and genreid and fee):
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
        Genre.objects.get(id=genreid)
    except:
        return Response({"error":"Genre id không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    
    data={
        'userid':userid,
        'name':name,
        'author':author, 
        'description':description,
        'fee':fee,
        'created_at': datetime.now(),
        'updated_at': datetime.now(),
        'totalViews':0,
    }
    try:
        Title.objects.get(userid = userid, name=name)
        return Response({"error":"Sách đã tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    except:
        serializer = TitleSerializer(data=data)
        # print(serializer)
        if serializer.is_valid():
            URL = "http://localhost:8000/api/add_genrelist/"
            print(serializer.data)

            title = serializer.create(data)
            # data = {"genreId": genreid, "titleID": title.id}
            # print(data)
            # r = requests.post(url = URL, json = data)
            return Response({"message":" Thêm  sach thành công."}, status=status.HTTP_200_OK)
    return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['PUT'])
def update_title(request):
    # sửa đầu truyện
    userid = request.data.get('userid')
    name=request.data.get('name')
    author=request.data.get('author')
    description=request.data.get('description')
    token = request.data.get('token')
    fee=request.data.get("fee")
    titleid=request.data.get("titleid")

    if not (userid and titleid and name and token and description and author and str(fee)):
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
        tmp=Title.objects.get(id=titleid)
    except:
        return Response({"error":"Sách không tồn tại đẻe sửa"},status=status.HTTP_400_BAD_REQUEST)
    
    data={
        'userid':userid,
        'name':name,
        'author':author,
        'description':description,
        'fee':fee,
        # 'created_at': Genre.objects.get("created_at"),
        'updated_at': datetime.now(),
    }
    try:
        
        Title.objects.get(userid = userid, id=titleid)
        serializer = UpdateTitleSerializer(tmp, data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        
    except:
        return Response({"error":"Sách không tồn tại"},status=status.HTTP_400_BAD_REQUEST)
    return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)



