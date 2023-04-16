from django.shortcuts import render
from rest_framework import viewsets,status
from rest_framework.response import Response
from .serializers import UserSerializer
from .models import User
from rest_framework.decorators import action
import bcrypt 

# Create your views here.

class UserModelViewSet(viewsets.ModelViewSet):
    serializer_class = UserSerializer
    queryset = User.objects.all()

    def create(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save(password= bcrypt.hashpw(self.request.data['password'].encode('utf-8'),bcrypt.gensalt()))
            return Response(serializer.data, staftus=status.HTTP_201_CREATED)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @action(detail=True, methods=['post'])
    def verify(self,request,pk = None):
        user = self.get_object()
        serializer = UserSerializer(user)
        token = request.POST.get('token')
        return Response({'user':serializer.data}, status=status.HTTP_200_OK)

