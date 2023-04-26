from calendar import c
from unicodedata import name
from urllib import response
from xmlrpc.client import DateTime
from django.http import HttpResponse
from content_model.models import Chapter, Liked, Title,User,Viewed
import json
import datetime
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from django.db.models import Q
import requests

@api_view(['POST'])
def get_chapter_info(request):
    if 'application/json' in request.META.get('CONTENT_TYPE',''):
        request_data = json.loads(request.body)
        
        chapterid = request_data.get('chapterid')

        if chapterid==None:
            return Response({'error':'Hãy điền đầy đủ các trường'},status=status.HTTP_400_BAD_REQUEST)
        else:
            chapterid=int(chapterid)

        try:
            #tang so views cua chapter do trong bang views
            chapter_=Chapter.objects.filter(id=chapterid)[0]
            # chapter_.views+=1
            # chapter_.save()
            
            #tang so totalViews cua truyen
            title_=Title.objects.filter(id=chapter_.titleId_id)[0]
            # title_.totalViews+=1
            # title_.save()

            #tao response data
            response_data={}
            response_data['id']=chapter_.id
            response_data['titleId']=chapter_.titleId_id
            response_data['name']=chapter_.name
            response_data['number']=chapter_.number
            response_data['views']=chapter_.views
            response_data['content']=chapter_.content
            response_data['created_at']=chapter_.created_at
            response_data['updated_at']=chapter_.updated_at
            
            return Response(response_data,status=status.HTTP_200_OK)
        except Exception as e:
            print(e)
            return Response({'error':'Đã có lỗi xảy ra'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    else:
        return Response({'error':'Lỗi định dạng request'},status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def add_read(request):

    userid = request.data.get('userid')
    chapterid = request.data.get('chapterid')
    token = request.data.get('token')



    if userid!=None and chapterid!=None and token!=None:                
        if User.objects.filter(id=userid).count()>0:
            if Chapter.objects.filter(id=chapterid).count()>0:

                chapter_=Chapter.objects.filter(id=chapterid)[0]
                title_=Title.objects.filter(id=chapter_.titleId_id)[0]

                if not title_.fee :
                    URL = "http://localhost:8000/api/checkPayment/"
                    body = {"userID" : userid}
                    response = requests.post(URL, json = body)
                    if(response.status_code!=200):
                        return Response({'error':'Người dùng cần mua gói thanh toán để đọc truyện này'},status=status.HTTP_401_UNAUTHORIZED)

                if token:#xac minh token
                    try:
                        view_at = datetime.datetime.now()
                        print(view_at)
                        if Viewed.objects.filter(userid=userid,chapterId=chapterid).count()==0:
                            view_data = Viewed(userid=userid,chapterId_id=chapterid,view_at=view_at,views=1)
                            view_data.save()
                        else:
                            view_data = Viewed.objects.filter(userid=userid,chapterId_id=chapterid)[0]
                            view_data.view_at = view_at
                            view_data.views+=1
                            view_data.save()

                        chapter_.views+=1
                        chapter_.save()


                        title_.totalViews+=1
                        title_.save()

                        return Response({'message':'Thêm lượt xem thành công.'},status=status.HTTP_200_OK)
                    except Exception as e:
                        print(e)
                        return Response({'error':'Có lỗi xảy ra, hãy thử lại sau'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
                else:
                    return Response({'error':'Xác minh token thất bại'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            else:
                return Response({'error':'Chapter id không tồn tại.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        else:
            return Response({'error':'User id không tồn tại.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)


@api_view(['GET'])
def get_read(request):
        userid = request.GET.get('userid')
        token = request.GET.get('token')

        if userid!=None and token!=None:                
            if User.objects.filter(id=userid).count()>0:
                if token:#xac minh token
                    try:
                        view_data = Viewed.objects.filter(userid=userid).order_by('-view_at').values()
                        for viewed in view_data :
                            chapter_=Chapter.objects.filter(id=viewed.get('chapterId_id'))[0]
                            title_=Title.objects.filter(id=chapter_.titleId_id)[0]
                            viewed['name'] = title_.name + ' - ' +  chapter_.name
                            viewed['chapterID'] = str(chapter_.id)
                        return Response(view_data,status=status.HTTP_200_OK)
                    except Exception as e:
                        print(e)
                        return Response({'error':'Có lỗi xảy ra, hãy thử lại sau.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
                else:
                    return Response({'error':'Xác minh token thất bại.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            else:
                return Response({'error':'User id không tồn tại.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        else:
            return Response({'error':'Hãy điền đầy đủ các trường.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)


@api_view(['GET'])
def totalviews(request):
    if 'application/json' in request.META.get('CONTENT_TYPE',''):
        request_data = json.loads(request.body)

        chapterid = request_data.get('chapterid')

        if chapterid!=None:                
            if Chapter.objects.filter(id=chapterid).count()>0:
                try:
                    views = Chapter.objects.filter(id=chapterid)[0].views

                    return Response({'views':views},status=status.HTTP_200_OK)
                except Exception as e:
                    print(e)
                    return Response({'error':'Có lỗi xảy ra, hãy thử lại sau.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
            else:
                return Response({'error':'Chapter id không tồn tại.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        else:
            return Response({'error':'Hãy điền đầy đủ các trường.'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    else:
        return Response({'error':'Request type is not matched.'},status=status.HTTP_400_BAD_REQUEST)

@csrf_exempt
def add_chapter(request):
    resp={}
    if request.method == 'POST':    
        request_data = json.loads(request.body)

        number = request_data.get('number')
        name = request_data.get('name')
        titleid = request_data.get('titleid')
        content = request_data.get('content')
        userid = request_data.get('userid')
        token = request_data.get('token')

        if number!=None and name!=None and titleid!=None and content!=None and userid!=None and token!=None:  
            if User.objects.filter(id=userid).count()>0:
                if Title.objects.filter(id=titleid).count()>0:         
                    if token:#xac minh token     
                        try:
                            created_at=datetime.datetime.now().date()
                            updated_at=datetime.datetime.now().date()
                            chapter_data=Chapter(
                                titleId_id=titleid,
                                name=name,
                                number=number,
                                views=0,
                                content=content,
                                created_at=created_at,
                                updated_at=updated_at
                            )
                            chapter_data.save()

                            data=Chapter.objects.filter(
                                titleId_id=titleid,
                                name=name,
                                number=number,
                                views=0,
                                content=content,
                                created_at=created_at,
                                updated_at=updated_at
                            ).values()[0]

                            resp['status'] = 'Success'
                            resp['status_code'] = '200'
                            resp['data'] = data
                        except Exception as e:
                            print(e)
                            resp['status'] = 'Failed'
                            resp['status_code'] = '500'
                            resp['error'] = 'Có lỗi xảy ra, hãy thử lại sau.'
                    else:
                        resp['status'] = 'Failed'
                        resp['status_code'] = '400'
                        resp['error'] = 'Xác minh token thất bại.'
                else:
                    resp['status'] = 'Failed'
                    resp['status_code'] = '400'
                    resp['error'] = 'Title id không tồn tại.'
            else:
                resp['status'] = 'Failed'
                resp['status_code'] = '400'
                resp['error'] = 'User id không tồn tại.'
        else:
            resp['status'] = 'Failed'
            resp['status_code'] = '400'
            resp['error'] = 'Hãy điền đầy đủ các trường.'
    else:
        resp['status'] = 'Failed'
        resp['status_code'] = '400'
        resp['error'] =  'Request method is not POST'

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   
   
@csrf_exempt
def update_chapter(request):
    resp={}
    if request.method == 'PUT':    
        if 'application/json' in request.META.get('CONTENT_TYPE',''):
            request_data = json.loads(request.body)

            chapterid = request_data.get('chapterid')
            number = request_data.get('number')
            name = request_data.get('name')
            titleid = request_data.get('titleid')
            content = request_data.get('content')
            userid = request_data.get('userid')
            token = request_data.get('token')

            if chapterid!=None and number!=None and name!=None and titleid!=None and content!=None and userid!=None and token!=None:  
                if User.objects.filter(id=userid).count()>0:
                    if Chapter.objects.filter(id=chapterid).count()>0:
                        if Title.objects.filter(id=titleid).count()>0:         
                            if token:#xac minh token     
                                try:
                                    chapter_data=Chapter.objects.filter(id=chapterid)[0]

                                    chapter_data.titleid=titleid
                                    chapter_data.name=name
                                    chapter_data.number=number
                                    chapter_data.views=0
                                    chapter_data.content=content
                                    chapter_data.updated_at=datetime.datetime.now().date()

                                    chapter_data.save()

                                    data=Chapter.objects.filter(id=chapterid).values()[0]

                                    resp['status'] = 'Success'
                                    resp['status_code'] = '200'
                                    resp['data'] = data
                                except Exception as e:
                                    print(e)
                                    resp['status'] = 'Failed'
                                    resp['status_code'] = '500'
                                    resp['error'] = 'Có lỗi xảy ra, hãy thử lại sau.'
                            else:
                                resp['status'] = 'Failed'
                                resp['status_code'] = '400'
                                resp['error'] = 'Xác minh token thất bại.'
                        else:
                            resp['status'] = 'Failed'
                            resp['status_code'] = '400'
                            resp['error'] = 'Title id không tồn tại.'
                    else:
                        resp['status'] = 'Failed'
                        resp['status_code'] = '400'
                        resp['error'] = 'Chapter id không tồn tại.'
                else:
                    resp['status'] = 'Failed'
                    resp['status_code'] = '400'
                    resp['error'] = 'User id không tồn tại.'
            else:
                resp['status'] = 'Failed'
                resp['status_code'] = '400'
                resp['error'] = 'Hãy điền đầy đủ các trường.'
        else:
            resp['status'] = 'Failed'
            resp['status_code'] = '400'
            resp['error'] =  'Request type is not matched.'
    else:
        resp['status'] = 'Failed'
        resp['status_code'] = '400'
        resp['error'] =  'Request method is not PUT'

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   

#lấy danh sách được đề xuất xắp xếp theo số likes (lấy (number) truyện có tổng số lượt thích cao nhất, 
# trong trong trường hợp không đủ (number) truyện, lấy tất cả truyện)
@api_view(['GET'])
def get_list_favorite_title(request):
    number = 5
    if number==None:
        return Response({'error':'hãy điền đầy đủ thông tin'},status=status.HTTP_400_BAD_REQUEST)
    else:
        #đưa các param về đúng kiểu của nó
        number=int(number)

    try:    
        #lay danh sach truyen
        list_title=Title.objects.all().values()[:5]

        #lay tong so luot like moi truyen
        for title_ in list_title:
            title_['likes']=Liked.objects.filter(titleId_id=title_['id']).count()

        #sap xep theo so luot like
        list_title=sorted(list_title,key=lambda x:x['likes'],reverse=True)

        response_data=[]
        #lay ra (number) truyen
        for i in range(len(list_title)):
            object = {}
            object['id']=list_title[i]['id']
            object['name']=list_title[i]['name']
            object['author']=list_title[i]['author']
            object['description']=list_title[i]['description']
            object['fee']=list_title[i]['fee']
            object['views']=list_title[i]['totalViews']
            object['like']=list_title[i]['likes']
            response_data.append(object)
        print(len(response_data))

        return Response(response_data,status=status.HTTP_200_OK)
    except Exception as e:
        print(e)
        return Response({'error':'Đã có lỗi xảy ra'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)


#Tim kiem theo ten hoac theo tac gia. danh sach sap xep theo luot xem
@api_view(['GET'])
def search_title_by_name_or_author(request):
    name_search=request.GET.get('name')
    author_search=request.GET.get('author')

    if name_search==None or author_search==None:
        return Response({'error':'hãy điền đầy đủ thông tin'},status=status.HTTP_400_BAD_REQUEST)

    # try:   
        #tim kiem
        #list_search_title=Title.objects.filter(name__contains=name_search,author__contains=author_search).values()
    list_search_title=Title.objects.filter(Q(name__contains=name_search)|Q(author__contains=author_search)).values()

    #lay tong so luot like moi title
    for title_ in list_search_title:
        title_['likes']=Liked.objects.filter(titleId_id=title_['id']).count()

    #sap xep theo so luot views
    list_search_title=sorted(list_search_title,key=lambda x:x['totalViews'],reverse=True)

    #tao response data
    response_data=[]
    for i in range(len(list_search_title)):
        object = {}
        object['id']=list_search_title[i]['id']
        object['name']=list_search_title[i]['name']
        object['author']=list_search_title[i]['author']
        object['description']=list_search_title[i]['description']
        object['fee']=list_search_title[i]['fee']
        object['views']=list_search_title[i]['totalViews']
        object['like']=list_search_title[i]['likes']
        response_data.append(object)

    return Response(response_data,status=status.HTTP_200_OK)
    # except Exception as e:
    #     print(e)
    #     return Response({'error':'Đã có lỗi xảy ra'},status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['GET'])
def chapterList(request):
    titleID = request.GET.get('titleID')
    if not titleID:
        return Response({'error' : "Vui lòng điền đầy đủ các trường"}, status=status.HTTP_400_BAD_REQUEST)
    title = Title.objects.filter(id = titleID)
    if title.exists():
        title = title.values()[0]
        list = []
        for i in Chapter.objects.filter(titleId = title.get('id')).order_by("number"):
            chapter = {}
            chapter['id'] = i.id 
            chapter['name'] = i.name
            chapter['number'] = i.number
            chapter['views'] = i.views
            chapter['content'] = i.content
            list.append(chapter)
        return(Response(list))
    else :
        return Response({'error' : "Title id không tồn tại"}, status=status.HTTP_400_BAD_REQUEST)
    
