from django.http import HttpResponse
from .models import Chapter, Title,User,Viewed
import json
import datetime
from django.views.decorators.csrf import csrf_exempt

def get_chapter_info(request):
    resp = {}
    if request.method == 'GET':    
        if 'application/json' in request.META.get('CONTENT_TYPE',''):
            request_data = json.loads(request.body)
            
            id = request_data.get('chapterid')

            try:
                #tang so views cua chapter do trong bang views
                chapter_=Chapter.objects.filter(id=id)[0]
                chapter_.views++
                chapter_.save()
                
                #lay thong tin cua chapter
                chapter_data=Chapter.objects.filter(id=id).values()[0]
                
                resp['status'] = 'Success'
                resp['status_code'] = '200'
                resp['data'] = chapter_data
            except Exception as e:
                print(e)
                resp['status'] = 'Failed'
                resp['status_code'] = '500'
                resp['error'] = 'Có lỗi xảy ra, hãy thử lại sau.'
        else:
            resp['status'] = 'Failed'
            resp['status_code'] = '400'
            resp['message'] =  'Request type is not matched.'
    else:
        resp['status'] = 'Failed'
        resp['status_code'] = '400'
        resp['message'] = 'Request type is not GET.'  

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   

@csrf_exempt
def add_read(request):
    resp = {}
    if request.method == 'POST':    
        if 'application/json' in request.META.get('CONTENT_TYPE',''):
            request_data = json.loads(request.body)

            userid = request_data.get('userid')
            chapterid = request_data.get('chapterid')
            token = request_data.get('token')

            if userid!=None and chapterid!=None and token!=None:                
                if User.objects.filter(id=userid).count()>0:
                    if Chapter.objects.filter(id=chapterid).count()>0:
                        if token:#xac minh token
                            try:
                                view_at = datetime.datetime.now().date()
                                if Viewed.objects.filter(userid=userid,chapterId=chapterid).count()==0:
                                    view_data = Viewed(userid=userid,chapterId_id=chapterid,view_at=view_at,views=1)
                                    view_data.save()
                                else:
                                    view_data = Viewed.objects.filter(userid=userid,chapterId_id=chapterid)[0]
                                    view_data.views+=1
                                    view_data.save()

                                resp['status'] = 'Success'
                                resp['status_code'] = '200'
                                resp['message'] = 'Thêm lượt xem thành công.'
                            except Exception as e:
                                print(e)
                                resp['status'] = 'Failed'
                                resp['status_code'] = '500'
                                resp['error'] = 'Có lỗi xảy ra, hãy thử lại sau.'
                        else:
                            resp['status'] = 'Failed'
                            resp['status_code'] = '401'
                            resp['error'] = 'Xác minh token thất bại.'
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
        resp['error'] =  'Request method is not POST'

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   

def get_read(request):
    resp={}
    if request.method == 'GET':    
        if 'application/json' in request.META.get('CONTENT_TYPE',''):
            request_data = json.loads(request.body)
            userid = request_data.get('userid')
            token = request_data.get('token')

            if userid!=None and token!=None:                
                if User.objects.filter(id=userid).count()>0:
                    if token:#xac minh token
                        try:
                            view_data = Viewed.objects.filter(userid=userid).values()

                            data=[]
                            for i in view_data:
                                data.append(i)

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
                        resp['status_code'] = '401'
                        resp['error'] = 'Xác minh token thất bại.'
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
        resp['error'] =  'Request method is not GET'

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   

def totalviews(request):
    resp={}
    if request.method == 'GET':    
        if 'application/json' in request.META.get('CONTENT_TYPE',''):
            request_data = json.loads(request.body)

            chapterid = request_data.get('chapterid')

            if chapterid!=None:                
                if Chapter.objects.filter(id=chapterid).count()>0:
                    try:
                        view_data = Viewed.objects.filter(chapterId_id=chapterid)

                        views=0
                        for i in view_data:
                            views+=i.views

                        resp['status'] = 'Success'
                        resp['status_code'] = '200'
                        resp['views'] = views
                    except Exception as e:
                        print(e)
                        resp['status'] = 'Failed'
                        resp['status_code'] = '500'
                        resp['error'] = 'Có lỗi xảy ra, hãy thử lại sau.'
                else:
                    resp['status'] = 'Failed'
                    resp['status_code'] = '400'
                    resp['error'] = 'Chapter id không tồn tại.'
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
        resp['error'] =  'Request method is not GET'

    return HttpResponse(json.dumps(resp,default=str), content_type = 'application/json')   

@csrf_exempt
def add_chapter(request):
    resp={}
    if request.method == 'POST':    
        if 'application/json' in request.META.get('CONTENT_TYPE',''):
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
            resp['error'] =  'Request type is not matched.'
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
