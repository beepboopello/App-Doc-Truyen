from django.shortcuts import render
from rest_framework import viewsets,status
from rest_framework.response import Response
from .serializers import UserSerializer
from .models import User, Subscription
from rest_framework.decorators import action
from django.http import HttpResponse
import json
from rest_framework.decorators import api_view

import bcrypt 

# Create your views here.

class UserModelViewSet(viewsets.ModelViewSet):
    serializer_class = UserSerializer
    queryset = User.objects.all()

    def create(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save(password= bcrypt.hashpw(self.request.data['password'].encode('utf-8'),bcrypt.gensalt()).decode('utf-8'))
            res = dict(serializer.data)
            res.pop('accountID')
            res.pop('password')
            return Response(res, status=status.HTTP_200_OK)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        
        
@api_view(['PUT','POST','GET'])
def update_sub(request):
    try:
        if request.method == 'GET':
            if request.GET.get('months'):
                res = Subscription.objects.filter(months=request.GET.get('months'))
                return Response(res.values()[0], status=status.HTTP_200_OK)
            sublist = Subscription.objects.all().order_by('months')
            res = []
            for sub in sublist.values():
                res.append(sub)
            return Response(res, status=status.HTTP_200_OK)

        keys = dict(request.data).keys()
        if 'token' not in keys or not request.data['token']:
            return Response({"error":"Xác minh token thất bại."},status=status.HTTP_401_UNAUTHORIZED)
        if 'price' not in keys or not request.data['price'] or 'months' not in keys or not request.data['months']:
            return Response({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
        if request.method == 'PUT':
            sub = Subscription.objects.filter(months = request.data['months'])
            if(sub.exists()):
                sub.update(price = request.data['price'])
                return Response(list(sub.values())[0], status=status.HTTP_200_OK)
            else:
                return Response({"error":"Gói đăng ký không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
        else  :
            sub = Subscription.objects.filter(months = request.data['months'])
            if(not sub.exists()):
                data = {}
                data['months'] = request.data['months']
                data['price'] = request.data['price']
                data['id'] = Subscription.objects.create(**data).id
                return Response(data, status=status.HTTP_200_OK)
            else:
                return Response({"error":f"Gói đăng ký {request.data['months']} tháng đã tồn tại."},status=status.HTTP_400_BAD_REQUEST)
    except ValueError:
        return Response({"error":"Giá và số tháng phải là số nguyên."},status=status.HTTP_400_BAD_REQUEST)
    except:
        return Response({"error":"Có lỗi xảy ra, xin hay thử lại sau."},status=status.HTTP_500_INTERNAL_SERVER_ERROR)

    


@api_view(['POST'])
def verify(request):
    keys = dict(request.data).keys()
    if 'token' not in keys or not request.data['token']:
        return Response({"error":"Xác minh token thất bại."},status=status.HTTP_401_UNAUTHORIZED)
    
    user = User.objects.filter(token = request.data['token'])
    if(user):
        user = list(user.values())[0]
        user.pop('accountID')
        user.pop('password')
        return Response(user,status=status.HTTP_200_OK)
    else :
        return Response({"error":"Xác minh token thất bại."},status=status.HTTP_401_UNAUTHORIZED)
    
@api_view(['POST'])
def login(request):
    username = request.data['username']
    password = request.data['password']
    if not username or not password :
        return Response({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
    user = User.objects.filter(username = username)
    if not user : 
        return Response({"error":"Username hoặc password không đúng."},status=status.HTTP_400_BAD_REQUEST)
    user = list(user.values())[0]
    if not user or not bcrypt.checkpw(password.encode('utf-8'), bytes(user['password'], 'utf-8')):
        return Response({"error":"Username hoặc password không đúng."},status=status.HTTP_400_BAD_REQUEST)
    else :
        user.pop('accountID')
        user.pop('password')
        return Response(user,status = status.HTTP_200_OK)
    

@api_view(['POST'])
def googleLogin(request):
    email = request.data['email']
    accountID = request.data['accountID']
    if not email or not accountID :
        return Response({"error":"Hãy điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
    print(accountID)
    user = User.objects.filter(email = email, accountID = accountID)
    if not user : 
        return Response({"error":"Không thể xác minh người dùng."},status=status.HTTP_400_BAD_REQUEST)
    else:
        user = list(user.values())[0]
        user.pop('accountID')
        user.pop('password')
        return Response(user,status = status.HTTP_200_OK)


    

