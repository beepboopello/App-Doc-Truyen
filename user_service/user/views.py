from django.shortcuts import render
from rest_framework import viewsets,status
from rest_framework.response import Response
from .serializers import UserSerializer
from .models import User
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


    

